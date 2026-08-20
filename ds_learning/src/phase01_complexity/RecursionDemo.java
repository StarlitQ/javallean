package phase01_complexity;

/**
 * 👀 Demo — 递归：自己调用自己
 *
 * 递归三要素：
 * 1. 终止条件（base case）—— 什么时候停
 * 2. 递推关系 —— 大问题怎么拆成小问题
 * 3. 每次调用问题规模必须缩小 —— 否则死循环
 */
public class RecursionDemo {

    // ===== 例1：阶乘 n! = n × (n-1) × ... × 1 =====
    // 递推：n! = n × (n-1)!
    // 终止：1! = 1
    static long factorial(int n) {
        if (n == 1) return 1;              // 终止条件
        return n * factorial(n - 1);        // 递推：n! = n × (n-1)!
    }

    // ===== 例2：打印链表（模拟） =====
    // 递推：先打印前面的，再打印自己
    // 终止：v == null（走完了）
    static class Node {
        int val;
        Node next;
        Node(int v) { this.val = v; }
    }

    // 正向打印
    static void printList(Node node) {
        if (node == null) return;           // 终止
        System.out.print(node.val + " ");   // 先打印自己
        printList(node.next);               // 再打印后面的
    }

    // 反向打印——递归的精髓！
    // 递推：先递归调用，回来再打印
    // 效果：1→2→3 变成 3 2 1
    static void printListReverse(Node node) {
        if (node == null) return;
        printListReverse(node.next);        // 先走到最末尾
        System.out.print(node.val + " ");   // 回来再打印
    }

    // ===== 例3：汉诺塔（经典递归谜题）=====
    // 目标：把 n 个盘子从 A 移到 C，B 为中转站
    // 规则：大盘不能压小盘
    // 递推：先移 n-1 个到 B，最大的到 C，再把 n-1 个从 B 移到 C
    // 终止：n == 1，直接移
    // static void hanoi(int n, char from, char to, char via) {
    //     if (n == 1) {
    //         System.out.println(from + " -> " + to);
    //         return;
    //     }
    //     hanoi(n - 1, from, via, to);        // n-1 个从 A 移到 B
    //     System.out.println(from + " -> " + to);  // 最大的从 A 移到 C
    //     hanoi(n - 1, via, to, from);        // n-1 个从 B 移到 C
    // }
    static void hanoi(int n, char from, char to, char via){
        if (n == 1){
            System.out.println(from + "->" +to);
            return;
        }
        hanoi(n-1, from, via, to);
        System.out.println(from + "->" +to);
        hanoi(n-1, via, to, from);
    }

    public static void main(String[] args) {
        // --- 阶乘 ---
        System.out.println("====== 阶乘 ======");
        System.out.println("5! = " + factorial(5));  // 120
        // 调用过程：factorial(5) → 5*f(4) → 5*4*f(3) → ... → 5*4*3*2*1 = 120

        // --- 链表打印 ---
        System.out.println("\n====== 链表打印 ======");
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);

        System.out.print("正向: ");
        printList(head);          // 1 2 3
        System.out.print("\n反向: ");
        printListReverse(head);   // 3 2 1
        System.out.println();

        // --- 汉诺塔 ---
        System.out.println("\n====== 汉诺塔 n=3 ======");
        hanoi(3, 'A', 'C', 'B');
        // 输出 2³-1 = 7 步
    }
}
