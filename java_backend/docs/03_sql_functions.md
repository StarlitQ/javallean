# 03 · SQL 函数 —— 让数据"变形"的工具

> 目标：学会用 SQL 内置函数加工字符串、数字、日期，以及做条件判断。
> 前置：读完《01 · SQL 入门》《02 · SQL 进阶》，会增删改查 + 分组 + 建用户。

---

## 零、函数是什么？为什么需要它

一句话：**函数就是"查询时顺手加工一下"的工具——它不改变表里存的数据，只改变返回/显示的结果。**

比如你想：

- 把每个人的名字拼上称呼
- 把身份证中间几位打码
- 算出员工入职了多少天
- 判断库存够不够

这些"加工"也可以交给 Java 程序去做，但 SQL 函数能让你**在查询时就一步完成**，省事很多。

> ⚠️ 核心原则：函数是**只读加工**。`SELECT UPPER(name)` 只是查询时显示大写，表里的数据还是原来的，不会被改。

---

## 一、字符串函数（最常用，因为名字/工号/身份证都是字符串）

| 函数 | 作用 | 例子 |
|------|------|------|
| `CONCAT(s1, s2, ...)` | 拼接多个字符串 | `CONCAT('Hello', ' ', 'World')` → `Hello World` |
| `LOWER(s)` / `UPPER(s)` | 转小写 / 转大写 | `UPPER('abc')` → `ABC` |
| `LPAD(s, len, pad)` / `RPAD` | 左/右填充到指定长度 | `LPAD('7', 3, '0')` → `007` |
| `TRIM(s)` | 去掉首尾空格 | `TRIM('  张三  ')` → `张三` |
| `SUBSTRING(s, start, len)` | 截取子串（从 start 开始取 len 个） | `SUBSTRING('123456', 1, 3)` → `123` |
| `LEFT(s, n)` / `RIGHT(s, n)` | 取左边/右边 n 个字符 | `LEFT('123456', 2)` → `12` |
| `REPLACE(s, a, b)` | 把 s 里的 a 替换成 b | `REPLACE('abcabc', 'a', 'x')` → `xbcxbc` |

例子（配合 employee 表）：

```sql
-- 名字后面拼上称呼
SELECT name, CONCAT(name, ' 你好') FROM employee;

-- 身份证脱敏：只显示前 6 位，后面全打码
SELECT idcard, CONCAT(LEFT(idcard, 6), '************') FROM employee;

-- 工号补 0 到 5 位
SELECT workno, LPAD(workno, 5, '0') FROM employee;
```

> 函数可以**嵌套**：`CONCAT(LEFT(idcard,6), '***')` 就是"先 LEFT 取前 6 位，再 CONCAT 拼上星号"。

---

## 二、数值函数

| 函数 | 作用 | 例子 |
|------|------|------|
| `CEIL(x)` | 向上取整（往大取） | `CEIL(3.1)` → `4` |
| `FLOOR(x)` | 向下取整（往小取） | `FLOOR(3.9)` → `3` |
| `MOD(x, y)` | 取余 | `MOD(10, 3)` → `1` |
| `ROUND(x, d)` | 四舍五入到 d 位小数 | `ROUND(3.14159, 2)` → `3.14` |
| `RAND()` | 返回 0~1 的随机数 | `RAND()` → `0.xxx` |

例子：

```sql
SELECT ROUND(3.14159, 2);          -- 3.14
SELECT CEIL(3.1), FLOOR(3.9);      -- 4, 3
SELECT MOD(10, 3);                 -- 1（10 除以 3 余 1）
```

---

## 三、日期函数

| 函数 | 作用 | 例子 |
|------|------|------|
| `CURDATE()` | 当前日期 | `CURDATE()` → `2026-08-14` |
| `CURTIME()` | 当前时间 | `CURTIME()` → `14:30:00` |
| `NOW()` | 当前日期 + 时间 | `NOW()` |
| `YEAR(d)` / `MONTH(d)` / `DAY(d)` | 提取年/月/日 | `YEAR('2020-01-01')` → `2020` |
| `DATE_ADD(d, INTERVAL n unit)` | 日期加 n 个单位 | `DATE_ADD(NOW(), INTERVAL 7 DAY)` |
| `DATEDIFF(d1, d2)` | 两个日期差几天 | `DATEDIFF('2026-01-01', '2020-01-01')` |

例子（配合 employee 表的 entrydate 入职日期）：

```sql
-- 入职年份
SELECT name, entrydate, YEAR(entrydate) FROM employee;

-- 入职到今天一共多少天
SELECT name, entrydate, DATEDIFF(CURDATE(), entrydate) FROM employee;
```

> 小技巧：`SELECT NOW();` 这种不用 FROM 表的也能直接跑，MySQL 允许。想快速看"现在几点"就用它。

---

## 四、流程函数（会"判断"的函数）

### 1. IF —— 像三目运算符

```sql
IF(条件, 条件为真时返回, 条件为假时返回)

SELECT name, age, IF(age >= 18, '成年', '未成年') FROM employee;
```

### 2. IFNULL —— 专门处理 NULL

```sql
IFNULL(可能为空的值, 为空时显示什么)

SELECT name, IFNULL(idcard, '无') FROM employee;   -- idcard 是 NULL 就显示"无"
```

> 还记得吗：NULL 不能用 `=` 判断。`IFNULL` 就是专门用来给"空值"兜底的。

### 3. CASE WHEN —— 多条件判断（像 if-else 链 / switch）

```sql
SELECT name, age,
    CASE
        WHEN age < 18 THEN '未成年'
        WHEN age < 60 THEN '成年人'
        ELSE '老年人'
    END AS '年龄段'
FROM employee;
```

- 从上往下找第一个成立的 `WHEN`，返回对应的 `THEN`。
- 都不成立就走 `ELSE`。
- `END` 结束，`AS` 给结果列起名。

---

## 五、动手练习

用你自己的 employee 表，敲一敲看结果：

1. 名字拼称呼：`SELECT name, CONCAT(name, ' 您好') FROM employee;`
2. 身份证脱敏：只显示前 6 位 + 后 2 位，中间打码
3. 入职到今天多少天：`SELECT name, DATEDIFF(CURDATE(), entrydate) FROM employee;`
4. 按年龄打标签：`CASE WHEN` 分未成年 / 青年 / 中年 / 老年
5. 处理空 idcard：`SELECT name, IFNULL(idcard, '未填写') FROM employee;`

---

## 怎么用（一句话）

函数跟在 `SELECT` 后面，对**列或值**做加工。核心记住一句：**函数是"加工"不是"修改"——它只改变查询显示的结果，不碰表里的原始数据。** 学的时候不用背，知道"有这一类函数能干这事"，用到时查表即可。
