# 11 · Spring Boot 入门 —— 用 Java 写后端

> 目标：搞懂 Spring Boot 是什么、三层架构长啥样，为建第一个项目做准备。
> 前置：读完 00（后端全景）和 10（HTTP + JSON）。这篇先建立整体认知，还没到写代码。

---

## 零、Spring Boot 是什么

一句话：**Spring Boot 是一个"帮你快速搭起 Java 后端"的框架（工具箱）**。

"后端"就是那个"接收 HTTP 请求 → 处理 → 返回 JSON"的服务。以前用 Java 写后端特别啰嗦——光启动一个服务就要写一大堆配置、配一堆 XML。Spring Boot 把这堆麻烦事全帮你打包好了，你只要专注写"业务逻辑"。

打比方：以前写 Java 后端像**从零装修一套毛坯房**（水电、墙、地板全自己搞）；Spring Boot 像**拎包入住的精装房**，水电墙地都装好了，你只要摆家具（写业务代码）就行。

### 为什么叫"Spring Boot"

- **Spring** = 一个老牌的 Java 开发框架（工具箱），功能超全但配置超繁琐
- **Boot** = "启动/开机"，暗示"一键启动、快速上手"

合起来：**"给 Spring 这个老框架装了个快速启动按钮"**。所以 Spring Boot = 简化版的 Spring，用它就能少写配置。

---

## 一、三层架构（这是 Spring Boot 的核心）

后端代码不是一坨堆在一起的，而是分三层，各管各的。记住这仨名词，Spring Boot 就懂一大半了：

| 层 | 英文 | 大白话 | 打比方 |
|---|---|---|---|
| **Controller** | 控制层 | 接收请求、返回结果 | 前台接待员 |
| **Service** | 服务层 | 业务逻辑（判断/计算） | 业务员 |
| **Repository**（也叫 Mapper） | 数据层 | 跟数据库打交道（增删改查） | 仓库管理员 |

流程是固定的一根线：

```
浏览器（请求） → Controller → Service → Repository → 数据库（MySQL）
浏览器（收到 JSON）← Controller ← Service ← Repository ← 数据库
```

**每一层只跟相邻那层打交道，不能越级。** Controller 不碰数据库，Repository 不碰业务逻辑。

---

## 二、一个请求完整走一遍（把 10 的知识串起来）

以"查 id=1 的用户"为例，你之前学的东西全用上了：

1. 浏览器发请求：`GET /user/1`（HTTP 的 GET 方法 + URL）
2. **Controller** 接到：一看是"查用户"，就把活交给 Service
3. **Service** 想：查用户不用额外逻辑，直接叫 Repository 去查
4. **Repository** 执行 SQL：`SELECT * FROM user WHERE id=1`（你 MySQL 学的）
5. 查到的数据**一层层原路返回**，Controller 最后把它包装成 **JSON** 返回（你 JSON 学的）

```
GET /user/1
   ↓
Controller（收到请求）
   ↓
Service（业务逻辑）
   ↓
Repository（SELECT * FROM user WHERE id=1）
   ↓
MySQL 数据库
   ↓ 查到 { id:1, name:"张三" }
原路返回 → Controller 包成 JSON → 返回给浏览器
```

> 看到没？**之前学的 HTTP、JSON、MySQL，在这里被"三层架构"串成了一条完整的流水线。** 这就是后端开发的骨架。

---

## 三、接下来怎么学

我们按"从简到繁"走，每一步都在 IDEA 里动手：

1. **第一个项目**：用 Spring Initializr（官方"一键生成项目"的工具）建项目，跑起来一个 Hello World
2. **Controller 入门**：写一个接口，浏览器访问它，返回一段文字 / 一个 JSON
3. **连 MySQL**：让 Spring Boot 真的去读你之前建的 `user` 表
4. **完整增删改查**：CRUD 接口（对应 HTTP 学的 GET / POST / PUT / DELETE）
5. **图书管理系统**：把上面全用上，做个完整小项目

---

## 怎么用（一句话）

Spring Boot = 快速搭 Java 后端的框架；三层架构（Controller 接请求 → Service 管业务 → Repository 查库）是它的骨架。等你 IDEA 装好，第一步就是"建项目 + 跑出 Hello World"。
