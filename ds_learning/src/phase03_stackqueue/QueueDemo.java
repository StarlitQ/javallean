package phase03_stackqueue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Deque;
import java.util.ArrayDeque;

/**
 * 👀 Demo — 队列（Queue）：先进先出 FIFO
 *
 * 核心操作：
 * offer(x) — 入队（加到队尾）
 * poll()   — 出队（从队头取走）
 * peek()   — 看一眼队头（不取出）
 * isEmpty()— 队空了吗？
 *
 * 栈 vs 队列：
 * ┌──────┬────────┬────────┐
 * │      │ 栈     │ 队列   │
 * ├──────┼────────┼────────┤
 * │ 规则 │ LIFO   │ FIFO   │
 * │ 插入 │ push   │ offer  │
 * │ 取出 │ pop    │ poll   │
 * │ 看顶 │ peek   │ peek   │
 * └──────┴────────┴────────┘
 */
public class QueueDemo {

    public static void main(String[] args) {
        // ===== 1. Java 内置队列 =====
        System.out.println("====== Java 内置队列 ======");
        // LinkedList 实现了 Queue 接口
        Queue<Integer> queue = new LinkedList<>();

        queue.offer(10);
        queue.offer(20);
        queue.offer(30);
        System.out.println("offer 10,20,30 后: " + queue);  // [10, 20, 30]

        System.out.println("队头 peek: " + queue.peek());     // 10（不取出）
        System.out.println("poll: " + queue.poll());          // 10
        System.out.println("poll: " + queue.poll());          // 20
        System.out.println("还剩: " + queue);                  // [30]
        System.out.println("poll: " + queue.poll());          // 30
        System.out.println("空了吗? " + queue.isEmpty());      // true

        // ===== 2. 手写循环队列（数组实现）=====
        System.out.println("\n====== 手写循环队列 ======");
        MyQueue mq = new MyQueue(4);  // 容量4，最多存3个（留一个空位区分空/满）
        mq.offer(1);
        mq.offer(2);
        mq.offer(3);
        System.out.println("队头: " + mq.peek());       // 1
        System.out.println("poll: " + mq.poll());        // 1
        System.out.println("poll: " + mq.poll());        // 2
        mq.offer(4);
        mq.offer(5);
        System.out.print("剩余: ");
        while (!mq.isEmpty()) {
            System.out.print(mq.poll() + " ");           // 3 4 5
        }
        System.out.println();

        // ===== 3. 队列 vs 栈 直观对比 =====
        System.out.println("\n====== 同一个数据：栈 vs 队列 ======");
        int[] data = {1, 2, 3};
        System.out.println("输入顺序: 1, 2, 3");

        Deque<Integer> stack = new ArrayDeque<>();
        Queue<Integer> q = new LinkedList<>();
        for (int x : data) { stack.push(x); q.offer(x); }

        System.out.print("栈输出:   ");
        while (!stack.isEmpty()) System.out.print(stack.pop() + " ");  // 3 2 1
        System.out.print("\n队列输出: ");
        while (!q.isEmpty()) System.out.print(q.poll() + " ");          // 1 2 3
        System.out.println();
    }
}

/**
 * 手写循环队列
 *
 * 为什么循环？pop 后头指针后移，前面空出来的空间不能浪费，尾指针绕回去用。
 *
 * front — 队头下标
 * rear  — 队尾下一个空位下标
 * 判空: front == rear
 * 判满: (rear + 1) % capacity == front  （牺牲一个格子）
 */
class MyQueue {
    private int[] data;
    private int front;   // 队头
    private int rear;    // 队尾下一个空位
    private int capacity;

    public MyQueue(int cap) {
        this.capacity = cap + 1;  // 多开一个，用于判满
        data = new int[this.capacity];
        front = rear = 0;
    }

    // 入队 O(1)
    public void offer(int x) {
        if ((rear + 1) % capacity == front) {
            throw new RuntimeException("队列满了！");
        }
        data[rear] = x;
        rear = (rear + 1) % capacity;  // 绕圈
    }

    // 出队 O(1)
    public int poll() {
        if (front == rear) throw new RuntimeException("队列空了！");
        int val = data[front];
        front = (front + 1) % capacity;  // 绕圈
        return val;
    }

    public int peek() {
        if (front == rear) throw new RuntimeException("队列空了！");
        return data[front];
    }

    public boolean isEmpty() { return front == rear; }
}
