# 🔄 数据结构 Phase 1-4 回顾 + 泛型补课

> **目标**：快速找回 4 个 Phase 的手感，补上遗漏的泛型知识，为学图做好准备。

你已完成的代码在 `ds_learning/src/` 下，可以 Ctrl+点击跳过去看。

---

## Part A：Phase 1-4 核心回顾

---

## Phase 1：复杂度 + 递归

### 大O记号 — 看增长速度

你在 [TimeComplexityDemo.java](../../src/phase01_complexity/TimeComplexityDemo.java) 练过：

| 复杂度 | 名称 | 现实比喻 | 1万条数据 |
|------|------|------|------|
| O(1) | 常数 | 眨一下眼 | 1 步 |
| O(log n) | 对数 | 翻字典（每次砍半） | ~13 步 |
| O(n) | 线性 | 一个一个翻 | 1万步 |
| O(n log n) | 线性对数 | 高效排序 | ~13万步 |
| O(n²) | 平方 | 两两配对 | 1亿步 💀 |

**核心思想**：大O不看具体多少步，看**增长速度**。n 翻 10 倍，O(n) 慢 10 倍，O(n²) 慢 100 倍。

### 空间复杂度

你在 [SpaceComplexityDemo.java](../../src/phase01_complexity/SpaceComplexityDemo.java) 见过这张对比表：

| 方案 | 时间 | 额外空间 | 思路 |
|------|------|------|------|
| 暴力 | O(n²) | O(1) | 时间换空间 |
| 开数组 | O(n) | O(n) | 空间换时间 |
| 滚动变量 | O(n) | O(1) | 两者兼得 ✨ |

**一句记住**：时空可以互换，最优算法两者都省。

### 递归 — 自己调自己

你在 [RecursionDemo.java](../../src/phase01_complexity/RecursionDemo.java) 写过：

```java
// 递归两个要素：
// ① 终止条件（没有它 = 无限递归 = 栈溢出）
// ② 每次问题规模缩小

int factorial(int n) {
    if (n <= 1) return 1;             // ← 终止条件！
    return n * factorial(n - 1);      // ← 规模缩小
}
```

**过程**：`factorial(4)` → 先一路走到 `factorial(1)`（这叫**递**），再一路乘回来（这叫**归**）。

---

## Phase 2：线性表 — 顺序表 vs 链表

这是数据结构最常用的两个选择，你在 [SeqListDemo.java](../../src/phase02_linearlist/SeqListDemo.java) 和 [LinkedListDemo.java](../../src/phase02_linearlist/LinkedListDemo.java) 都手写实现过：

```
顺序表 (ArrayList)              链表 (LinkedList)
─────────────────              ───────────────
底层：Object[] 数组             底层：节点 Node
                                  data | next ──→ data | next ──→ ...

[10][20][30][40]               10 → 20 → 30 → 40 → null
 连续一大块内存                  散落在内存各处
```

| 操作 | 顺序表 | 链表 |
|------|------|------|
| 按下标访问 `get(i)` | **O(1)** 🏆 | O(n) 从头数 |
| 头部插入 | O(n) 全员后移 | **O(1)** 🏆 动指针 |
| 尾部追加 | O(1) | O(n) 或 O(1) 有tail |
| 中间插入/删除 | O(n) 挪元素 | O(n) 先找到 + O(1) 改指针 |
| 内存 | 连续，扩容翻倍复制 | 散落，每个节点多存一个指针 |

**选型口诀**：**查多改少用顺序表，插删多用链表。**

### 链表操作的灵魂：动指针

你在 [LinkedListDemo.java](../../src/phase02_linearlist/LinkedListDemo.java) 练过的核心技巧——中间插入只改 2 次指针，不用搬任何元素：

```java
// 在 a 和 b 之间插入 c：
c.next = a.next;   // c 先牵上 b
a.next = c;        // a 再牵上 c
// 就两步！如果顺序表，b 及后面全部元素都要后移
```

### 双向链表

