# 22 · 查借阅记录（三表 JOIN + VO 类）

> 目标：把借阅记录查出来给前端看，每条要显示"**书名**"和"**读者名**"——这两个字段不在 borrow_record 表里，得跨三张表 JOIN。
> 前置：21 借书/还书跑通了，borrow_record 里已经有几条数据。

---

## 零、这一步要干嘛

现在你打开数据库，borrow_record 表长这样（就 5 列）：

| id | book_id | reader_id | borrow_date | return_date |
|---|---|---|---|---|
| 1 | 1 | 1 | 2026-08-19 | null |

问题来了：**前端用户想看的是"《百年孤独》被 王五 借了"，而不是"book_id=1 被 reader_id=1 借了"**。

`book_id` 和 `reader_id` 只是**编号**，真正的书名在 book 表、读者名在 reader 表。所以查记录时，得**拿着编号去别的表把名字查回来**——这就是 **JOIN 多表查询**（你 15 学过两张表 JOIN，现在是**三张表**）。

---

## 一、先搞清楚：JOIN 出来是啥样

把三张表拼起来，我们希望得到一张"**完整借阅单**"：

| id | book_id | reader_id | borrow_date | return_date | book_title | reader_name |
|---|---|---|---|---|---|---|
| 1 | 1 | 1 | 2026-08-19 | null | 百年孤独 | 王五 |

注意到没？这张表有 **7 列**，比 borrow_record 表本身（5 列）**多了两列**：`book_title`（书名）和 `reader_name`（读者名）。

**关键结论**：查出来的结果，跟任何一张单表都对不上（字段更多），所以**不能用现有的 Book / Reader / 实体类来装**，得**新建一个专门的类**来装这个"拼出来的视图"。这个类就叫 **VO**。

> **VO = View Object（视图对象）**。它是"几张表 JOIN 拼出来的、给前端看的"结果容器，字段比单表多，跟表不是一一对应的。
>
> **打比方**：三张表是"图书卡""读者卡""借阅卡"，每张卡只有自己那点信息。VO 是**前台填好的"完整借阅单"**——它把三张卡的关键信息汇总在一张纸上，比任何一张原始卡内容都全。

---

## 二、这一步要动的文件

| 文件 | 动作 | 干什么 |
|---|---|---|
| `BorrowRecordVO.java` | **新建** | 装 JOIN 结果的 VO 类（7 字段） |
| `BorrowRecordMapper.java` | **改**（加方法） | 加一句三表 JOIN 的 `findAll` |
| `BorrowService.java` | **改**（加方法） | 加 `findAllRecords` 转发 |
| `BorrowController.java` | **改**（加方法） | 加 `GET /borrows` 接口 |

---

## 三、完整代码

### 1. 新建 `BorrowRecordVO.java`

**📁 新建：`src/main/java/com/example/springbootdemo/BorrowRecordVO.java`**

```java
package com.example.springbootdemo;

import java.time.LocalDate;

public class BorrowRecordVO {
    private Integer id;
    private Integer bookId;
    private Integer readerId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private String bookTitle;    // 从 book 表 JOIN 来的书名
    private String readerName;   // 从 reader 表 JOIN 来的读者名

    // getter / setter 用 IDEA 的 Alt+Insert 自动生成（7 个字段，14 个方法）
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getBookId() { return bookId; }
    public void setBookId(Integer bookId) { this.bookId = bookId; }

    public Integer getReaderId() { return readerId; }
    public void setReaderId(Integer readerId) { this.readerId = readerId; }

    public LocalDate getBorrowDate() { return borrowDate; }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }

    public String getReaderName() { return readerName; }
    public void setReaderName(String readerName) { this.readerName = readerName; }
}
```

> 前 5 个字段对应 borrow_record 表，后 2 个（bookTitle、readerName）是 JOIN 来的，**表里没有**。这就是 VO 和实体类的区别。

### 2. 改 `BorrowRecordMapper.java`（加一句 findAll）

**📁 改：`src/main/java/com/example/springbootdemo/BorrowRecordMapper.java`**

