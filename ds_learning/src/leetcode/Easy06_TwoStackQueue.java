/**
 * LeetCode #232 — 用栈实现队列 (Easy)
 *
 * ────────── 题目 ──────────
 * 请你仅使用两个栈实现一个先入先出的队列。队列应当支持：
 *   push(x) — 将元素 x 推到队列末尾
 *   pop()   — 移除并返回队列开头元素
 *   peek()  — 返回队列开头元素
 *   empty() — 队列是否为空
 *
 * 你只能用栈的标准操作：push to top、peek/pop from top、size、isEmpty。
 *
 * ────────── 思路 ──────────
 * 两个栈：in（入栈）和 out（出栈）
 *   push → 往 in 里塞
 *   pop  → 从 out 取。out 空就把 in 全倒进 out（顺序正好反转！）
 *
 * 例：push 1,2,3  → in = [1,2,3（栈顶）]
 *     倒到 out:   out = [3,2,1（栈顶）] ← 1 到了栈顶！
 *     pop = 1     ← 刚好是队列的头
 */
import java.util.*;

public class Easy06_TwoStackQueue {
    Deque<Integer> in;
    Deque<Integer> out;

    public Easy06_TwoStackQueue() {
        in  = new LinkedList<>();
        out = new LinkedList<>();
    }

    void push(int x) {
        in.push(x);                       // 永远往 in 里塞
    }

    int pop() {
        dumpIfNeeded();                   // out 空就把 in 全倒进 out
        return out.pop();                 // out 的栈顶就是队列的头
    }

    int peek() {
        dumpIfNeeded();
        return out.peek();
    }

    boolean empty() {
        return in.isEmpty() && out.isEmpty();
    }

    // 核心：out 空了就把 in 全倒过去，顺序正好反转
    void dumpIfNeeded() {
        if (out.isEmpty()) {
            while (!in.isEmpty()) {
                out.push(in.pop());       // in 的栈顶 → out 的栈底
            }
        }
    }

    public static void main(String[] args) {
        Easy06_TwoStackQueue q = new Easy06_TwoStackQueue();
        q.push(1);
        q.push(2);
        System.out.println(q.peek());  // 1
        System.out.println(q.pop());   // 1
        System.out.println(q.empty()); // false
        System.out.println(q.pop());   // 2
        System.out.println(q.empty()); // true
    }
}
