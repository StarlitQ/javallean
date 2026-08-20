# 21 · 借书 / 还书（事务第一次实战）

> 目标：借书、还书。一次操作要**同时改两张表**，用 `@Transactional` 保证"要么都成功，要么都回滚"。
> 前置：19 图书模块 + 20 读者模块都通了，borrow_record 表（18 建的）已经存在。

---

## 零、这一步要干嘛

图书和读者的增删改查，都是"对一张表做一件事"，简单。但**借书**不一样：

你想象图书馆前台办事：

1. **查**这本书还在不在架子上（status 是不是 `'0'` 在馆）；
2. **改**这本书的状态 → `'1'`（已借出）；
3. **记**一条借阅记录（谁、哪本书、哪天借的）。

这 3 步**必须一起成功**。想想如果第 3 步登记到一半电脑死机了：书被标成"已借出"，但记录没登记上 → 书凭空丢了，账对不上。所以这 3 步要么全做、要么全不做——**这就是事务**（你在 17 学过概念，现在是第一次真用）。

**还书**同理，两步：找到那条"还没还"的记录填上还书日期 + 把书标回 `'0'` 在馆。

---

## 一、先想清楚：借书 / 还书各自要动哪几张表

| 操作 | 动 book 表 | 动 borrow_record 表 |
|---|---|---|
| 借书 | status `'0'` → `'1'` | 插一条记录（book_id, reader_id, borrow_date） |
| 还书 | status `'1'` → `'0'` | 给那条未归还记录填 return_date |

一张表改状态，一张表记/改记录，**跨两张表** → 必须包进事务。

---

## 二、这一步要动的文件

| 文件 | 动作 | 干什么 |
|---|---|---|
| `BookMapper.java` | **改**（加一个方法） | 加一个"只改 status"的方法 |
| `BorrowRecordMapper.java` | **新建** | 借阅记录的两句 SQL |
| `BorrowService.java` | **新建** | 借书 / 还书的业务逻辑 + `@Transactional` |
| `BorrowController.java` | **新建** | `/borrow`、`/return` 两个接口 |

> 注意：这次不建 `BorrowRecord` 实体类。因为借书/还书只是**往里写**，不需要把整条记录查出来返回给前端。（22 查借阅记录时才建实体类。）

---

## 三、新知识点（先讲清楚，不然代码看不懂）

这次有 **4 个新东西**，都是之前没碰过的。

### 1. `@Param` —— 方法有多个参数时，给它们"贴名字标签"

你之前 Mapper 里的方法，参数要么是**一个对象**（`insert(Book book)`），要么是**一个 id**（`findById(Integer id)`），MyBatis 一眼就能认出 `#{}` 里是谁。

但现在要写一个"改书的状态"的方法，得传**两个参数**：`id` 和 `status`：

```java
int updateStatus(Integer id, String status);
```

两个参数，MyBatis 就**分不清** SQL 里的 `#{...}` 到底指谁了。所以要用 `@Param("名字")` 给每个参数**贴标签**：

```java
int updateStatus(@Param("id") Integer id, @Param("status") String status);
//                 ↑标签叫 id        ↑标签叫 status
```

然后 SQL 里 `#{id}`、`#{status}` 就能对上号了。

**打比方**：@Param 就像快递包裹上贴的**收件人标签**。一个包裹贴"张三"、一个贴"李四"，快递员（MyBatis）才不会送错。不贴标签，它只知道"有两个包裹"，不知道谁是谁。

### 2. `LocalDate` —— 只表示"年月日"的日期类型

book 表的 `status` 是 CHAR，没问题；但 borrow_record 表的 `borrow_date`、`return_date` 是 **DATE 类型**（只有年月日，没有时分秒）。

Java 里对应 DATE 的类型是 `LocalDate`（Java 8 之后的标准日期类，你 JDK 25 完全支持）。

- `LocalDate.now()` → 今天（比如 `2026-08-19`）
- 比老的 `java.util.Date` 干净好用，直接用。

导入：`import java.time.LocalDate;`

### 3. `throw new RuntimeException("...")` —— 主动"扔"一个错误

17 里事务回滚是靠 **MySQL 外键报错**触发的。但实际业务里，很多错误 MySQL 根本不报——比如"这本书已经被借出去了"，MySQL 才不管你的业务规则。

