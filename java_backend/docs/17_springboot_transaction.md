# 17 · 事务 @Transactional —— 多个操作同生共死

> 目标：让一个方法里的多个数据库操作"要么全成功、要么全失败"。
> 前置：跑通了 16 的 Result 统一响应；MySQL 阶段学过事务（ACID）。

---

## 零、这一步要干嘛

回顾你 MySQL 阶段学的**事务**：一次转账，要分两步——A 减 100、B 加 100。这两步必须**同生共死**：要么都成功，要么都失败。绝不能 A 扣了钱、B 没收到，那 100 块就凭空消失了。

现在在 Spring 里，一个 Service 方法里如果有**多个数据库操作**，默认它们是**各自独立提交**的：

- 第一步 insert 成功 → 马上写进数据库（"泼出去的水"）；
- 第二步 insert 失败 → 报错，但第一步**已经留下了，收不回来**。

`@Transactional` 注解就是解决这个的：给方法标上它，方法里的**所有数据库操作打包成一个事务**——任何一步失败，之前成功的步骤也**全部回滚（撤销）**。

---

## 一、先看问题：不加注解会怎样

我们先写一个"两步操作"的方法，**故意让第二步失败**，看看不加注解的后果。

### ① UserService 加一个演示方法

**📁 写在：`src/main/java/com/example/springbootdemo/UserService.java`**

```java
// 演示方法：第一步成功，第二步故意失败
public void addTwoUsers() {
    // 第一步：插入合法用户（deptId=2 是存在的）
    User ok = new User();
    ok.setName("ok_user");
    ok.setAge(20);
    ok.setStatus("1");
    ok.setGender("M");
    ok.setDeptId(2);
    userMapper.insert(ok);

    // 第二步：插入违反外键的用户（deptId=999 不存在），必然报错
    User bad = new User();
    bad.setName("bad_user");
    bad.setAge(20);
    bad.setStatus("1");
    bad.setGender("M");
    bad.setDeptId(999);          // dept 表没有 999，外键约束会拒绝
    userMapper.insert(bad);      // 这里抛异常，方法中断
}
```

> 你 user 表有外键 `fk_user_dept_id2`（dept_id 指向 dept.id），所以插入 deptId=999 会**违反外键约束、报错**。我们就是利用这个来"故意让第二步失败"。

### ② UserController 加一个触发接口

**📁 写在：`src/main/java/com/example/springbootdemo/UserController.java`**

```java
@GetMapping("/demo/tx")
public Result demoTx() {
    try {
        userService.addTwoUsers();
        return Result.success();
    } catch (Exception e) {
        return Result.error("第二步失败了，方法中断");
    }
}
```

### ③ 先不加注解，测一次

重启服务，浏览器访问 `http://localhost:8080/demo/tx`：

- 会看到返回 `{"code":500, "msg":"第二步失败了，方法中断", ...}`（第二步报错）；
- 再访问 `/users` 查一下——**你会看到 `ok_user` 留下了！**

这就是问题：第二步失败，但第一步的 `ok_user` 已经写进库了，**没被撤销**。如果这是转账，就等于"A 扣了钱，B 没收到"。

---

## 二、加 @Transactional，让它们同生共死

给方法加一个注解就行。

**📁 写在：`UserService.java`**（在 addTwoUsers 方法上面加一行，并补 import）

```java
import org.springframework.transaction.annotation.Transactional;   // 顶部 import 补上

// 演示方法：加 @Transactional，第一步和第二步打包成一个事务
@Transactional
public void addTwoUsers() {
    // ... 里面代码不变 ...
}
```

就这一行 `@Transactional`。它的意思是：**"这个方法里的所有数据库操作，打包成一个事务。任何一步失败，前面成功的全部回滚。"**

重启服务，再访问 `/demo/tx`：

- 还是返回"第二步失败"（第二步确实报错了）；
- 但再访问 `/users` 查一下——**这次 `ok_user` 没留下！**

第一步虽然"执行"了，但因为第二步失败，整个事务回滚，第一步的插入被**撤销**了。这就对了。

---

## 三、为什么一行注解就这么神？

这是 **Spring 事务管理**在背后干活（呼应你之前学的 IoC/DI，都是 Spring 替你办的"家务"）：

1. 你方法标了 `@Transactional`，Spring 就给它**包了一层**；
2. 方法开始 → Spring 开启一个数据库事务；
3. 方法里每一步操作，都记在这个事务里，**先不真正提交**；
4. 方法正常走完 → Spring 提交（commit），全部生效；
5. 方法**半路抛异常** → Spring 回滚（rollback），全部撤销。

所以 `try-catch` 写在 Controller 里没关系——异常是先从 Service 方法抛出来、**Spring 在那一刻已经回滚了**，Controller 的 try-catch 只是接住异常、显示个友好提示。

**打比方**：就像"打包发快递"——一个包裹里装了好几件货（多个操作）。发出去（提交）之前，任何一件有问题，整个包裹拆掉重来（回滚）；只有全部没问题，才统一发货（提交）。

---

## 四、什么时候用？

只要一个业务要**动好几次数据库、且必须一致**，就用 `@Transactional`。典型场景：

- 转账（A 减钱 + B 加钱）；
- **借书**（插入借阅记录 + 图书状态改成"已借出"）；
- 下单（减库存 + 生成订单 + 扣钱）。

> 你做图书管理系统时，"借书""还书"就一定会用上它。

---

## 动手练习

1. `UserService` 加 `addTwoUsers()`（先不加注解）；
2. `UserController` 加 `/demo/tx`；
3. 重启，访问 `/demo/tx`，再查 `/users` → 发现 `ok_user` 留下了（问题）；
4. 给 `addTwoUsers()` 加 `@Transactional`（补 import）；
5. 重启，再访问 `/demo/tx`，再查 `/users` → 发现 `ok_user` 没了（回滚生效）。

> 看到"加注解前后 ok_user 一留一没"的对比，事务的作用就彻底懂了。

---

## 怎么用（一句话）

一个方法里多个数据库操作必须"同生共死"，就给 Service 方法加 **`@Transactional`**。方法正常走完就提交，半路抛异常就全部回滚。
