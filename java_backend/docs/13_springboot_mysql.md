# 13 · Spring Boot 连 MySQL —— 后端真的读数据库了

> 目标：让接口真的去读你 MySQL 里的 `user` 表，而不是返回写死的假数据。
> 前置：跑通了 12 的 Controller；MySQL 服务**开着**，`user` 表里有数据。

---

## 零、这一步要干嘛

之前接口返回的都是**写死的假数据**（"Hello World"、Map 里手填的"张三"）。现在要让后端**真的连上 MySQL、读出 `user` 表**。

呼应 11 文档的三层架构：之前你只用了 **Controller（接待员）** 这一层，现在要再雇一个 **Mapper（仓库管理员）**，专门去数据库里取货。

```
浏览器 → Controller（接待员） → Mapper（仓库管理员） → MySQL（仓库）
```

### 📋 这一步要动 5 个文件（先看整体，再逐个做）

| 顺序 | 文件 | 在项目里的位置 | 干什么 |
|---|---|---|---|
| 1 | `pom.xml` | 项目根目录 | 加 2 个依赖 |
| 2 | `application.properties` | `src/main/resources/` 下 | 填数据库连接 |
| 3 | `User.java` | `src/main/java/com/example/springbootdemo/` 下 | 实体类（对应 user 表） |
| 4 | `UserMapper.java` | 同上（同包） | 写 SQL 查库 |
| 5 | `UserController.java` | 同上（同包） | 接待员调 Mapper |

> 后三个 `.java` 文件都在同一个包 `com.example.springbootdemo` 里（跟你主类同包）。新建方法：在 IDEA 左侧项目树里**右键这个包 → New（新建）→ Java Class（Java 类）→ 输入类名**。

---

## 一、加两个依赖

**📁 写在：`pom.xml`（项目根目录）** —— 找到里面的 `<dependencies>` 那一大块，在它里面加：

```xml
<!-- MySQL 驱动：Java 连 MySQL 用的"电话线" -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- MyBatis：帮你方便地写 SQL 查库 -->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>4.1.0</version>
</dependency>
```

改完 pom.xml 后，IDEA 右上角可能会弹出一个小提示"**Load Maven Changes**"（或一个刷新图标），**点它**，让它下载这两个新依赖。第一次下载要等一会儿。

> `mysql-connector-j` 那个不用写版本号，Spring Boot 会自动帮你选好；`mybatis-spring-boot-starter` 必须写版本 `4.1.0`（正好配你的 Spring Boot 4.1.0）。

---

## 二、告诉 Spring Boot 数据库在哪

**📁 写在：`src/main/resources/application.properties`**（如果没有这个文件，就在 `resources` 文件夹上右键 New → File 新建一个，名字就叫 `application.properties`）：

```properties
# 数据库连接：url + 用户名 + 密码
spring.datasource.url=jdbc:mysql://localhost:3306/zxt?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&useSSL=false
spring.datasource.username=root
spring.datasource.password=123456

# 开启驼峰映射：数据库的 dept_id 列，自动对应 Java 的 deptId 字段
mybatis.configuration.map-underscore-to-camel-case=true
```

三个地方要跟你自己的对上：

| 填啥 | 改成什么 |
|---|---|
| `zxt` | 你的数据库名（你之前建表用的那个库，不是 `zxt` 就改掉） |
| `root` | 你连 MySQL 的用户名（通常是 `root`） |
| `123456` | 你连 MySQL 的密码 |

> 想不起来用户名密码？去 **VS Code 的 Database Client 插件**里，点你那个 MySQL 连接 → 右键 → 查看连接属性，里面就写着主机、用户名、密码。

---

## 三、建实体类 User（对应 user 表）

**📁 写在：`src/main/java/com/example/springbootdemo/User.java`**（右键 `com.example.springbootdemo` 包 → New → Java Class → 输 `User`）

