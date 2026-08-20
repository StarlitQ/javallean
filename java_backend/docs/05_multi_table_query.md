# 05 · 多表查询 —— 把分散的数据连起来

> 目标：学会内连接、外连接、自连接、子查询、联合查询，能从多张表里联合取数。
> 前置：读完《04 · 约束》，理解主键和外键。

---

## 零、为什么需要多表查询

现实里数据不会全塞一张表：部门存 `dept`、员工存 `user`，用外键 `dept_id` 关联。现在要"查出每个员工 + 他所在部门的名字"，就得把两张表**连起来查**——这就是多表查询。

> 多表查询是外键的"用武之地"：外键建立了表间关系，多表查询就用这个关系，把分散的数据合起来看。

---

## 一、先懂多表关系（外键到底怎么连）

| 关系 | 例子 | 怎么实现 |
|------|------|---------|
| **一对多**（最常见） | 一个部门有多个员工 | 在"多"的一方（user）加外键 `dept_id` |
| **多对多** | 一个学生选多门课，一门课有多个学生 | 加一张**中间表**（两个外键） |
| **一对一** | 一个人对应一个身份证详情 | 外键 + `UNIQUE`（保证唯一） |

你现在手上的 `dept` + `user` 就是**一对多**：dept 是"一"，user 是"多"。

---

## 二、笛卡尔积：直接连表的"坑"

如果直接写（**不写连接条件**）：

```sql
SELECT * FROM user, dept;
```

结果 = **两表行数相乘**：每个员工 × 每个部门，产生一大堆**无意义的组合**。比如 3 个员工 × 3 个部门 = 9 行，其中大部分是"张冠李戴"（张三配了根本不存在的部门）。

这个"全都两两组合"的结果叫**笛卡尔积**。所以多表查询**必须写连接条件**，把真正相关的行连起来。

---

## 三、内连接 INNER JOIN（只留"对得上"的）

### 隐式内连接（老写法）

```sql
SELECT u.name, d.name FROM user u, dept d WHERE u.dept_id = d.id;
```

### 显式内连接（推荐，更清晰）

```sql
SELECT u.name, d.name FROM user u INNER JOIN dept d ON u.dept_id = d.id;
```

- `INNER` 可以省略，直接写 `JOIN`。
- `ON u.dept_id = d.id` 就是连接条件（外键 = 主键）。
- **只返回两边都匹配的行**；没部门的员工（dept_id 对不上）不会出现。

---

## 四、外连接 LEFT/RIGHT JOIN（保留"对不上"的一边）

内连接会丢掉"匹配不上"的行。如果你想**保留某一边的全部**，用外连接。

### 左外连接 LEFT JOIN（左表全要）

```sql
SELECT u.name, d.name FROM user u LEFT JOIN dept d ON u.dept_id = d.id;
```

- **左表（user）所有行都保留**；右表（dept）匹配不上就填 `NULL`。
- 经典用法：查出"**没有部门的人**"（右表为 NULL 的就是）：

```sql
SELECT u.name
FROM user u
LEFT JOIN dept d ON u.dept_id = d.id
WHERE d.id IS NULL;
```

### 右外连接 RIGHT JOIN（右表全要）

想保留右表全部，就换 `RIGHT JOIN`，逻辑镜像。日常**左连接用得更多**，右连接了解即可。

---

## 五、自连接 SELF JOIN（表连自己）

场景：员工表里有个 `manager_id` 列（上级的 id），想查"**每个员工 + 他上级的名字**"。上级也是员工，存在同一张表里，所以表要**自己连自己**。

```sql
SELECT e.name AS 员工, m.name AS 上级
FROM user e LEFT JOIN user m ON e.manager_id = m.id;
```

- 关键：同一张表起**两个别名**（e、m），当成两张表来连。
- `LEFT JOIN` 保证"没有上级的员工（比如老板）"也显示，上级填 NULL。
- 本质还是内/外连接，只是"自己连自己"而已。

---

## 六、子查询（查询里套查询）

### 1. 标量子查询（返回**一个值**）

```sql
SELECT * FROM user WHERE age > (SELECT AVG(age) FROM user);
```

括号里算出"平均年龄"一个值，外面拿它当条件。

### 2. 列子查询（返回**一列**，配 `IN`）

```sql
SELECT * FROM user WHERE dept_id IN (SELECT id FROM dept WHERE name LIKE '%开发%');
```

括号里返回一列 id，外面用 `IN` 判断"dept_id 在不在这一列里"。

### 3. 表子查询（返回**一张表**，当临时表用）

```sql
SELECT * FROM (SELECT * FROM user WHERE age > 20) AS t WHERE t.gender = 'M';
```

括号里查出一张表，起别名 `t`，外面再对这张临时表继续查。

> 子查询能不用尽量别乱嵌套——它可读性差、有时还慢。**多表查询首选 JOIN，子查询是"另一种思路"的补充工具。**

---

## 七、联合查询 UNION（上下拼接）

`JOIN` 是**左右**连（加列），`UNION` 是**上下**拼（加行）。

```sql
SELECT name, age FROM user WHERE age < 25
UNION
SELECT name, age FROM user WHERE gender = 'M';
```

- 上下两个查询的**列数、类型要一致**。
- `UNION` 会**去重**；`UNION ALL` **不去重**（保留全部，更快）。

---

## 八、总结：JOIN 家族一句话

| 连接 | 一句话 |
|------|--------|
| 内连接 `JOIN` | 只留对得上的 |
| 左外连接 `LEFT JOIN` | 左表全留，右表对不上填 NULL |
| 右外连接 `RIGHT JOIN` | 右表全留 |
| 自连接 `SELF JOIN` | 自己连自己（靠别名） |
| 子查询 | 查询里套查询 |
| `UNION` | 结果上下拼接 |

---

## 九、动手练习（用你的 dept + user 表）

1. 查出每个员工的名字 + 他所在部门的**名字**（内连接）。
2. 查出**所有**员工（包括没部门的），没部门的部门名显示 NULL（左连接）。
3. 查出"没有部门"的员工（左连接 + WHERE 右表为 NULL）。
4. 查出年龄大于平均年龄的员工（标量子查询）。
5. 查出"开发"类部门下的所有员工（列子查询 + IN）。

> 练习时重点体会：**内连接 vs 左连接**的区别（能不能看到"匹配不上"的行），以及 **JOIN vs 子查询**两种思路的取舍。这两点搞懂了，多表查询就通了。
