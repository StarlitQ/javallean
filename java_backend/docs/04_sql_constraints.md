# 04 · 约束 —— 给数据"立规矩"

> 目标：学会用约束保证数据正确、完整，理解主键和外键。
> 前置：读完《01 · SQL 入门》~《03 · SQL 函数》，会建表、增删改查。

---

## 零、约束是什么？为什么要约束

一句话：**约束 = 给字段"立规矩"，防止你存进去错误的数据。**

比如：年龄不能是负数、身份证不能重复、名字不能为空、员工所属的部门必须存在……这些"规矩"如果只靠脑子记，迟早出错；把它写进数据库的**约束**里，数据库就会**自动把不符合规矩的数据挡在门外**。

**你还记得之前自己纠结的事吗？** 你试过往 age 里塞 -1，后来用 `INT UNSIGNED` 拦住了负数——那其实就是"约束"的思想。约束就是这套思想的正规化、系统化。

**类比**：约束是字段的"保安"。不符合规定的数据想进门，直接被拦下、报错。

---

## 一、非空约束 NOT NULL

作用：这个字段**不能为 NULL**（必须填）。

```sql
CREATE TABLE employee (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,   -- 名字不能为空
    age INT
);
```

插入时不给 name，就直接报错：`Column 'name' cannot be null`。

---

## 二、唯一约束 UNIQUE

作用：这个字段的值**不能重复**。

```sql
CREATE TABLE employee (
    id INT PRIMARY KEY,
    idcard VARCHAR(18) UNIQUE   -- 身份证不能重复
);
```

插两条一样的身份证，第二条会被拦下：`Duplicate entry ... for key 'idcard'`。

---

## 三、主键约束 PRIMARY KEY（重点）

作用：**非空 + 唯一** 的组合，用来**唯一标识每一行**。

```sql
CREATE TABLE employee (
    id INT PRIMARY KEY   -- id 非空且唯一，是每条记录的身份
);
```

- **主键 = 每条记录的"身份证号"**，靠它就能精确定位到某一行。
- 一张表**有且只有**一个主键（可由多个字段组成"联合主键"，先知道有这回事）。
- 你之前见过的 `AUTO_INCREMENT` 就是跟主键搭配用的——主键自增，不用你手动填。

### 主键 PK vs 唯一 UNIQUE（易混点）

| | PRIMARY KEY | UNIQUE |
|--|-------------|--------|
| 能为 NULL 吗 | ❌ 不能 | ✅ 能（且多个 NULL 不冲突） |
| 一张表能几个 | **只有一个** | 可以有**多个** |
| 定位 | 表的"唯一标识" | 只是"这一列不许重复" |

一句话：**主键 = 非空 + 唯一 + 全表唯一一个，是身份标识；唯一约束 = 只是"别重样"，可以多个、可以为空。**

---

## 四、默认约束 DEFAULT

作用：插入时**没给这个字段值**，就用默认值。

```sql
CREATE TABLE employee (
    id INT PRIMARY KEY,
    status INT DEFAULT 1,          -- 不填就默认 1
    city VARCHAR(50) DEFAULT '北京'
);
```

```sql
INSERT INTO employee (id) VALUES (1);   -- status、city 没给，自动用默认值
```

---

## 五、检查约束 CHECK

作用：字段值必须**满足某个条件**（比如年龄不能为负）。

```sql
CREATE TABLE employee (
    id INT PRIMARY KEY,
    age INT CHECK (age >= 0 AND age <= 150)   -- 年龄限定在合理范围
);
```

> ⚠️ 注意：`CHECK` 在 **MySQL 8.0.16 之后才真正生效**，老版本写了也不拦。你装的 8.0.46 是支持的，放心用。

---

## 六、外键约束 FOREIGN KEY（重点，连接两张表）

作用：让**两张表建立关联**，保证"引用的一致"。

场景：有 `dept`（部门）表和 `employee`（员工）表，每个员工属于某个部门。员工表用 `dept_id` 记录部门，但这个 `dept_id` **必须是真实存在的部门**——不能填一个不存在的部门编号。

```sql
CREATE TABLE dept (
    id INT PRIMARY KEY,
    name VARCHAR(50)
);

CREATE TABLE employee (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    dept_id INT,
    CONSTRAINT fk_dept FOREIGN KEY (dept_id) REFERENCES dept(id)
);
```

拆开看：

- `FOREIGN KEY (dept_id)`：声明 employee 表的 `dept_id` 是**外键**
- `REFERENCES dept(id)`：它**引用** dept 表的 `id`
- `CONSTRAINT fk_dept`：给这个外键起个名字（可省略）

**效果**：

1. 插入员工时，`dept_id` 只能填 dept 表里**存在的** id，否则报错。
2. 要删除某个部门时，如果还有员工挂在它下面，**删不掉**（保护数据不悬空）。

> 外键是"**关系型**数据库"里"关系"二字的来源——它让表和表之间有了真正的关联，这正是你《02》里学 JOIN 连表的基础。

---

## 七、建表时加 vs 后期改

建表时直接写在字段后面（上面那些都是）。后期要加/删约束，用 `ALTER`：

```sql
-- 加非空
ALTER TABLE employee MODIFY name VARCHAR(100) NOT NULL;
-- 加唯一
ALTER TABLE employee ADD UNIQUE (idcard);
-- 删唯一（需要知道约束名/索引名）
ALTER TABLE employee DROP INDEX idcard;
-- 加外键
ALTER TABLE employee ADD CONSTRAINT fk_dept FOREIGN KEY (dept_id) REFERENCES dept(id);
-- 删外键
ALTER TABLE employee DROP FOREIGN KEY fk_dept;
```

---

## 八、总结表

| 约束 | 关键字 | 作用 | 一句话 |
|------|--------|------|--------|
| 非空 | `NOT NULL` | 不能为空 | 必须填 |
| 唯一 | `UNIQUE` | 不能重复 | 不许重样 |
| 主键 | `PRIMARY KEY` | 非空 + 唯一 | 每条记录的唯一身份 |
| 默认 | `DEFAULT` | 不填用默认值 | 缺省值 |
| 检查 | `CHECK` | 值满足条件 | 守规矩 |
| 外键 | `FOREIGN KEY` | 引用另一张表 | 引用必须存在 |

---

## 九、动手练习

1. 建一张带主键、非空、唯一、默认值的表（把六种约束尽量都用上）。
2. 故意插一条**违反唯一约束**的数据，看报什么错。
3. 故意插一条**违反非空约束**的数据，看报什么错。
4. 建 `dept` + `employee` 两张表，用外键关联；插一条"属于不存在部门"的员工，看报什么错。
5. 试着删一个"下面还有员工"的部门，看能不能删掉。

> 每道题重点不是"做对"，而是**看报错信息**——约束的报错信息就是它"保安"身份的证明，看懂报错你就懂约束了。
