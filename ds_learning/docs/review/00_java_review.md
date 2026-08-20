# 🔄 Java OOP 快速回顾

> **目标**：用 30 分钟唤醒你的 Java 肌肉记忆，为继续学数据结构热身。
>
> 你已完成的代码在 `first_java/src/` 下，可以用 Ctrl+点击 跳转过去看。

---

## 1. 类与对象 — 蓝图与房子

这是 Java 最核心的比喻，你最早在 [ClassAndObject.java](../../../first_java/src/phase01_intro/ClassAndObject.java) 就掌握了：

```
类（Class）  = 盖房子的蓝图    →  只有一份
对象（Object）= 按蓝图盖出的房子 →  可以有很多栋
```

```java
Dog d1 = new Dog();   // new = 按蓝图造一个真的出来
d1.name = "大黄";

Dog d2 = new Dog();   // 同一张蓝图，再盖一栋
d2.name = "小白";
```

**三个关键点：**

| 概念 | 一句话 |
|------|--------|
| `class` | 定义一个新类型（就像 `int`、`String`，但是你自己发明的） |
| `new` | 在堆内存里分配空间，返回这个对象的"遥控器"（引用） |
| 字段 vs 方法 | 字段 = 对象**有什么**（数据），方法 = 对象**能做什么**（行为） |

**快速自检**：`Dog d;` 和 `new Dog()` 分别做了什么？→ [答案看这里](../../../first_java/src/phase01_intro/MemoryModel.java)

---

## 2. 栈与堆 — 内存里长什么样

你在 [MemoryModel.java](../../../first_java/src/phase01_intro/MemoryModel.java) 学过这个：

```
栈（Stack）                    堆（Heap）
───────────                  ────────────
Dog d = 0x100  ──────────→   0x100: { name="大黄", age=3 }
int x = 10                   0x200: { name="小白", age=1 }
                             0x300: { name="小黑", age=5 }
```

| | 栈 | 堆 |
|------|------|------|
| 放什么 | 局部变量、引用（遥控器） | 对象本身 |
| 大小 | 小，用完就扔 | 大，由 GC 自动回收 |
| 类比 | 桌面（临时摆东西） | 仓库（长期存放） |

**一句记住**：引用在栈，对象在堆。`d1 = d2` 复制的是遥控器不是房子！

---

## 3. 继承 — "是一种"关系

你在 [InheritanceDemo.java](../../../first_java/src/phase03/InheritanceDemo.java) 写过：

```java
class Animal {          // 父类 = 共性
    String name;
    void sleep() { ... }
}

class Dog extends Animal {  // 子类 = 个性 + 共性
    void bark() { ... }     // Dog 特有
}
```

**核心规则：**

| 规则 | 说明 |
|------|------|
| 子类自动拥有父类的字段和方法 | `Dog` 不用写 `sleep()`，从 `Animal` 继承就有了 |
| `super(...)` | 子类构造方法第一行，调用父类构造方法 |
| 单继承 | Java 只能有一个亲爹（但可以实现多个接口） |
| **组合优于继承** | 能用"有一个"(has-a)解决的，别用"是一种"(is-a) |

**快速自检**：什么时候用继承，什么时候用组合？→ 你在 [CompositionDemo.java](../../../first_java/src/phase03/CompositionDemo.java) 对比过这两种写法。

---

## 4. 多态 — 同一个按钮，不同的行为

这是 OOP 最精华的部分，你在 [PolyDemo.java](../../../first_java/src/phase04/PolyDemo.java) 练过：

```java
Animal a1 = new Dog("大黄");   // 声明类型: Animal，实际类型: Dog
Animal a2 = new Cat("咪咪");   // 声明类型: Animal，实际类型: Cat
Animal a3 = new Duck("唐老鸭"); // 声明类型: Animal，实际类型: Duck

a1.makeSound();  // 大黄：汪汪！     ← 同一个方法调用
a2.makeSound();  // 咪咪：喵喵~       ← 不同的行为！
a3.makeSound();  // 唐老鸭：嘎嘎！    ← 这就是多态
```

**三个配套概念：**

| 概念 | 一句话 | 你在哪练过 |
|------|--------|-----------|
| 向上转型 | `Animal a = new Dog();` — 子类自动转父类，安全 | [PolyDemo.java](../../../first_java/src/phase04/PolyDemo.java) |
| 向下转型 | `Dog d = (Dog) a;` — 父类强制转子类，危险，需 `instanceof` | [CastingDemo.java](../../../first_java/src/phase04/CastingDemo.java) |
| `@Override` | 子类重写父类方法，`@Override` 让编译器帮你检查 | 几乎每个 Demo 都有 |

**一句记住**：能调什么方法**看左边**（声明类型），调出来什么行为**看右边**（实际类型）。

---

## 5. 抽象类 vs 接口 — 什么时候用什么

你最后的综合练习 [AbstractDemo.java](../../../first_java/src/phase04/AbstractDemo.java) 清楚地展示了区别：

```java
abstract class AbstractShape {   // 抽象类：有 abstract 关键字
    String name;                  // ✅ 可以有字段
    abstract double area();       // 抽象方法：子类必须实现
    void showInfo() { ... }       // ✅ 可以有普通方法
}

interface Drawable {              // 接口：纯合同
    void draw();                  // 所有方法默认 public abstract
}                                 // ❌ 不能有字段（只能有常量）
```

| | 抽象类 | 接口 |
|------|------|------|
| 关键字 | `abstract class` | `interface` |
| 能 new 吗 | ❌ 不能 | ❌ 不能 |
| 能有字段吗 | ✅ 可以 | ❌ 只能常量 |
| 继承数量 | 单继承 | 多实现 `implements A, B, C` |
| 什么时候用 | "是什么" + 有共享字段 | "能做什么" + 跨类型能力 |

**一句记住**：抽象类 = 没画完的蓝图（共享状态），接口 = 合同/证书（共享能力）。

---

## 🧠 核心四句话

```
1. new    → 堆上建房子，栈上拿遥控器
2. extends → 子承父业，代码复用
3. 多态   → 声明看左边，行为看右边
4. 抽象类 → 共享"是什么"，接口 → 共享"能做什么"
```

---

## ✍️ 动手练习

> 打开你的 `first_java/src/`，随便挑 3 个文件看一遍代码，然后回来回答：
>
> 1. `Animal a = new Dog("旺财"); a.bark();` 为什么编译报错？
> 2. 抽象类和接口最大的区别是什么？（一句话回答）
> 3. 你当时写学生管理系统（[StudentMS.java](../../../first_java/src/phase05_project/StudentMS.java)）用了哪些 OOP 概念？

完成后告诉我，我们接着回顾数据结构！
