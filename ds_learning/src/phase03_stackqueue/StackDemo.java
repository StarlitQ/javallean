package phase03_stackqueue;

/**
 * 👀 Demo — 栈（Stack）：后进先出 LIFO
 *
 * 核心操作就四个：
 * push(x)  — 压入栈顶
 * pop()    — 弹出栈顶
 * peek()   — 看一眼栈顶（不取出）
 * isEmpty() — 栈空了吗？
 */
public class StackDemo {

    public static void main(String[] args) {
        // ===== 1. Java 内置栈（用 Deque 代替老旧的 Stack）=====
        System.out.println("====== Java内置栈 ======");
        java.util.Deque<Integer> stack = new java.util.ArrayDeque<>();
        //java.util.Deque<Integer> stack2 = new java.util.ArrayDeque<>();
        stack.push(10);
        stack.push(20);
        stack.push(30);
        System.out.println("push 10,20,30 后: " + stack);   // [30, 20, 10]
        System.out.println("栈顶 peek: " + stack.peek());      // 30（不取出）
        System.out.println("pop: " + stack.pop());             // 30
        System.out.println("pop: " + stack.pop());             // 20
        System.out.println("pop: " + stack.pop());             // 10
        System.out.println("空了吗? " + stack.isEmpty());       // true

        // ===== 2. 手写栈（数组实现）=====
        System.out.println("\n====== 手写数组栈 ======");
        ArrayStack<Integer> myStack = new ArrayStack<>(5);
        myStack.push(1);
        myStack.push(2);
        myStack.push(3);
        System.out.println("栈顶: " + myStack.peek());         // 3
        System.out.println("pop: " + myStack.pop());           // 3
        System.out.println("pop: " + myStack.pop());           // 2
        myStack.push(4);
        System.out.println("push 4 后再pop: " + myStack.pop()); // 4
        System.out.println("size: " + myStack.size());          // 1

        // ===== 3. 实际应用：括号匹配 =====
        System.out.println("\n====== 应用：括号匹配 ======");
        System.out.println("\"()[]{}\"   → " + isValid("()[]{}"));   // true
        System.out.println("\"([)]\"     → " + isValid("([)]"));     // false
        System.out.println("\"([]{})\"   → " + isValid("([]{})"));   // true

        // ===== 4. 应用：浏览器后退模拟 ======
        System.out.println("\n====== 应用：浏览器后退 ======");
        simulateBrowser();
    }

    // ==== 括号匹配 ====
    // 思路：左括号入栈，右括号跟栈顶匹配
    static boolean isValid(String s) {
        java.util.Deque<Character> stack = new java.util.ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);                          // 左括号入栈
            } else {
                if (stack.isEmpty()) return false;      // 右括号但栈空 → 失衡
                char top = stack.pop();
                if ((c == ')' && top != '(') ||
                    (c == ']' && top != '[') ||
                    (c == '}' && top != '{'))
                    return false;                       // 不匹配
            }
        }
        return stack.isEmpty();                         // 全匹配完栈应该为空
    }

    // ==== 浏览器后退模拟 ====
    static void simulateBrowser() {
        java.util.Deque<String> backStack = new java.util.ArrayDeque<>();
        String current = "主页";

        // 浏览了4个页面
        String[] pages = {"百度", "知乎", "GitHub"};
        for (String page : pages) {
            backStack.push(current);     // 旧页面压入后退栈
            System.out.println("浏览: " + current);
            current = page;              // 跳到新页面
            
        }

        // 点3次后退
        System.out.println("\n--- 按后退 ---");
        for (int i = 0; i < 3; i++) {
            if (!backStack.isEmpty()) {
                current = backStack.pop();  // 弹出上一个页面
                System.out.println("后退到: " + current);
            }
        }
    }
}

/**
 * 手写数组栈 — 理解栈的底层
 * 用一个数组 + 一个 top 指针
 */
class ArrayStack<T> {
    private Object[] data;
    private int top;       // 栈顶指针，-1 表示空栈

    public ArrayStack(int capacity) {
        data = new Object[capacity];
        top = -1;
    }

    // 压栈 O(1)
    public void push(T x) {
        if (top == data.length - 1) throw new RuntimeException("栈满了！");
        data[++top] = x;           // 先移指针，再赋值
    }

    // 弹栈 O(1)
    public T pop() {
        if (top == -1) throw new RuntimeException("栈空了！");
        return (T)data[top--];        // 返回值，再移指针（不需要真的"删除"）
    }

    // 看一眼栈顶 O(1)
    public T peek() {
        if (top == -1) throw new RuntimeException("栈空了！");
        return (T)data[top];
    }

    public int size() { return top + 1; }
    public boolean isEmpty() { return top == -1; }
}
