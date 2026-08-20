# 08 · SQL 优化 —— 让查询快起来

> 目标：学会"定位慢 SQL → 分析 → 优化"的完整思路，掌握几个高频优化技巧。
> 前置：读完 07 索引——索引是 SQL 优化的核心武器，没它后面都白搭。

---

## 零、优化的整体思路（先有方法论）

一句话：**优化不是瞎猜，是"先找出慢的 → 看它为什么慢 → 对症下药"。**

三步走：

1. **定位**：找出哪些 SQL 慢 → 慢查询日志
2. **分析**：看它慢在哪 → EXPLAIN（有没有走索引、type 是啥）
3. **优化**：对症下药 → 加索引 / 改写法 / 改表结构

> 绝大多数慢 SQL 的根因就一个：**没走索引，或索引失效了**。所以 07 学的索引知识，是这里每一招的弹药。

---

## 一、定位慢 SQL：慢查询日志

（前面讲过，这里串起来）

```sql
SHOW VARIABLES LIKE 'slow_query_log';      -- 开关（ON/OFF）
SHOW VARIABLES LIKE 'long_query_time';     -- 阈值，超过几秒算"慢"

SET GLOBAL slow_query_log = 'ON';          -- 开（重启失效）
SET GLOBAL long_query_time = 2;            -- 超过 2 秒就记

SHOW VARIABLES LIKE 'slow_query_log_file'; -- 日志文件在哪
```

> 生产环境里，慢查询日志是**排查性能问题的第一入口**：先把慢的揪出来，再逐个分析。

---

## 二、分析慢在哪：EXPLAIN（重点看 Extra）

07 里讲了 type 和 key 两列，这里补最关键的一列 **Extra**：

| Extra 值 | 意思 | 好不好 |
|---|---|---|
| `Using index` | 走了**覆盖索引**（不用回表） | ✅ 最好 |
| `Using where` | 用了 WHERE 过滤 | 🟡 正常 |
| `Using filesort` | **排序没走索引**，要额外排序 | ❌ 要优化 |
| `Using temporary` | 用了**临时表** | ❌ 要优化 |

> 记忆：**看到 `Using filesort` 或 `Using temporary`，就说明"排序/分组没蹭上索引"，是优化重点。**

---

## 三、覆盖索引（优化利器，面试常考）

**覆盖索引 = 查询用到的所有列，全都在索引里，不需要回表。**

回忆 07：辅助索引的叶子只存"索引列 + 主键值"。查到主键后，还要**回表**再查一遍整行。但如果 `SELECT` 的列恰好全在索引里，就**不用回表**，省一次磁盘 IO（Input/Output，输入输出），更快。

```sql
-- 假设有索引 (name, age)
SELECT name, age FROM user WHERE name = '张三';   -- 覆盖索引，不回表 ✅
SELECT *        FROM user WHERE name = '张三';   -- 要回表（* 里有索引外的列）❌
```

`Extra` 出现 `Using index`，就是覆盖索引生效的证据。

> 这也是"**别老用 SELECT \*，尽量只查需要的列**"的原因——除了省传输，还能蹭上覆盖索引。

---

## 四、几个高频优化技巧

### 1. 插入优化（大批量写数据时）

| 技巧 | 说明 |
|---|---|
| 批量插入 | 一条 SQL 插多行，比逐条插快 |
| 手动提交事务 | 大批量时关 `autocommit`，最后一起 `COMMIT` |
| 主键顺序插入 | 乱序插入会导致"页分裂"，慢（B+ 树重排） |

### 2. 排序 / 分组优化

`ORDER BY` / `GROUP BY` 的列，尽量用索引——因为**索引本身排好序了，等于白嫖排序**，不用再 filesort。

```sql
SELECT * FROM user ORDER BY age;   -- 若 age 有索引，直接白嫖 ✅
SELECT * FROM user ORDER BY name;  -- 若 name 无索引，会 filesort ❌
```

### 3. 深分页优化

```sql
SELECT * FROM user LIMIT 1000000, 10;   -- 深分页：要把前面 100 万行都数过去，慢
```

原因：`LIMIT offset, n` 会从头数到 offset 再取 n 行，offset 越大越慢。

优化思路：**记住上一页最后一条的主键 id，用 `WHERE id > 上页末id LIMIT n`** 来翻页（游标翻页），而不是用大 offset。

### 4. count 优化

- `count(*)`、`count(1)` 都算总行数，性能差不多
- `count(字段)` 会**忽略该字段为 NULL 的行**（之前学过）
- 大表精确 count 慢，可考虑用估算值或缓存

### 5. UPDATE / DELETE 优化（锁）

`UPDATE` / `DELETE` 的 `WHERE` **尽量带索引**。否则行锁会升级成**表锁**，锁住整张表，并发就废了。

```sql
UPDATE user SET age = 20 WHERE id = 1;        -- 走主键索引，只锁这行 ✅
UPDATE user SET age = 20 WHERE name = '张三'; -- name 无索引 → 锁整表 ❌
```

---

## 五、动手练习（用你的 user 表）

1. 开慢查询日志（`SET GLOBAL slow_query_log='ON'`），`SELECT SLEEP(3);` 造一条慢查询，去日志文件看它被记录没。
2. 给 `user` 的 `name` 建索引，`EXPLAIN SELECT name, age FROM user WHERE name='张三';` 看 Extra 是不是 `Using index`（覆盖索引）。
3. 故意 `EXPLAIN SELECT * FROM user ORDER BY name;`（name 无索引时），看是不是 `Using filesort`，再建索引对比。
4. 体会 `LIMIT 100000, 10` 和 `WHERE id > 100000 LIMIT 10` 的差异。

> 练习重点：**会用慢查询日志"定位"、用 EXPLAIN 的 Extra "分析"、再加索引"优化"**，把三步走串一遍。

---

## 怎么用（一句话）

SQL 优化 = **慢查询日志定位 → EXPLAIN 分析（看 type/key/Extra）→ 对症下药（加索引 / 覆盖索引 / 改写法）**。核心记住：**大多数慢的根子是"没走索引"，所以优化的第一反应永远是"看看 EXPLAIN 里走没走索引"。**
