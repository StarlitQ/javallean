# 16 · 统一响应格式（Result 类）

> 目标：让所有接口返回**统一的结构** `{code, msg, data}`，而不是五花八门（有的返回对象、有的返回字符串）。
> 前置：跑通了 15 的多表查询。

---

## 零、这一步要干嘛

现在你的接口返回格式很乱：

- 查 `/users` → 返回一个 User 数组；
- 增 `/user` → 返回字符串 `"新增成功"`；
- 删 `/user/6` → 返回字符串 `"删除失败：没找到..."`。

前端（或调用你接口的人）拿到这些，得猜"这次返回的到底是个啥？"——很麻烦。

真实项目里，**几乎所有接口都返回统一格式**：

```json
{"code": 200, "msg": "成功", "data": {...}}
```

三个字段各司其职：

| 字段 | 干嘛的 |
|---|---|
| `code` | 状态码：200 成功，500 失败 |
| `msg` | 给人看的提示（"成功"/"删除失败：没找到 id=6 的用户"） |
| `data` | 真正的数据（查到的用户、列表；没数据就是 null） |

前端一看到这个结构，就统一按套路处理：**先看 code 判断成败 → 成功取 data，失败提示 msg**。

---

## 一、建一个通用的 Result 类

**📁 写在：`src/main/java/com/example/springbootdemo/Result.java`**（新建，跟你其他类同一个包）

```java
package com.example.springbootdemo;

public class Result {

    private Integer code;   // 状态码：200 成功，500 失败
    private String msg;     // 提示信息
    private Object data;    // 数据：可以是任意类型（User、List、null）

    // 成功（带数据）
    public static Result success(Object data) {
        Result r = new Result();
        r.code = 200;
        r.msg = "成功";
        r.data = data;
        return r;
    }

    // 成功（不带数据）
    public static Result success() {
        return success(null);
    }

    // 失败（带错误提示）
    public static Result error(String msg) {
        Result r = new Result();
        r.code = 500;
        r.msg = msg;
        r.data = null;
        return r;
    }

    // getter / setter（IDEA 里 Alt+Insert 生成）
    public Integer getCode() { return code; }
    public void setCode(Integer code) { this.code = code; }
    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }
    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }
}
```

三个点解释：

- **`static` 方法**：这样你调用时不用 `new Result()`，直接 `Result.success(...)` 就行。这三个 `success` / `error` 是给你的"快捷按钮"；
- **`Object data`**：`Object` 是 Java 里所有类的"老祖宗"，所以它能装任何东西——User、`List<User>`、null 都行；
- **两个 `success` 重载**：`success(数据)` 带数据，`success()` 不带（比如增删改成功时没数据可返回）。

---

## 二、改 Controller，所有接口返回 Result

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

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    // 查一个
    @GetMapping("/user/{id}")
    public Result getUser(@PathVariable Integer id) {
        User user = userService.findById(id);
        return Result.success(user);
    }

    // 查所有
    @GetMapping("/users")
    public Result listUsers() {
        return Result.success(userService.findAll());
    }

    // 增
    @PostMapping("/user")
    public Result addUser(@RequestBody User user) {
        userService.insert(user);
        return Result.success();
    }

    // 改
    @PutMapping("/user/{id}")
    public Result updateUser(@PathVariable Integer id, @RequestBody User user) {
        user.setId(id);
        int count = userService.update(user);
        if (count > 0) {
            return Result.success();
        } else {
            return Result.error("修改失败：没找到 id=" + id + " 的用户");
        }
    }

    // 删
    @DeleteMapping("/user/{id}")
    public Result deleteUser(@PathVariable Integer id) {
        int count = userService.delete(id);
        if (count > 0) {
            return Result.success();
        } else {
            return Result.error("删除失败：没找到 id=" + id + " 的用户");
        }
    }
}
```

注意变化：每个方法的返回类型从 `User` / `List<User>` / `String`，**全变成了 `Result`**。原来的数据，现在塞进 `Result.success(...)` 里一起返回。

---

## 三、为什么这样更好

1. **前端好处理**：拿到返回先看 `code`，200 就取 `data`，500 就把 `msg` 弹给用户——一套代码通吃所有接口；
2. **错误信息规范**：失败时 `msg` 里写清楚原因，不再靠"字符串里带没带'成功'两个字"来判断；
3. **好扩展**：以后想加"分页信息"、"时间戳"，都在 Result 里加一个字段就行，不影响 data。

---

## 四、动手练习

1. 新建 `Result.java`（照抄第一节）；
2. 把 `UserController.java` 整个替换成第二节的版本；
3. 重启，用 REST Client 或浏览器测：

| 请求 | 你该看到 |
|---|---|
| `GET /users` | `{"code":200,"msg":"成功","data":[...]}` |
| `GET /user/2` | `{"code":200,"msg":"成功","data":{...xiaolan..., "deptName":"development"}}` |
| `POST /user` | `{"code":200,"msg":"成功","data":null}` |
| `DELETE /user/999` | `{"code":500,"msg":"删除失败：没找到 id=999 的用户","data":null}` |

> 这一步做完，你的接口就"像正经项目"了——**统一的返回结构，是所有真实后端系统的基本盘。**

---

## 怎么用（一句话）

接口返回统一用 **Result 类**包一层：`{code, msg, data}`。成功调 `Result.success(数据)`，失败调 `Result.error("原因")`。前端只认这一个结构。
