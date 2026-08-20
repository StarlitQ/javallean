# 02 · SQL 进阶 —— 统计、分组、多表连接

> 目标：学会用聚合函数统计、GROUP BY 分组、JOIN 连多张表、LIMIT 分页。
> 前置：读完《01 · SQL 入门》，会用增删改查。

---

## 一、聚合函数：把一堆数据算成一个数

《01》里你见过 `COUNT(*)`——它把多行算成一个数。这类函数叫**聚合函数**，常用的五个：

| 函数 | 作用 | 例子 |
|------|------|------|
| `COUNT()` | 数有多少条 | `COUNT(*)` 一共几本 |
| `SUM()` | 求和 | `SUM(stock)` 总库存 |
| `AVG()` | 求平均 | `AVG(price)` 平均价格 |
| `MAX()` | 最大 | `MAX(price)` 最贵的 |
| `MIN()` | 最小 | `MIN(price)` 最便宜的 |

```sql
SELECT COUNT(*) FROM books;       -- 几本书
SELECT AVG(price) FROM books;     -- 平均价格
SELECT MAX(price) FROM books;     -- 最贵多少钱
SELECT SUM(stock) FROM books;     -- 总库存
```

> ⚠️ 聚合函数把"很多行"压成"一行一个值"，所以不能写成 `SELECT name, MAX(price)`——`name` 是一行一个，`MAX` 是一列一个，对不上。这个问题下面 GROUP BY 会解决。

---

## 二、GROUP BY：按某列分组统计

"每类各有多少"这种需求，就要分组。`GROUP BY` 把相同值的行分成一组，再对每组算聚合。

假设 `books` 多一列 `category`（分类）：

```sql
-- 每个分类各有几本书
SELECT category, COUNT(*) FROM books GROUP BY category;

-- 每个分类的平均价格
SELECT category, AVG(price) FROM books GROUP BY category;
```

理解顺序：**先 GROUP BY 分组 → 再对每组算聚合**。

---

## 三、多表查询 + JOIN：把两张表连起来

### 为什么一张表不够？

现实里数据不会全塞一张表。比如图书系统要两张：

**`authors` 表**（作者）：
| id | name | country |
|----|------|---------|
| 1  | 张三 | 中国 |
| 2  | John | 美国 |

**`books` 表**（书），用 `author_id` 指向作者：
| id | name | price | author_id |
|----|------|-------|-----------|
| 1  | Java 入门 | 59 | 1 |
| 2  | Spring 实战 | 89 | 1 |
| 3  | MySQL 实战 | 79 | 2 |

书和作者**分开存**，用 `books.author_id = authors.id` 关联。好处：作者信息改一次，所有书自动跟着对，不用每条都改。

### INNER JOIN：只查"两边都匹配"的

要列出"书名 + 作者名"，就把两张表连起来：

```sql
SELECT books.name, authors.name
FROM books
JOIN authors ON books.author_id = authors.id;
```

结果：

| books.name | authors.name |
|-----------|--------------|
| Java 入门 | 张三 |
| Spring 实战 | 张三 |
| MySQL 实战 | John |

拆开看：
- `JOIN authors ON 条件`：把 books 和 authors 按 `author_id = id` 连起来
- `SELECT books.name, authors.name`：分别从两张表取列（表名.列名）

### LEFT JOIN：左表全要，右表匹配不到就空着

如果某本书的作者被删了（`author_id` 找不到对应作者），`INNER JOIN` 会**把这一行丢掉**；`LEFT JOIN` 则**保留左表所有行**，右表匹配不到就填 `NULL`：

```sql
SELECT books.name, authors.name
FROM books
LEFT JOIN authors ON books.author_id = authors.id;
```

一句话区别：**INNER JOIN 只保留两边都对得上的；LEFT JOIN 保留左表全部，右表对不上填 NULL。**

---

## 四、LIMIT：取前 N 条 / 分页

```sql
SELECT * FROM books ORDER BY price DESC LIMIT 3;  -- 最贵的 3 本
SELECT * FROM books LIMIT 10 OFFSET 20;            -- 跳过 20 条，取 10 条（第 3 页）
```

---

## 五、动手练习

先建两张表、插数据，再连起来查：

```sql
CREATE TABLE authors (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    country VARCHAR(100)
);

CREATE TABLE books (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2),
    author_id INT
);

INSERT INTO authors(name, country) VALUES
    ('张三', '中国'), ('John', '美国'), ('李四', '中国');

INSERT INTO books(name, price, author_id) VALUES
    ('Java 入门', 59, 1), ('Spring 实战', 89, 1),
    ('MySQL 实战', 79, 2), ('算法导论', 99, 3);
```

然后自己敲，看结果：

1. `SELECT COUNT(*), AVG(price), MAX(price) FROM books;` —— 一次性看三个统计
2. `SELECT country, COUNT(*) FROM authors GROUP BY country;` —— 各国作者数
3. `SELECT books.name, authors.name FROM books JOIN authors ON books.author_id = authors.id;` —— 书名 + 作者名
4. `SELECT authors.name, COUNT(*) FROM authors LEFT JOIN books ON authors.id = books.author_id GROUP BY authors.name;` —— 每个作者出了几本书
5. `SELECT * FROM books ORDER BY price DESC LIMIT 2;` —— 最贵两本

> 在哪跑：在线练习场 [db-fiddle.com](https://www.db-fiddle.com)（选 MySQL 8.0），或本地 MySQL 装好后在 Workbench 里敲。