这种"业务规则不满足"，得**我们自己主动告诉 Spring"出错了，快回滚"**。怎么告诉？**主动扔异常**：

```java
if ("1".equals(book.getStatus())) {
    throw new RuntimeException("这本书已经被借出去了");
}
```

- `throw` = 主动扔出去一个异常
- `new RuntimeException("...")` = 造一个"运行时异常"，括号里是你想说的话
- Spring 只要看到方法里**抛了异常**，就**自动回滚**整个事务

**打比方**：事务就像排雷。Spring 默认"顺利走完就收工"，但你要在发现雷时**主动喊一嗓子**（throw），它才知道"这地方不对，赶紧撤回（回滚）"。

### 4. Controller 里 `try-catch` 接住异常，别让它崩

你扔了异常，如果没人接，程序就报错崩溃、返回一大堆红色堆栈给前端。所以 Controller 里要用 `try-catch` **接住**：

```java
try {
    borrowService.borrow(bookId, readerId);
    return Result.success();          // 顺利 → 返回成功
} catch (RuntimeException e) {
    return Result.error(e.getMessage());  // 出错 → 把异常里的"人话"放进 Result 返回
}
```

- `e.getMessage()` = 你 throw 时括号里写的那句话（比如"这本书已经被借出去了"）
- 这样前端收到的不是崩溃，而是 `{"code":500, "msg":"这本书已经被借出去了", ...}` 的友好提示

---

## 四、完整代码

### 1. 改 `BookMapper.java`（加一个方法）

**📁 改：`src/main/java/com/example/springbootdemo/BookMapper.java`**

在接口里**加**下面这个方法（其余不动）。记得顶部 import 里加 `import org.apache.ibatis.annotations.Param;`：

```java
// 只改 status（借书改成 '1'，还书改成 '0'）
@Update("update book set status = #{status} where id = #{id}")
int updateStatus(@Param("id") Integer id, @Param("status") String status);
```

> 为什么不能复用之前的 `update(Book book)`？因为那个要把 title/author/status 全传一遍。这里只想改 status 一个字段，专门写个方法更干净。

### 2. 新建 `BorrowRecordMapper.java`

**📁 新建：`src/main/java/com/example/springbootdemo/BorrowRecordMapper.java`**

```java
package com.example.springbootdemo;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;

@Mapper
public interface BorrowRecordMapper {

    // 借书时：插一条借阅记录（return_date 先空着，等还书时填）
    @Insert("insert into borrow_record(book_id, reader_id, borrow_date) values(#{bookId}, #{readerId}, #{borrowDate})")
    void insert(@Param("bookId") Integer bookId,
                @Param("readerId") Integer readerId,
                @Param("borrowDate") LocalDate borrowDate);

    // 还书时：给"这本还没还"的记录填上还书日期
    @Update("update borrow_record set return_date = #{returnDate} where book_id = #{bookId} and return_date is null")
    int updateReturnDate(@Param("bookId") Integer bookId,
                         @Param("returnDate") LocalDate returnDate);
}
```

> 第二句 SQL 的关键是 `where ... and return_date is null`：还书时**只找"还没还"的那条**。如果这本书没有未归还记录，这个 update 影响 0 行，`count == 0`，就知道"这本书根本没借出去"。

### 3. 新建 `BorrowService.java`（核心）

**📁 新建：`src/main/java/com/example/springbootdemo/BorrowService.java`**

```java
package com.example.springbootdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class BorrowService {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BorrowRecordMapper borrowRecordMapper;

    // 借书：三步，必须同时成功 → @Transactional
    @Transactional
    public void borrow(Integer bookId, Integer readerId) {
        // 1. 查这本书，校验它在不在馆
        Book book = bookMapper.findById(bookId);
        if (book == null) {
            throw new RuntimeException("这本书不存在：id=" + bookId);
        }
        if ("1".equals(book.getStatus())) {
            throw new RuntimeException("这本书已经被借出去了");
        }

        // 2. 先把书标成"已借出"
        bookMapper.updateStatus(bookId, "1");

        // 3. 再登记借阅记录（如果 readerId 不存在，这里外键失败 → 第 2 步也回滚）
        borrowRecordMapper.insert(bookId, readerId, LocalDate.now());
    }

    // 还书：两步，必须同时成功 → @Transactional
    @Transactional
    public void returnBook(Integer bookId) {
        // 1. 给"还没还"的记录填上还书日期
        int count = borrowRecordMapper.updateReturnDate(bookId, LocalDate.now());
        if (count == 0) {
            throw new RuntimeException("这本书没有未归还的借阅记录");
        }

        // 2. 把书标回"在馆"
        bookMapper.updateStatus(bookId, "0");
    }
}
```

