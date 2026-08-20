# 14 · Spring Boot 完整增删改查（CRUD）+ Service 层

> 目标：把"查"扩展成完整的**增删改查**，并补上 Service 层，让三层架构完整。
> 前置：跑通了 13 的 `/users` 和 `/user/1`（能查到真实数据）。

---

## 零、这一步要干嘛

13 里你只会"**查**"（findById、findAll）。但后端不只是查，还有**增、删、改**——合起来叫 **CRUD**（Create 增 / Read 查 / Update 改 / Delete 删），对应你 10 文档学的 HTTP 四个方法。

同时，13 里为了先跑通，Controller 是**直接调 Mapper** 的（跳过了 Service 层）。现在把 **Service 层补上**，三层架构就完整了：

```
浏览器 → Controller（接待员） → Service（业务经理） → Mapper（仓库管理员） → MySQL
```

### 📋 这一步要动 4 个文件

| 顺序 | 文件 | 干什么 |
|---|---|---|
| 1 | `UserMapper.java` | 加增删改的 SQL |
| 2 | `UserService.java` | **新建**，业务层 |
| 3 | `UserController.java` | 改成调 Service，加增删改接口 |
| 4 | `test.http` | **新建**，用来测 POST/PUT/DELETE |

---

## 一、为什么需要 Service 层？

三层架构里，每层各管一摊，**不能越级**：

| 层 | 只该管 |
|---|---|
| Controller | 接请求、返回结果（HTTP 相关） |
| Service | **业务逻辑**（判断、计算、校验） |
| Mapper | 数据库操作（SQL 相关） |

现在 CRUD 简单，Service 层看起来只是"转发"（把活传给 Mapper）。但以后业务复杂了——比如"新增用户前先查名字重没重复"、"订单金额要打折计算"——这些**业务逻辑就该放 Service 层**，不能塞进 Controller（Controller 只该管请求）也不能塞进 Mapper（Mapper 只该管 SQL）。

打比方：

> Controller = 前台接待员（只管接待、传话）；
> **Service = 业务经理**（干真正的业务，判断、计算）；
> Mapper = 仓库管理员（只管存取货）。

13 里你让"前台直接去仓库拿货"，省事但不规范。现在补上"业务经理"，三层齐全。

---

## 二、扩展 UserMapper（加增删改的 SQL）

**📁 写在：`src/main/java/com/example/springbootdemo/UserMapper.java`**（在原来基础上加三个方法）

```java
package com.example.springbootdemo;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {

    // —— 查（原有）——
    @Select("select * from user where id = #{id}")
    User findById(Integer id);

    @Select("select * from user")
    List<User> findAll();

    // —— 增 ——
    @Insert("insert into user(name, age, status, gender, dept_id) values(#{name}, #{age}, #{status}, #{gender}, #{deptId})")
    void insert(User user);

    // —— 改 ——
    @Update("update user set name=#{name}, age=#{age}, status=#{status}, gender=#{gender}, dept_id=#{deptId} where id=#{id}")
    void update(User user);

    // —— 删 ——
    @Delete("delete from user where id=#{id}")
    void delete(Integer id);
}
```

- `@Insert` / `@Update` / `@Delete`：跟 `@Select` 一个套路，只是 SQL 换成增删改；
- `#{name}`、`#{deptId}` 这些占位符，会把 User 对象里对应字段的值填进去（`user.getName()`、`user.getDeptId()`）；
- 增删改一般不用返回值，所以方法是 `void`。

---

## 三、新建 UserService（业务层）

**📁 写在：`src/main/java/com/example/springbootdemo/UserService.java`**（新建）

```java
package com.example.springbootdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User findById(Integer id) {
        return userMapper.findById(id);
    }

    public List<User> findAll() {
        return userMapper.findAll();
    }

    public void insert(User user) {
        userMapper.insert(user);
    }

    public void update(User user) {
        userMapper.update(user);
    }

    public void delete(Integer id) {
        userMapper.delete(id);
    }
}
```

- `@Service`：告诉 Spring "这是个业务经理"，跟 `@RestController`、`@Mapper` 一样，是让你被扫描管理的"工牌"；
- 现在每个方法就是"转发"给 Mapper，看起来简单，但这就是 Service 该待的位置——以后业务逻辑加在这里。

---

## 四、改 UserController（调 Service，加增删改接口）

**📁 写在：`src/main/java/com/example/springbootdemo/UserController.java`**（整个替换）

```java
package com.example.springbootdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;   // 改成注入 Service，不再直接注入 Mapper

    // 查一个（GET）
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Integer id) {
        return userService.findById(id);
    }

    // 查所有（GET）
    @GetMapping("/users")
    public List<User> listUsers() {
        return userService.findAll();
    }

    // 增（POST）
    @PostMapping("/user")
    public String addUser(@RequestBody User user) {
        userService.insert(user);
        return "新增成功";
    }

    // 改（PUT）
    @PutMapping("/user/{id}")
    public String updateUser(@PathVariable Integer id, @RequestBody User user) {
        user.setId(id);              // 把路径里的 id 塞给对象，让 update 知道改哪一行
        userService.update(user);
        return "修改成功";
    }

    // 删（DELETE）
    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.delete(id);
        return "删除成功";
    }
}
```

### 两个新东西讲清楚