在接口里**加**下面这个方法（原来的 insert、updateReturnDate 不动）。顶部 import 加 `import java.util.List;`：

```java
@Select("select br.id, br.book_id, br.reader_id, br.borrow_date, br.return_date, " +
        "b.title as book_title, r.name as reader_name " +
        "from borrow_record br " +
        "left join book b on br.book_id = b.id " +
        "left join reader r on br.reader_id = r.id " +
        "order by br.id desc")
List<BorrowRecordVO> findAll();
```

**逐句拆解这段 SQL**（这是本步的核心）：

| 片段 | 意思 |
|---|---|
| `br`、`b`、`r` | 三张表的**别名**（borrow_record → br，book → b，reader → r） |
| `b.title as book_title` | book 表的 title 列，**起个别名**叫 book_title |
| `r.name as reader_name` | reader 表的 name 列，**起个别名**叫 reader_name |
| `from borrow_record br` | **主表**是 borrow_record |
| `left join book b on br.book_id = b.id` | 拿 book_id 去 book 表找书名 |
| `left join reader r on br.reader_id = r.id` | 拿 reader_id 去 reader 表找读者名 |
| `order by br.id desc` | 按记录 id **倒序**（最新的借阅在前） |

**两个关键点**：

1. **为什么要 `as book_title` 起别名？** 因为 book 表的书名列叫 `title`，reader 表的姓名列叫 `name`。不重命名的话，查出来两个表可能撞名、分不清；起了别名 `book_title` 后，配合驼峰映射（application.properties 里已开 `map-underscore-to-camel-case=true`），`book_title` 自动对应 VO 类的 `bookTitle` 字段。

2. **为什么用 `left join` 不用 `inner join`？** `left join` 意思是"**主表 borrow_record 的每条记录都要保留**，副表（book/reader）匹配不上就填 null"。虽然外键约束保证了 book_id/reader_id 一定存在（其实 inner join 也查得到），但 left join 更**稳**——万一将来删了某本书，借阅记录还在、书名显示 null，而不是整条记录消失。

### 3. 改 `BorrowService.java`（加转发）

**📁 改：`src/main/java/com/example/springbootdemo/BorrowService.java`**

加方法 + import `java.util.List;`：

```java
public List<BorrowRecordVO> findAllRecords() {
    return borrowRecordMapper.findAll();
}
```

### 4. 改 `BorrowController.java`（加接口）

**📁 改：`src/main/java/com/example/springbootdemo/BorrowController.java`**

加方法 + import `org.springframework.web.bind.annotation.GetMapping;`：

```java
// 查所有借阅记录（带书名 + 读者名）
@GetMapping("/borrows")
public Result listRecords() {
    return Result.success(borrowService.findAllRecords());
}
```

---

## 四、测试

**重启服务**，在 test.http 里加：

```http
### 查所有借阅记录（带书名 + 读者名）
GET http://localhost:8080/borrows
```

**期望返回**（大致长这样）：

```json
{
  "code": 200,
  "msg": "成功",
  "data": [
    {
      "id": 1,
      "bookId": 1,
      "readerId": 1,
      "borrowDate": "2026-08-19",
      "returnDate": null,
      "bookTitle": "百年孤独",
      "readerName": "王五"
    }
  ]
}
```

看 `bookTitle` 和 `readerName` 这两列——它们**不是 borrow_record 表里的**，是 JOIN 拼出来的。**这就是 VO 的价值**：把编号翻译成了人能看懂的名字。

---

## 五、总结

| 概念 | 一句话 |
|---|---|
| **JOIN 多表** | 拿编号去别的表把名字查回来，拼成一张完整的表 |
| **VO 类** | 装"JOIN 拼出来的视图"的类，字段比单表多，专门给前端看 |
| **别名 `as`** | 给撞名的列重命名，配合驼峰映射落到 VO 字段 |
| **left join** | 主表记录全保留，副表匹配不上就 null，不丢记录 |

---

## 怎么用（一句话）

查借阅记录要显示书名/读者名 → 三表 `left join`，结果字段比单表多 → 建个 **VO 类**装，`as` 别名 + 驼峰映射自动落到字段。