你在 [DoublyLinkedListDemo.java](../../src/phase02_linearlist/DoublyLinkedListDemo.java) 见过——每个节点多了 `prev` 指针，再加上 `tail` 引用，首尾操作全变 O(1)。Java 官方的 `LinkedList` 就是双向链表。

---

## Phase 3：栈与队列

### 栈 Stack — 后进先出 LIFO

你在 [StackDemo.java](../../src/phase03_stackqueue/StackDemo.java) 手写了数组栈：

```
push(10) push(20) push(30)
        栈顶 ↓
      ┌───┐
      │30 │  ← 最后进的
      │20 │
      │10 │  ← 最先进的
      └───┘
pop() → 30（最后进的先出去）
```

| 操作 | 代码 | 复杂度 |
|------|------|------|
| 压栈 | `push(x)` | O(1) |
| 弹栈 | `pop()` | O(1) |
| 看栈顶 | `peek()` | O(1) |

**底层**：数组 + `top` 指针。`push` 先移指针再赋值，`pop` 返回值再移指针。

**经典应用**：括号匹配、浏览器后退、函数调用栈。

### 队列 Queue — 先进先出 FIFO

你在 [QueueDemo.java](../../src/phase03_stackqueue/QueueDemo.java) 实现过：

```
队尾入 →  offer(10) offer(20) offer(30)
         ┌───┬───┬───┐
         │10 │20 │30 │
         └───┴───┴───┘
         队头 → poll() = 10（最先进来的先出去）
```

| 操作 | 代码 | 复杂度 |
|------|------|------|
| 入队 | `offer(x)` | O(1) |
| 出队 | `poll()` | O(1) |
| 看队头 | `peek()` | O(1) |

**底层**：循环数组 + `front` + `rear` + 取余绕圈。

**经典应用**：排队系统、BFS（图的广度优先遍历！马上就会用到）、任务调度（你写过 [TaskScheduler.java](../../src/phase03_stackqueue/TaskScheduler.java)）。

---

## Phase 4：树

### 二叉树遍历 — 看根的时机

你在 [BinaryTreeDemo.java](../../src/phase04_tree/BinaryTreeDemo.java) 练过三种遍历：

```
        A
       / \
      B   C
     / \
    D   E

前序（根→左→右）：A B D E C   ← 根最先
中序（左→根→右）：D B E A C   ← 根在中间
后序（左→右→根）：D E B C A   ← 根最后
```

递归套路都一样，只有 `print` 的位置不同：

```java
void preOrder(Node n)  { if(n==null) return; print(n); preOrder(n.left); preOrder(n.right); }
void inOrder(Node n)   { if(n==null) return; inOrder(n.left); print(n); inOrder(n.right); }
void postOrder(Node n) { if(n==null) return; postOrder(n.left); postOrder(n.right); print(n); }
```

### 二叉搜索树 BST — 左边小右边大

你在 [BSTDemo.java](../../src/phase04_tree/BSTDemo.java) 实现过查找和插入：

**核心规则**：对任意节点，左边全 < 它，右边全 > 它。

- **查找** = 二分查找：小了去左边，大了去右边，平均 O(log n)
- **中序遍历** = 从小到大自动排序！
- **最坏情况**：按顺序插入 → 退化成链 → O(n)

### 堆 Heap — 完全二叉树用数组

你在 [HeapDemo.java](../../src/phase04_tree/HeapDemo.java) 实现过最小堆：

```
数组存完全二叉树：
         [0] [1] [2] [3] [4] [5]
         ───────────────────────
树:       3
        /   \              父 = (i-1)/2
       5     8             左子 = 2i+1
      / \   /              右子 = 2i+2
     7   9 10
```

- **插入**：放末尾 → 上浮 O(log n)
- **删除堆顶**：末尾换上去 → 下沉 O(log n)
- **堆顶永远是最值**（最小堆堆顶最小，最大堆堆顶最大）

---

## Part B：泛型 — 补上遗漏的知识

你之前在 `MyArrayList` 里用 `Object[]` 存数据，`get()` 返回 `Object`，取出来还要强转。泛型就是来解决这个问题的。

