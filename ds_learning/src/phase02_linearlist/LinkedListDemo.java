package phase02_linearlist;

/**
 * 👀 Demo — 单链表：自己造一个 MyLinkedList
 *
 * 链表 vs 顺序表：
 * ┌──────────┬─────────────┬─────────────┐
 * │          │ 顺序表       │ 链表         │
 * ├──────────┼─────────────┼─────────────┤
 * │ 找第i个  │ O(1) 秒找   │ O(n) 从头数 │
 * │ 首部插入 │ O(n) 全后移 │ O(1) 动指针 │
 * │ 中间插入 │ O(n)        │ O(n) 先找到 │
 * │ 删除     │ O(n)        │ O(1) 改指针 │
 * │ 内存     │ 一大片连续   │ 散落各地     │
 * └──────────┴─────────────┴─────────────┘
 */
public class LinkedListDemo {

    public static void main(String[] args) {
        // ===== 1. 手造链表：1→2→3→null =====
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);

        System.out.println(countLength(head));

        System.out.println("====== 遍历打印 ======");
        printList(head);   // 1 → 2 → 3 → null

        // ===== 2. 头插法 O(1) =====
        System.out.println("\n====== 头插法 ======");
        head = insertAtHead(head, 99);
        printList(head);   // 99 → 1 → 2 → 3 → null

        // ===== 3. 尾插法 O(n) =====
        System.out.println("\n====== 尾插法 ======");
        head = insertAtTail(head, 88);
        printList(head);   // 99 → 1 → 2 → 3 → 88 → null

        // ===== 4. 删除 =====
        System.out.println("\n====== 删除值为1的节点 ======");
        head = deleteByValue(head, 1);
        printList(head);   // 99 → 2 → 3 → 88 → null

        // ===== 5. 链表反转（经典面试题）=====
        System.out.println("\n====== 反转链表 ======");
        head = reverse(head);
        printList(head);   // 88 → 3 → 2 → 99 → null

        // ===== 6. 演示：链表插入只改两个指针 =====
        System.out.println("\n====== 指针操作演示 ======");
        Node a = new Node(1);
        Node b = new Node(3);
        a.next = b;
        System.out.print("a.next = b   → "); printList(a);

        // 在 a 和 b 之间插入 c
        Node c = new Node(2);
        c.next = a.next;   // c 牵上 b
        a.next = c;        // a 牵上 c
        System.out.print("插入(2)后    → "); printList(a);
        // 只改了两次指针！不需要把 b 往后挪
    }

    // ============ 链表操作 ============

    // 遍历打印
    static void printList(Node head) {
        Node current = head;
        while (current != null) {
            System.out.print(current.data);
            if (current.next != null) System.out.print(" → ");
            current = current.next;
        }
        System.out.println(" → null");
    }

    // 头插：新节点变成新的 head
    static Node insertAtHead(Node head, int val) {
        Node newNode = new Node(val);
        newNode.next = head;  // 新人牵上原来的第一个
        return newNode;       // 新人就是新头
    }

    // 尾插：走到最后，附加新节点
    static Node insertAtTail(Node head, int val) {
        Node newNode = new Node(val);
        if (head == null) return newNode;

        Node current = head;
        while (current.next != null) {  // 走到最后一个
            current = current.next;
        }
        current.next = newNode;         // 最后一个人牵上新人
        return head;
    }

    // 删除指定值的节点
    static Node deleteByValue(Node head, int val) {
        if (head == null) return null;
        if (head.data == val) return head.next;  // 删的是头，直接返回下一个

        Node current = head;
        while (current.next != null) {
            if (current.next.data == val) {
                current.next = current.next.next;  // 跨过要删的节点
                return head;
            }
            current = current.next;
        }
        return head;  // 没找到
    }

    // 反转链表（三指针法）
    static Node reverse(Node head) {
        Node prev = null;
        Node current = head;
        while (current != null) {
            Node next = current.next;  // 先记住下一个
            current.next = prev;       // 掉转方向！
            prev = current;            // 三人组整体前移
            current = next;
        }
        return prev;  // prev 就是新头
    }

    static Node reverse2(Node head){
        Node nhead;
        if(head.next == null)return head;
        nhead = reverse2(head.next);
        head.next.next = head;
        head.next = null;
        return nhead;
    }

    // 递归反转 — 对照版
    static Node reverse3(Node head) {
        // 空链表或只有一个节点：不用反转，直接返回
        if (head == null || head.next == null) return head;

        Node newHead = reverse3(head.next);  // ① 递归反转后面的
        head.next.next = head;               // ② 让下一个节点回头指自己
        head.next = null;                    // ③ 掐断自己向前的线
        return newHead;                      // ④ 新头一路传回去
    }

    static int countLength(Node head){
        int len = 0;
        while(head != null){
            len++;
            head = head.next;

        }
        return len;
    }

}


// 链表的"细胞"——节点
class Node {
    int data;       // 存的数据
    Node next;      // 指向下一个节点

    Node(int data) {
        this.data = data;
    }
}
