# 01 · SQL 入门 —— 跟数据库"说话"

> 目标：学会用 SQL 对一张表做**增删改查（CRUD）**，能自己建一张 `books` 表并操作它。
> 前置：读完《00 · 后端全景》。

---

## 一、数据库里的数据长什么样？—— 表

数据库里的数据放在**表（table）**里。一张表就像一张 Excel：

| id | name | price | stock |
|----|------|-------|-------|
| 1  | Java 入门 | 59.0 | 30 |
| 2  | Spring Boot 实战 | 89.0 | 12 |
| 3  | MySQL 实战 | 79.0 | 100 |

- 每一**列（column）** = 一种属性（书名、价格、库存）
- 每一**行（row）** = 一条记录（一本书）
- 一个数据库里可以有很多张表

---

## 二、SQL 就是跟表"说话"的语言

SQL（Structured Query Language）就是对数据库下命令的语言。核心是四个操作，合称 **CRUD**：

| 操作 | 关键词 | 大白话 |
|------|--------|--------|
| 增 Create | `INSERT` | 加一条记录 |
| 查 Read | `SELECT` | 查数据（**最常用**） |
| 改 Update | `UPDATE` | 改某条记录 |
| 删 Delete | `DELETE` | 删某条记录 |

下面用 `books` 表，逐个学。

---

## 三、建表 `CREATE TABLE`

先建表：

```sql
CREATE TABLE books (
    id    INT PRIMARY KEY AUTO_INCREMENT,  -- 编号，主键，自动增长
    name  VARCHAR(100) NOT NULL,           -- 书名，最长100字符，不能为空
    price DECIMAL(10, 2),                  -- 价格，小数（共10位，2位小数）
    stock INT DEFAULT 0                    -- 库存，默认 0
);
```

几个概念先混个脸熟（不用背，用多了自然记住）：
- `PRIMARY KEY` 主键：每条记录的唯一编号，一张表必须有
- `AUTO_INCREMENT` 自增：id 不用你填，自动 1、2、3…
- `VARCHAR(n)` 变长字符串、`INT` 整数、`DECIMAL` 小数

---

## 四、增 `INSERT` —— 加书

```sql
-- 一次加一本书
INSERT INTO books(name, price, stock) VALUES ('Java 入门', 59.0, 30);

-- 一次加多本
INSERT INTO books(name, price, stock) VALUES
    ('Spring Boot 实战', 89.0, 12),
    ('MySQL 实战', 79.0, 100);
```

> 注意：`id` 没写——因为它是自增的，数据库自动填。

---

## 五、查 `SELECT` —— 找书（重点！）

### 查所有

```sql
SELECT * FROM books;   -- * 代表"所有列"
```

### 查指定列

```sql
SELECT name, price FROM books;   -- 只查书名和价格
```

### 加条件 `WHERE`（筛选）

```sql
SELECT * FROM books WHERE price < 80;          -- 价格小于80的书
SELECT * FROM books WHERE name = 'Java 入门';  -- 书名正好是这本
SELECT * FROM books WHERE name LIKE '%Java%';  -- 书名里含"Java"的（% 是任意字符）
```

### 排序 `ORDER BY`

```sql
SELECT * FROM books ORDER BY price DESC;   -- 按价格从高到低（DESC 降序）
SELECT * FROM books ORDER BY price ASC;    -- 从低到高（ASC 升序，可省略）
```

### 计数 `COUNT`

```sql
SELECT COUNT(*) FROM books;   -- 一共有几本书
```

---

## 六、改 `UPDATE` —— 改书

```sql
-- 把 id=1 的书价格改成 49
UPDATE books SET price = 49.0 WHERE id = 1;

-- 把库存全部 +10（没写 WHERE 就是改所有行！）
UPDATE books SET stock = stock + 10;
```

> ⚠️ 牢记：`UPDATE` 和 `DELETE` 一定要带 `WHERE`，否则会把**整张表**都改了/删了。

---

## 七、删 `DELETE` —— 删书

```sql
DELETE FROM books WHERE id = 2;   -- 删掉 id=2 那本
```

---

## 八、动手练习

按顺序自己敲一遍，观察结果：

1. 建表 `CREATE TABLE`
2. 插入 3 本书 `INSERT`
3. 查所有 `SELECT * FROM books`
4. 查价格 < 80 的书 `WHERE price < 80`
5. 按价格降序 `ORDER BY price DESC`
6. 把某本书改价 `UPDATE`
7. 删掉一本书 `DELETE`
8. 再查一次，确认变化

---

## 怎么跑这些 SQL？

两种方式，任选：

- **在线练习场**（零安装，立刻能玩）：打开 [sqliteonline.com](https://sqliteonline.com) 或 [db-fiddle.com](https://www.db-fiddle.com)，左边写 SQL，点 Run 就能看到表。适合先熟悉语法。
- **本地 MySQL**（正式，后面 Spring Boot 要用）：装好 MySQL + Workbench 后，在 Workbench 里写 SQL。

> 建议：先用在线练习场把语法练熟，等装好本地 MySQL 再迁过去（语法几乎一样）。