> **注意借书的顺序**：我故意把"改状态"放第 2 步、"插记录"放第 3 步。这样当你传一个**不存在的 readerId** 时，第 3 步外键失败、抛异常 → 事务回滚 → **第 2 步刚改的状态也撤销**。你就能亲眼看到"回滚"的效果了（见下面测试第 3 条）。

### 4. 新建 `BorrowController.java`

**📁 新建：`src/main/java/com/example/springbootdemo/BorrowController.java`**

```java
package com.example.springbootdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    // 借书：POST /borrow/{bookId}/{readerId}
    @PostMapping("/borrow/{bookId}/{readerId}")
    public Result borrow(@PathVariable Integer bookId, @PathVariable Integer readerId) {
        try {
            borrowService.borrow(bookId, readerId);
            return Result.success();
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    // 还书：PUT /return/{bookId}
    @PutMapping("/return/{bookId}")
    public Result returnBook(@PathVariable Integer bookId) {
        try {
            borrowService.returnBook(bookId);
            return Result.success();
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }
}
```

> 两个接口都只有路径参数（`{bookId}`、`{readerId}`），没有请求体，所以**不需要 @RequestBody**。

---

## 五、动手练习 + 测试

1. 改好 BookMapper，新建其余 3 个文件；
2. **重启服务**；
3. 在 test.http 里加，**按顺序**测（顺序很重要，因为会改状态）：

```http
### 1. 借书：bookId=1 借给 readerId=1
POST http://localhost:8080/borrow/1/1

### 2. 再借同一本（应该报错"已经被借出去了"）
POST http://localhost:8080/borrow/1/2

### 3. 借书，但读者不存在 readerId=999（外键失败 → 事务回滚）
POST http://localhost:8080/borrow/2/999

### 4. 查 bookId=2 的状态（应该还是 "0"，证明第 3 步回滚成功了）
GET http://localhost:8080/book/2

### 5. 还书：bookId=1
PUT http://localhost:8080/return/1

### 6. 还一本没借出的书（应该报错"没有未归还的记录"）
PUT http://localhost:8080/return/2
```

**验证要点**：

- 第 1 条：返回 `code 200` 成功；
- 第 2 条：返回 `code 500` + `"这本书已经被借出去了"`；
- 第 3 条：返回 `code 500`（外键错误）；
- **第 4 条最关键**：查 bookId=2，`status` 应该还是 `"0"`。如果第 3 步没有事务，书 2 会被误标成 `"1"` 借出——但因为回滚了，它还是 `"0"`。**这就是你亲眼看到的事务在起作用。**
- 第 5 条：成功；第 6 条：报错。

---

## 六、总结：这一步你真正收获了什么

| 以前 | 现在 |
|---|---|
| 一次操作只碰一张表 | 一次操作**跨两张表**，用 `@Transactional` 保证一致 |
| 错误靠 MySQL 报 | **自己 `throw`** 业务规则错误，主动触发回滚 |
| Mapper 方法参数 ≤ 1 个 | 多参数用 **`@Param`** 贴标签 |
| 用 String/Integer | 日期用 **`LocalDate`** |
| Controller 直接 return | 用 **try-catch** 接住异常，返回友好提示 |

这套"借书/还书 + 事务"是后端最经典的业务形态：**不是单纯的增删改查，而是有规则、有跨表操作、要保证一致性的真实业务**。你已经会写它了。

---

## 怎么用（一句话）

借书/还书 = 多步操作跨两张表，用 `@Transactional` 包起来；业务规则不满足就 `throw new RuntimeException`，Controller 用 try-catch 接住返回友好错误。
