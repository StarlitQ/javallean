# 23 · 分页 + 搜索（数据一多就得翻页、按关键字找）

> 目标：借阅记录多了以后，一次性全查回来太笨重，要能**翻页**（第几页、每页几条）和**按书名搜索**。
> 前置：22 的三表 JOIN 查借阅记录跑通了。

---

## 零、这一步要干嘛

现在借阅记录才几条，`findAll` 一下全查回来没问题。但真实图书馆可能有几万条借阅记录，**一次性全查回来**既慢又没用——用户只会一页一页看。

所以这一步做两件事：

1. **分页**：一次只查一页（比如每页 5 条，看第 2 页就看第 6~10 条）；
2. **搜索**：按书名模糊搜（输入"百年"，把书名含"百年"的找出来）。

---

## 一、分页原理：`LIMIT`

MySQL 里分页就靠一句 `LIMIT`：

```sql
select * from borrow_record limit 5, 5;
--                         ↑跳过5条   ↑取5条
```

`LIMIT offset, size`：**跳过前 `offset` 条，取 `size` 条**。

**打比方**：书架上有一排借阅单，`LIMIT 5, 5` 就是"**从第 6 张开始，拿 5 张**"（前 5 张跳过）。

那"第几页"怎么翻译成 offset？公式：

```
offset = (page - 1) * pageSize
```

- 第 1 页，每页 5 条 → offset = (1-1)×5 = **0** → 取第 1~5 条；
- 第 2 页，每页 5 条 → offset = (2-1)×5 = **5** → 取第 6~10 条；
- 第 3 页 → offset = 10 → 取第 11~15 条。

这个 `(page - 1) * pageSize` 是分页的**万能公式**，记下来。

---

## 二、新知识点：`@RequestParam`（第三种"参数来源"）

之前你用过两种拿参数的方式，现在来第三种：

| 注解 | 值从哪来 | 例子 |
|---|---|---|
| `@PathVariable` | URL **路径里** | `/book/{id}` → `id` 嵌在路径上 |
| `@RequestParam` | URL **问号后面** | `/borrows?page=1` → `page` 在 `?` 后面 |
| `@RequestBody` | 请求体 **JSON** | POST 的 `{ "title": "..." }` |

**打比方**：@PathVariable 是"地址里**嵌**的房号"；@RequestParam 是"地址后面 `?` 跟的**筛选选项**"（就像淘宝网址后面 `?keyword=手机&page=2`）；@RequestBody 是"**包裹里**的清单"。

分页和搜索的参数（page、pageSize、keyword）天然就该放在 URL 问号后面，所以用 `@RequestParam`：

```java
public Result listRecords(@RequestParam(defaultValue = "1") Integer page,
                          @RequestParam(defaultValue = "5") Integer pageSize,
                          @RequestParam(defaultValue = "") String keyword) {
```

> **`defaultValue` 是啥？** = "前端没传这个参数时，用这个默认值"。这样前端可以偷懒：
> - 不传 `page` → 默认第 1 页；
> - 不传 `pageSize` → 默认每页 5 条；
> - 不传 `keyword` → 默认空字符串（= 不过滤，查所有）。

---

## 三、搜索：`like` 模糊查询

要"书名里**包含**某几个字"的，用 `like` + 通配符 `%`：

```sql
where b.title like '%百年%'
```

- `%` 是**通配符**，代表"任意内容"（像扑克牌里的万能牌）；
- `'%百年%'` = "只要书名里**含有**'百年'这两个字，前后有啥都行"；
- `concat('%', #{keyword}, '%')` = 用 MySQL 的 `concat` 函数把 `%`、你搜的关键字、`%` **拼起来**（因为 `%` 不能直接写在 `#{}` 里，得拼）。

所以 `keyword="百年"` 时，`concat('%','百年','%')` = `'%百年%'`；`keyword=""` 时 = `'%%'` = **匹配所有**（等价于不过滤）。

---

## 四、完整代码（把 22 的 `findAll` 升级成 `search`）

> 为什么"升级"而不是"再加一个"？因为"查所有"只是"搜索"的特例：`keyword` 传空、`pageSize` 设大，就等于查所有了。一个接口搞定，更干净。