后端要读 `user` 表，就得有个 Java 类来"装"每一行数据。这个类叫**实体类**，它的字段要跟表的列一一对应。

回忆你 `user` 表的结构（id、name、age、status、gender）：

```java
package com.example.springbootdemo;

public class User {

    private Integer id;
    private String name;
    private Integer age;
    private String status;
    private String gender;
    private Integer deptId;   // 对应表的 dept_id 列（外键，指向 dept 表）

    // 下面这一堆 getter / setter，在 IDEA 里按 Alt + Insert 自动生成，别手敲
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Integer getDeptId() { return deptId; }
    public void setDeptId(Integer deptId) { this.deptId = deptId; }
}
```

> 关键认知：**表的一行 ↔ 一个 User 对象；表的列 ↔ 对象的字段。** 这个"对象 ↔ 数据库行"的映射，就是实体类的意义。

---

## 四、建 Mapper 接口（仓库管理员 + 写 SQL）

**📁 写在：`src/main/java/com/example/springbootdemo/UserMapper.java`**（新建方法同上）

Mapper 就是"仓库管理员"，里面写 SQL。它是个**接口**，不用你写实现，MyBatis 会自动搞定：

```java
package com.example.springbootdemo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    // 按 id 查一个用户
    @Select("select * from user where id = #{id}")
    User findById(Integer id);

    // 查所有用户
    @Select("select * from user")
    List<User> findAll();
}
```

- `@Mapper`：告诉 Spring "这是个仓库管理员"；
- `@Select("...")`：把 SQL 写在注解里；
- `#{id}`：SQL 里的**占位符**，会把方法参数 `id` 的值填进去（相当于你之前学的 `where id = 1` 里的 `1`）。

---

## 五、建 UserController（接待员调仓库管理员）

**📁 写在：`src/main/java/com/example/springbootdemo/UserController.java`**

最后，让接待员（Controller）去使唤仓库管理员（Mapper）：

```java
package com.example.springbootdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;   // 注意：这个接口我们没 new，哪来的对象？

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Integer id) {
        return userMapper.findById(id);   // 真的去查数据库了
    }

    @GetMapping("/users")
    public List<User> listUsers() {
        return userMapper.findAll();
    }
}
```

### 这里的 @Autowired 是啥？（重要）

你肯定疑惑：`UserMapper` 明明是个接口，我也没 `new` 它，那 `userMapper` 这个对象哪来的？

答案：**Spring 自动创建好、并"递"给你的**。

呼应你上次学的"容器自动招人"——你给字段标上 `@Autowired`（自动装配），就等于说："这个字段我需要的对象，**容器你帮我准备好，直接塞进来**"。Spring 一看 `UserMapper` 上有 `@Mapper` 注解，就自动创建出它的实现对象，然后塞进你的字段里。

> 这个"**要用谁，声明一下，Spring 自动递给你，不用自己 new**"的机制，叫 **依赖注入（DI，Dependency Injection，依赖注入）**。它是 Spring 的命根子，后面你会天天用它。

---

## 六、动手练习

1. 按上面的标注，把这 5 个文件都建好/改好；
2. 等 IDEA 把新依赖下完（右下角不转了）；
3. 重启服务；
4. 浏览器访问：
   - `http://localhost:8080/users` → 应该看到**你 user 表里所有数据的 JSON 列表**
   - `http://localhost:8080/user/1` → 应该看到 **id=1 那一行**（如果表里有 id=1 的话）

> 看到自己数据库里真实的数据出现在浏览器里，这一下就通了——**这就是后端开发的完整闭环：浏览器 → Controller → Mapper → MySQL → 原路返回 JSON。**

---

## 怎么用（一句话）

连数据库 = 四件事：**加依赖（驱动 + MyBatis）→ 配连接（url/用户名/密码）→ 建实体类（对应表）→ 建 Mapper（写 SQL）**。Controller 用 `@Autowired` 注入 Mapper，就能让接口真的查数据库了。Service 层我们下一篇再补。
