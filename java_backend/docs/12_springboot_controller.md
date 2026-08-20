# 12 · Spring Boot Controller 入门 —— 接口怎么写

> 目标：学会写"能收参数、能返回 JSON"的接口，这是后端开发的基本功。
> 前置：已经跑通 11 里的 Hello World（访问 `/hello` 能看到 `Hello World!`）。

---

## 零、回顾：你已经写过第一个接口了

你写的 `HelloController` 里的 `@GetMapping("/hello")`，就是一个**接口**（也叫端点 endpoint）。它干的事：收到 `GET /hello` 请求 → 返回一句话。

现在要学三件事，让它从"玩具"变成"能干活的接口"：

1. **返回 JSON**（而不是纯文本）
2. **接收参数**（路径参数 / 查询参数）
3. **区分 HTTP 方法**（GET / POST / PUT / DELETE）

---

## 一、返回 JSON —— 后端真正干的事

后端接口几乎不返回纯文本，而是返回 **JSON**（你在 10 文档里学过它）。

Spring Boot 里怎么返回 JSON？**特别简单：直接 `return` 一个 Java 对象，它自动帮你转成 JSON**。

先别急着建复杂的类，我们用 `Map`（映射）做个最简单的演示，把这个认知建立起来：

```java
@GetMapping("/info")
public Map<String, Object> getInfo() {
    Map<String, Object> map = new HashMap<>();
    map.put("name", "张三");
    map.put("age", 20);
    return map;
}
```

浏览器访问 `http://localhost:8080/info`，你会看到：

```json
{"name":"张三","age":20}
```

> 关键认知：**你 return 的是一个 Java 对象（Map），浏览器收到的是 JSON 字符串。** 中间那个"对象 → JSON"的转换是自动的——这就是 10 文档里说的"**序列化**"，由 Jackson 这个库自动完成。你啥都不用管。

（真实项目里，这里 return 的通常是 `User` 这种"实体类"，而不是 Map。等下一篇连 MySQL 时，我们会建一个 `User` 类，让 Java 对象对应数据库里的 `user` 表。）

---

## 二、接收参数 —— 让接口"听懂"别人传来的东西

### 1. 路径参数：@PathVariable

把参数放在 URL 的**路径里**，用花括号占位：

```java
@GetMapping("/hello/{name}")
public String helloName(@PathVariable String name) {
    return "Hello, " + name + "!";
}
```

访问 `http://localhost:8080/hello/小明` → 返回 `Hello, 小明!`

> `@PathVariable`（path variable，路径变量）：把路径里的 `{name}` 抠出来，塞进后面的 `String name`。

### 2. 查询参数：@RequestParam

把参数放在 URL 的**问号后面**：

```java
@GetMapping("/greet")
public String greet(@RequestParam String name) {
    return "你好, " + name + "!";
}
```

访问 `http://localhost:8080/greet?name=小红` → 返回 `你好, 小红!`

> `@RequestParam`（request parameter，请求参数）：从 URL 问号后面的 `?name=小红` 里，把 `name` 的值抠出来。

### 3. 两者的区别（重点，新手最容易混）

| | **@PathVariable** | **@RequestParam** |
|---|---|---|
| 参数长在哪 | 路径里 `/hello/小明` | 问号后 `/greet?name=小红` |
| 长啥样 | `/hello/{name}` | `/greet?name=xxx` |
| 一般用来干嘛 | **定位某一个资源**（查 id=1 的用户） | **附加条件**（搜索、筛选、分页） |

> 一句话：**路径参数是"找谁"，查询参数是"怎么找"。**

---

## 三、区分 HTTP 方法（呼应 10 文档的增删改查）

你在 10 文档学过：HTTP 有 GET / POST / PUT / DELETE，分别对应数据库的查 / 增 / 改 / 删。Spring Boot 里用不同的注解来"接"不同的方法：

| 注解 | HTTP 方法 | 干的事 | 对应 SQL |
|---|---|---|---|
| `@GetMapping` | GET | 查 | SELECT |
| `@PostMapping` | POST | 增 | INSERT |
| `@PutMapping` | PUT | 改 | UPDATE |
| `@DeleteMapping` | DELETE | 删 | DELETE |

> 注意：浏览器地址栏只能发 **GET**。要测 POST / PUT / DELETE，得用工具（IDEA 自带的 HTTP Client、或者 Postman、或者以后学前端时用页面调）。**这一篇我们先只练 GET，其他方法到下一篇做增删改查时再上手。**

---

## 四、动手练习

把 `HelloController` 升级成下面这样（**import 别漏**），然后重启，逐个访问测试：

```java
package com.example.springbootdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {

    // 原来的 Hello World
    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    // 1. 返回 JSON
    @GetMapping("/info")
    public Map<String, Object> getInfo() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", 20);
        return map;
    }

    // 2. 路径参数
    @GetMapping("/hello/{name}")
    public String helloName(@PathVariable String name) {
        return "Hello, " + name + "!";
    }

    // 3. 查询参数
    @GetMapping("/greet")
    public String greet(@RequestParam String name) {
        return "你好, " + name + "!";
    }
}
```

**改完代码记得重启服务**（点红色 ■ 停，再点绿色 ▶ 跑），然后依次访问：

| 地址 | 你该看到 |
|---|---|
| `http://localhost:8080/hello` | `Hello World!` |
| `http://localhost:8080/info` | `{"name":"张三","age":20}` |
| `http://localhost:8080/hello/小明` | `Hello, 小明!` |
| `http://localhost:8080/greet?name=小红` | `你好, 小红!` |

---

## 怎么用（一句话）

接口 = **一个地址 + 一个 HTTP 方法 + 一段处理逻辑**。`@GetMapping` 这类注解定"地址和方法"，`@PathVariable` / `@RequestParam` 收参数，`return` 一个对象就自动转成 JSON 返回。这就是后端接口的全部基本套路。
