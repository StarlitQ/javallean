# 20 · 读者模块（Reader）—— 这次你自己写

> 目标：给 reader 表做 CRUD。跟 Book 模块 **100% 同套路**，只是字段换了。
> 前置：跑通了 19 的图书模块。

---

## 零、这一步要干嘛

读者模块 = reader 表增删改查。跟图书模块**一模一样**，只是字段从 `title/author/status` 换成 `name/gender/phone`。

**这次我不给完整代码了，你自己写**——你已经完整写过 User 和 Book 两遍 CRUD，套路该内化了。写完发我，我帮你看（照 feedback：先确认对不对，再提改进）。

---

## 一、reader 表长啥样（你 18 建的）

| 字段 | 类型 | 说明 |
|---|---|---|
| id | INT 主键自增 | 读者编号 |
| name | VARCHAR(20) | 姓名 |
| gender | CHAR(1) | 性别 |
| phone | VARCHAR(20) | 电话 |

> 注意：这张表**没有外键**，所以 Reader 实体类就是纯 4 个字段，比 Book 还简单。

---

## 二、你要写的 4 个文件（都在 `com.example.springbootdemo` 包下）

| 文件 | 干什么 | 照着谁改 |
|---|---|---|
| `Reader.java` | 实体类：4 字段 + getter/setter | 照 Book.java |
| `ReaderMapper.java` | 4 句 SQL（增删改查） | 照 BookMapper.java |
| `ReaderService.java` | 转发给 Mapper | 照 BookService.java |
| `ReaderController.java` | 4 个接口，统一 Result | 照 BookController.java |

哈哈哈哈哈哈

---

## 四、几个提醒（都是你踩过或见过的坑）

1. **SQL 占位符别写空**：`#{name}` 不是 `#{}`（你 19 踩的坑）；
2. **update / delete 返回 `int`**（影响行数），Controller 里要判断 `count > 0`；
3. **统一用 `Result` 返回**，别直接 return 对象或字符串；
4. 写完记得**重启服务**再测（你之前还忘过一次）。

---

## 五、写完自测（test.http 里加）

```http
### 查所有读者
GET http://localhost:8080/readers

### 新增读者
POST http://localhost:8080/reader
Content-Type: application/json

{
  "name": "王五",
  "gender": "M",
  "phone": "13900000004"
}

### 修改 id=1 的读者
PUT http://localhost:8080/reader/1
Content-Type: application/json

{
  "name": "张语钦",
  "gender": "M",
  "phone": "13800000001"
}

### 删除 id=3 的读者
DELETE http://localhost:8080/reader/3
```

---

## 六、写完发我

把 4 个文件写完、测通增删改查，然后跟我说一声。我会读你的代码帮你 review（先看对不对，再看能不能更好）。卡住了也随时喊我，别硬憋。

---

## 怎么用（一句话）

第二遍 CRUD 套路：实体类 → Mapper → Service → Controller 四件套，照 Book 原样改字段名和表名就行。写熟了这个套路，以后任何一张表都难不倒你。