### 1. 改 `BorrowRecordMapper.java`

**📁 改：`src/main/java/com/example/springbootdemo/BorrowRecordMapper.java`**

把 22 的 `findAll()` **替换成**下面这个 `search`（其余 insert/updateReturnDate 不动）：

```java
@Select("select br.id, br.book_id, br.reader_id, br.borrow_date, br.return_date, " +
        "b.title as book_title, r.name as reader_name " +
        "from borrow_record br " +
        "left join book b on br.book_id = b.id " +
        "left join reader r on br.reader_id = r.id " +
        "where b.title like concat('%', #{keyword}, '%') " +
        "order by br.id desc " +
        "limit #{offset}, #{pageSize}")
List<BorrowRecordVO> search(@Param("keyword") String keyword,
                            @Param("offset") int offset,
                            @Param("pageSize") int pageSize);
```

> 相对 22 的 findAll，多了两行：
> - `where b.title like concat(...)` —— **搜索**（keyword 空时匹配所有）；
> - `limit #{offset}, #{pageSize}` —— **分页**。

### 2. 改 `BorrowService.java`

**📁 改：`src/main/java/com/example/springbootdemo/BorrowService.java`**

把 22 的 `findAllRecords()` 替换成：

```java
public List<BorrowRecordVO> search(String keyword, int offset, int pageSize) {
    return borrowRecordMapper.search(keyword, offset, pageSize);
}
```

### 3. 改 `BorrowController.java`

**📁 改：`src/main/java/com/example/springbootdemo/BorrowController.java`**

把 22 的 `listRecords()` 替换成（记得 import `org.springframework.web.bind.annotation.RequestParam;`）：

```java
@GetMapping("/borrows")
public Result listRecords(@RequestParam(defaultValue = "") String keyword,
                          @RequestParam(defaultValue = "1") Integer page,
                          @RequestParam(defaultValue = "5") Integer pageSize) {
    int offset = (page - 1) * pageSize;   // 万能分页公式
    return Result.success(borrowService.search(keyword, offset, pageSize));
}
```

> **关键一行** `int offset = (page - 1) * pageSize;`：把"第几页"翻译成"跳过几条"，交给 SQL 的 LIMIT。

---

## 五、测试

**重启服务**，在 test.http 里加：

```http
### 分页：第 1 页，每页 2 条
GET http://localhost:8080/borrows?page=1&pageSize=2

### 分页：第 2 页，每页 2 条
GET http://localhost:8080/borrows?page=2&pageSize=2

### 搜索：书名含"百年"
GET http://localhost:8080/borrows?keyword=百年

### 搜索 + 分页一起
GET http://localhost:8080/borrows?keyword=百年&page=1&pageSize=5

### 什么都不传（默认：第 1 页，每页 5 条，不过滤）
GET http://localhost:8080/borrows
```

**验证要点**：

- 第 1、2 条：传 `page=1` 和 `page=2`，返回的记录**不重复**、正好翻页；
- 第 3 条：只返回书名含"百年"的记录；
- 第 5 条：什么都不传也能查（默认值生效）。

---

## 六、总结

| 新知识 | 一句话 |
|---|---|
| `LIMIT offset, size` | 跳过 offset 条、取 size 条，实现分页 |
| `offset = (page-1)*pageSize` | 万能分页公式 |
| `@RequestParam` | 拿 URL 问号后面的参数（第三种参数来源） |
| `defaultValue` | 前端不传参数时的默认值 |
| `like '%关键字%'` | 模糊搜索，`%` 是通配符 |
| `concat('%', #{kw}, '%')` | 把通配符和关键字拼起来 |

---

## 怎么用（一句话）

分页 = `LIMIT` + `offset=(page-1)*pageSize`；搜索 = `like concat('%',关键字,'%')`；两者参数都用 `@RequestParam` 从 URL 问号后面拿。

---

> 🎉 **到这里，图书管理系统的核心功能全齐了**：图书 CRUD、读者 CRUD、借书/还书（事务）、借阅记录查询（三表 JOIN + 分页 + 搜索）。一个完整的后端小项目闭环跑通。下一阶段可以做：登录鉴权（JWT）、接口文档（Swagger）、部署上线——等你觉得这摊练熟了再往下走。