### 没有泛型的世界（你写的代码）

```java
class MyArrayList {
    private Object[] data;     // 啥都能存，但不知道具体是啥类型
    
    public void add(Object x) { ... }
    public Object get(int index) { return data[index]; }  // 返回 Object
}

// 使用时：
MyArrayList list = new MyArrayList();
list.add("hello");
list.add(123);           // ⚠️ 字符串和整数混一起了！不会报错
String s = (String) list.get(0);  // 必须强转，烦人
String s2 = (String) list.get(1); // 😱 编译通过，运行时报 ClassCastException！
```

### 有泛型的世界

```java
class MyArrayList<T> {        // <T> = "不确定是啥类型，用的时候再定"
    private Object[] data;    // 底层还是 Object[]（Java 泛型擦除）
    
    public void add(T x) { ... }        // 参数是 T 类型
    public T get(int index) {           // 返回 T 类型
        return (T) data[index];         // 内部强转，对外干净
    }
}

// 使用时：
MyArrayList<String> list = new MyArrayList<>();  // T = String
list.add("hello");
// list.add(123);  // ❌ 编译就报错！不能混入整数
String s = list.get(0);  // ✅ 不用强转！编译器知道返回的一定是 String
```

### 泛型语法速查

```java
// ① 泛型类
class Box<T> {                    // T = Type，占位符，叫什么都可以
    private T item;               // 字段用 T
    public void set(T item) { }   // 参数用 T
    public T get() { return item; }  // 返回值用 T
}
Box<String> b1 = new Box<>();     // T 换成 String
Box<Integer> b2 = new Box<>();    // T 换成 Integer

// ② 泛型接口（你在写 Comparable 时可能见过）
interface Comparable<T> {
    int compareTo(T o);
}

// ③ 泛型方法
public static <T> void print(T x) {   // <T> 写在返回值前面
    System.out.println(x);
}
```

### 什么时候用泛型

**写容器类（数据结构）的时候！** 你手写的 MyArrayList、ArrayStack、链表……如果没加泛型，那就是你看到的——全部用 `Object`，取出来要强转，类型不安全。

加了泛型 `<T>` 之后 → **类型安全 + 不用强转 + 编译期检查**。

---

## 🧠 Phase 1-4 核心一句话总结

| Phase | 一句话 |
|------|------|
| 复杂度 | 大O看增长率，O(1) < O(log n) < O(n) < O(n²) |
| 顺序表 | 数组+size，get快，插入慢，扩容翻倍 |
| 链表 | 节点+next，改指针O(1)，找位置O(n) |
| 栈 | LIFO，数组+top，push/pop/peek 全 O(1) |
| 队列 | FIFO，循环数组取余，offer/poll/peek 全 O(1) |
| 二叉树 | 三种遍历看根，递归套路一模一样 |
| BST | 左小右大，查找=二分 O(log n)，中序=排序 |
| 堆 | 完全二叉树数组存，上浮下沉 O(log n)，堆顶=最值 |
| 泛型 | `<T>` = 类型的占位符，编译时检查不用强转 |

---

## ✍️ 回顾练习

> 在你的 `ds_learning/src/` 里操作，不必完美，目的是找回手感。

### 练习 1（泛型改造）：把你手写的 `ArrayStack` 改成泛型版

原始代码在 [StackDemo.java](../../src/phase03_stackqueue/StackDemo.java)，你用 `int[]` 存的。改成 `GenericStack<T>`，支持任意类型。

提示：`int[] data` → `Object[] data`，`push(int x)` → `push(T x)`。

### 练习 2（链表再练）：实现 `size()` 方法

在 [LinkedListDemo.java](../../src/phase02_linearlist/LinkedListDemo.java) 里给链表加一个计算长度的方法。

### 练习 3（递归手感）：用递归反转链表

你之前用三指针法（迭代）实现了反转，试试用递归。（提示：`reverse(node.next)` 之后，把 node 接到末尾）

---

完成 1-2 个练习后告诉我，或者直接说"开始学图"，我们就进入 Phase 5！