**① `@RequestBody`（请求体）**：POST/PUT 发来的数据是放在**请求体**里的 JSON（不是 URL 上）。`@RequestBody User user` 就是把请求体里的 JSON **自动反序列化**成一个 User 对象——这是 10 文档说的"JSON → Java 对象"的反过程，Jackson 自动干。

**② HTTP 方法对应关系**（呼应 10 文档的增删改查）：

| 注解 | HTTP 方法 | 干的事 | SQL |
|---|---|---|---|
| `@GetMapping` | GET | 查 | SELECT |
| `@PostMapping` | POST | 增 | INSERT |
| `@PutMapping` | PUT | 改 | UPDATE |
| `@DeleteMapping` | DELETE | 删 | DELETE |

---

## 五、怎么测 POST / PUT / DELETE？

浏览器地址栏**只能发 GET**，测不了增删改。我们用 **IDEA 自带的 HTTP Client**（不用装任何东西）。

**📁 新建：`test.http`**（在项目根目录，右键项目名 → New → File → 命名 `test.http`），粘贴：

```http
### 1. 查所有（GET，这个浏览器也能测）
GET http://localhost:8080/users

### 2. 新增用户（POST）
POST http://localhost:8080/user
Content-Type: application/json

{
  "name": "tom",
  "age": 30,
  "status": "1",
  "gender": "M",
  "deptId": 2
}

### 3. 修改 id=1 的用户（PUT）
PUT http://localhost:8080/user/1
Content-Type: application/json

{
  "name": "alice改",
  "age": 24,
  "status": "1",
  "gender": "M",
  "deptId": 2
}

### 4. 删除 id=6 的用户（DELETE）
DELETE http://localhost:8080/user/6
```

**怎么用**：每个 `###` 分隔一段请求，每一段上面会有一个**绿色 ▶** 按钮，点它就能发对应请求。发完在下面能看到返回结果。

> 注意：**`deptId` 必须填 dept 表里真实存在的 id**（你之前知道是 2、3、5 这几个），否则外键约束会拒绝插入——这个坑你 13 前 LOAD DATA 时踩过，现在用 Java 插入也一样要遵守。

---

## 六、动手练习

1. 按标注把 4 个文件建好/改好；
2. 重启服务；
3. 在 `test.http` 里：
   - 先点 `### 2` 新增，看返回"新增成功"；
   - 再点 `### 1` 查所有，看新用户出现在列表里；
   - 点 `### 3` 修改，点 `### 4` 删除，分别看效果；
4. 每一步做完，用 `GET /users` 验证数据库真的变了。

> 做完你就掌握了后端开发的完整闭环：**增删改查，通过 HTTP 四个方法，穿透三层架构，直达数据库。**

---

## 七、进阶：怎么知道真的删/改到了？（影响行数）

你可能会发现一个坑：删一个**不存在的 id**（数据库里根本没有 id=6 的用户），接口照样返回"删除成功"，但数据一条没少。

**原因**：`delete from user where id=6` 这条 SQL **本身是合法且执行成功的**——只是它**影响了 0 行**（没有匹配的数据可删）。而我们的代码里 `return "删除成功"` 是**写死的**，压根不检查"到底删了几行"。

> 核心认知：**"SQL 执行成功" ≠ "真的影响到了数据"。** 删不存在的行、改不存在的行，SQL 都不会报错，只是**影响行数（affected rows）是 0**。

**打比方**：你让仓库管理员"扔掉 6 号货"，他去转了一圈没找到 6 号，但回来报告"扔完了"——他把"执行了扔的动作"当成了"扔到了东西"。货架上根本没有 6 号，他也不提醒你。

**解决**：让 delete / update 方法返回 `int`（影响行数），再根据行数判断。改三处：

**① Mapper** —— 让 delete / update 返回 int：

```java
@Update("update user set name=#{name}, age=#{age}, status=#{status}, gender=#{gender}, dept_id=#{deptId} where id=#{id}")
int update(User user);      // 改成 int，返回影响行数

@Delete("delete from user where id=#{id}")
int delete(Integer id);     // 改成 int，返回影响行数
```

**② Service** —— 跟着返回 int：

```java
public int update(User user) {
    return userMapper.update(user);
}

public int delete(Integer id) {
    return userMapper.delete(id);
}
```

**③ Controller** —— 根据影响行数判断：

```java
@PutMapping("/user/{id}")
public String updateUser(@PathVariable Integer id, @RequestBody User user) {
    user.setId(id);
    int count = userService.update(user);   // 实际改了几行
    if (count > 0) {
        return "修改成功";
    } else {
        return "修改失败：没找到 id=" + id + " 的用户";
    }
}

@DeleteMapping("/user/{id}")
public String deleteUser(@PathVariable Integer id) {
    int count = userService.delete(id);     // 实际删了几行
    if (count > 0) {
        return "删除成功";
    } else {
        return "删除失败：没找到 id=" + id + " 的用户";
    }
}
```

现在再删一个不存在的 id，就会返回"删除失败：没找到 id=6 的用户"。

> `insert` 一般不用管这个——插入要么成功（影响 1 行）、要么直接报错，不会"静默插 0 行"。

---

## 怎么用（一句话）

CRUD = 四套注解（`@GetMapping`/`@PostMapping`/`@PutMapping`/`@DeleteMapping`）+ 四句 SQL（SELECT/INSERT/UPDATE/DELETE）。三层架构：Controller 接请求 → Service 管业务 → Mapper 查库。POST/PUT/DELETE 用 IDEA 的 `.http` 文件测（浏览器只能测 GET）。
