# 15 · 多表关联查询（一对一）—— 查用户带出部门名

> 目标：查 user 的时候，顺便把部门名字也带出来，而不是只给一个 deptId 数字。
> 前置：跑通了 14 的 CRUD，user 表有 dept_id 外键，dept 表存在。

---

## 零、这一步要干嘛

现在访问 `/users`，每个用户长这样：

```json
{"id": 2, "name": "xiaolan", "age": 20, "status": "0", "gender": "F", "deptId": 5}
```

你只看到 `deptId: 5` 这个数字，但 5 是哪个部门？不知道，还得自己去查 dept 表。

这次要让它变成：

```json
{"id": 2, "name": "xiaolan", "age": 20, "status": "0", "gender": "F", "deptId": 5, "deptName": "development"}
```

多了个 `deptName: "development"`，一眼就知道是"开发部"。

### 📋 这一步只动 2 个文件（Service、Controller 都不用动）

| 文件 | 干什么 |
|---|---|
| `User.java` | 加一个 deptName 字段 |
| `UserMapper.java` | 把查询 SQL 从 `select *` 改成 JOIN 版 |

为什么 Service、Controller 不用动？因为 Mapper 里的方法名（`findById`、`findAll`）没变，只是它的 SQL 升级了。

---

## 一、先看两张表的关系（你的真实数据）

**user 表**（里面只存了 dept_id 这个"外键数字"）：

| id | name | age | dept_id |
|---|---|---|---|
| 1 | alice | 24 | 2 |
| 2 | xiaolan | 20 | 5 |
| 8 | bob | 28 | 2 |
| 9 | carol | 25 | 3 |

**dept 表**（部门名字在这）：

| id | name |
|---|---|
| 2 | management |
| 3 | finance |
| 5 | development |

关系：`user.dept_id` 指向 `dept.id`（外键）。所以 user 表里只存了个数字 2，真正的名字 "management" 在 dept 表里。

想拿到名字，就得**跨表查**——把两张表"拼"起来，这就是 **JOIN**（连接）。你 MySQL 阶段学过，现在搬到 MyBatis 里用。

---

## 二、动手改（两步）

### ① User.java 加一个字段

**📁 写在：`src/main/java/com/example/springbootdemo/User.java`**

加一个 `deptName` 字段。注意：它**不是** user 表里的列，而是查询时 JOIN 拼出来的"临时结果列"，专门用来装部门名：

```java
private Integer deptId;      // 原有（user 表的 dept_id 列）
private String deptName;     // 新增（不是表里的列，是 JOIN 查出来的部门名）

// getter / setter 用 IDEA 的 Alt+Insert 生成
public String getDeptName() { return deptName; }
public void setDeptName(String deptName) { this.deptName = deptName; }
```

### ② UserMapper.java 改 SQL

**📁 写在：`src/main/java/com/example/springbootdemo/UserMapper.java`**

把 `findById` 和 `findAll` 的 SQL，从 `select * from user` 改成 JOIN 版：

```java
// 查一个 + 部门名
@Select("select u.id, u.name, u.age, u.status, u.gender, u.dept_id, d.name as dept_name " +
        "from user u left join dept d on u.dept_id = d.id where u.id = #{id}")
User findById(Integer id);

// 查所有 + 部门名
@Select("select u.id, u.name, u.age, u.status, u.gender, u.dept_id, d.name as dept_name " +
        "from user u left join dept d on u.dept_id = d.id")
List<User> findAll();
```

改完重启，访问 `/users` 或 `/user/2`，就能看到 `deptName` 了。

---

## 三、这段 SQL 在说啥（逐句拆）

```
from user u left join dept d on u.dept_id = d.id
```

- `user u`、`dept d`：给两张表各起个别名（因为两张表都有 id、name，得起别名区分）；
- `left join dept d`：把 dept 表"拼"到 user 表右边，**以 user 为主**；
- `on u.dept_id = d.id`：拼接条件——user 的 dept_id 和 dept 的 id 对上，就拼成一行；
- `d.name as dept_name`：dept 表的 name 列，起个别名 `dept_name`（不然会和 user 的 name 撞名）；
- `left join`（左连接）：**左表（user）全部保留**，dept 匹配不上就填 NULL。这样就算某用户 dept_id 为空，他也会出现在结果里（只是 deptName 是 null）。

最后 `dept_name` 这个结果列，靠你之前配的**驼峰映射**（`map-underscore-to-camel-case=true`），自动装进 User 的 `deptName` 字段——你啥都不用管。

---

## 四、为什么不能写 select * 了？（重点）

原来单表查，可以偷懒写 `select *`。但 JOIN 两张表后**绝对不能**：

因为 user 表有 id、name，dept 表也有 id、name——`select *` 会返回**两个 id、两个 name**，字段名重复，MyBatis 分不清哪个是哪个，直接乱套。

所以 JOIN 时必须：

1. **显式列出**要哪些列（`u.id, u.name, ...`）；
2. **起别名**（`u`、`d`）区分哪列是哪个表的；
3. **重名的列用 as 改名**（`d.name as dept_name`）。

---

## 五、left join 还是 inner join？

你 MySQL 阶段学过，呼应一下：

| | 结果 |
|---|---|
| `inner join` | 只保留**两边都对上**的行 |
| `left join` | **左表全部保留**，右表匹配不上填 NULL |

你的数据里所有 dept_id 都能对上，两种结果一样。但**推荐 left join**——更安全：万一将来有个用户 dept_id 是空（没部门），inner join 会把他整行丢掉，left join 则保留他（deptName 为 null）。

---

## 六、动手练习

1. `User.java` 加 `deptName` + getter/setter；
2. `UserMapper.java` 的 `findById` / `findAll` 改成 JOIN 版；
3. 重启，访问：
   - `http://localhost:8080/users` → 每个用户多了 `deptName`
   - `http://localhost:8080/user/2` → xiaolan 的 deptName 是 `development`

> 这一步你会直观感受到：**后端"多表关联查询"的本质，就是把两张表用外键拼起来，一次拿全所有信息，不让前端（或用户）自己再查一次。**

---

## 怎么用（一句话）

多表查询 = **JOIN 拼表 + 起别名 + 重名列用 as 改名**。查谁就显式列出谁，用 left join 保证左表不丢。拼出来的新列（dept_name）靠驼峰映射自动装进实体类新加的字段（deptName）。
