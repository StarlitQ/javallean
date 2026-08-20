# 18 · 项目启动：图书管理系统 —— 数据库设计

> 从这篇开始，进入**项目实战**。把你学过的 CRUD、多表查询、事务、统一响应，串成一个完整的小系统。
> 前置：跑通了 17 的事务。

---

## 零、这个项目要做什么

做一个**图书管理系统**，核心就三件事：

1. **管书**：图书的增删改查（书名、作者、在馆状态）
2. **管读者**：读者的增删改查
3. **借书 / 还书**：读者借书、还书（这是核心业务，用到事务 + 多表查询）

一句话的业务闭环：

> 读者来借一本书 → 系统记一条"借阅记录"，并把书标成"已借出"；读者还书 → 系统把借阅记录标成"已还"，并把书标回"在馆"。

---

## 一、数据库设计（做项目的第一步，也是最重要的一步）

做后端项目，**先想清楚数据怎么存**，再写代码。图书管理系统需要 **3 张表**：

```
reader（读者） ──┐
                 ├── borrow_record（借阅记录）── book（图书）
```

关系：

- 一个读者能借**多本**书 → reader 和 borrow_record 是**一对多**；
- 一本书能被**多次**借 → book 和 borrow_record 也是一对多；
- 借阅记录是"中间表"，靠两个外键 `book_id`、`reader_id`，把"谁借了哪本书"串起来。

### 表结构

**① book（图书）**

| 字段 | 类型 | 说明 |
|---|---|---|
| id | INT 主键自增 | 图书编号 |
| title | VARCHAR(50) | 书名 |
| author | VARCHAR(30) | 作者 |
| status | CHAR(1) 默认 '0' | '0'=在馆，'1'=已借出 |

**② reader（读者）**

| 字段 | 类型 | 说明 |
|---|---|---|
| id | INT 主键自增 | 读者编号 |
| name | VARCHAR(20) | 姓名 |
| gender | CHAR(1) | 性别 |
| phone | VARCHAR(20) | 电话 |

**③ borrow_record（借阅记录）**

| 字段 | 类型 | 说明 |
|---|---|---|
| id | INT 主键自增 | 记录编号 |
| book_id | INT 外键→book.id | 借的是哪本书 |
| reader_id | INT 外键→reader.id | 谁借的 |
| borrow_date | DATE | 借书日期 |
| return_date | DATE | 还书日期，**NULL = 还没还** |

> 关键设计：**"借出去没有"用 return_date 是不是 NULL 来判断**。NULL 表示还没还，有日期表示已还。这样"借阅记录"既是"当前在借"，也是"历史记录"。

---

## 二、动手：建库建表

### ① 建一个独立的库 library

在 **VS Code 的 Database Client 插件**里（或 MySQL 命令行），执行：

```sql
CREATE DATABASE IF NOT EXISTS library DEFAULT CHARSET utf8mb4;
```

然后**切到 library 库**，再执行下面的建表语句。

### ② 建 3 张表

```sql
-- 1. 图书表
CREATE TABLE book (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(50) NOT NULL COMMENT '书名',
    author VARCHAR(30) COMMENT '作者',
    status CHAR(1) DEFAULT '0' COMMENT '0=在馆 1=已借出'
);

-- 2. 读者表
CREATE TABLE reader (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL COMMENT '姓名',
    gender CHAR(1) COMMENT '性别',
    phone VARCHAR(20) COMMENT '电话'
);

-- 3. 借阅记录表
CREATE TABLE borrow_record (
    id INT PRIMARY KEY AUTO_INCREMENT,
    book_id INT NOT NULL COMMENT '借的书',
    reader_id INT NOT NULL COMMENT '借书人',
    borrow_date DATE COMMENT '借书日期',
    return_date DATE COMMENT '还书日期，NULL=未还',
    CONSTRAINT fk_br_book FOREIGN KEY (book_id) REFERENCES book(id),
    CONSTRAINT fk_br_reader FOREIGN KEY (reader_id) REFERENCES reader(id)
);
```

> 注意：外键约束 `fk_br_book`、`fk_br_reader` 保证借阅记录里的 book_id、reader_id 一定是真实存在的书和读者。

### ③ 改配置：把数据库从 zxt 换成 library

**📁 写在：`src/main/resources/application.properties`**

把 url 里的数据库名改一下：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useSSL=false
```

（就是 `zxt` 换成 `library`，其他不动。）

---

## 三、插点测试数据

建完表，先塞几条数据，方便后面测：

```sql
-- 图书
INSERT INTO book (title, author, status) VALUES
('Java核心技术', '霍斯特曼', '0'),
('三体', '刘慈欣', '0'),
('活着', '余华', '0'),
('百年孤独', '马尔克斯', '0');

-- 读者
INSERT INTO reader (name, gender, phone) VALUES
('张语钦', 'M', '13800000001'),
('李雷', 'M', '13800000002'),
('韩梅梅', 'F', '13800000003');
```

---

## 四、整体实现步骤（预告）

| 步骤 | 内容 |
|---|---|
| 18 | ✅ 建库建表（你现在在这） |
| 19 | 图书模块：Book 实体类 + Mapper + Service + Controller（CRUD） |
| 20 | 读者模块：Reader 全套（跟图书一样，练熟套路） |
| 21 | 借书 / 还书（**事务**：改借阅记录 + 改图书状态） |
| 22 | 借阅记录查询（**多表 JOIN**：带出书名和读者名）+ 分页/搜索 |

---

## 怎么用（一句话）

做项目先设计数据库：**想清楚有几张表、每张表几个字段、表之间怎么关联（外键）**。图书管理系统 = book + reader + borrow_record 三张表，借阅记录靠两个外键把"谁借了哪本书"串起来。
