package phase02_linearlist;

/**
 * 👀 Demo — 双向链表
 *
 * 单链表 vs 双向链表：
 * ┌──────────┬──────────────┬──────────────┐
 * │          │ 单链表        │ 双向链表      │
 * ├──────────┼──────────────┼──────────────┤
 * │ 节点结构 │ data + next  │ data+next+prev│
 * │ 头插     │ O(1)         │ O(1)          │
 * │ 尾插     │ O(n) 走到底  │ O(1) tail直达 │
 * │ 尾删     │ O(n)         │ O(1)          │
 * │ 回退     │ ❌ 不能      │ ✅ 沿着prev走 │
 * │ 内存     │ 省           │ 多一个指针     │
 * └──────────┴──────────────┴──────────────┘
 */
public class DoublyLinkedListDemo {

    static class DNode {
        int data;
        DNode prev;
        DNode next;

        DNode(int data) { this.data = data; }
    }

    // 带头尾指针的链表（这样尾插就能 O(1) 了）
    static class DList {
        DNode head;
        DNode tail;

        // 头插 O(1)
        void addFirst(int val) {
            DNode node = new DNode(val);
            if (head == null) {
                head = tail = node;
                return;
            }
            node.next = head;
            head.prev = node;
            head = node;
        }

        // 尾插 O(1) —— 单链表做不到！
        void addLast(int val) {
            DNode node = new DNode(val);
            if (tail == null) {
                head = tail = node;
                return;
            }
            tail.next = node;
            node.prev = tail;
            tail = node;
        }

        // 删除指定节点 O(1)
        void remove(DNode node) {
            if (node.prev != null) {
                node.prev.next = node.next;   // 前一个绕过后一个
            } else {
                head = node.next;             // 删的是头
            }
            if (node.next != null) {
                node.next.prev = node.prev;   // 后一个绕过前一个
            } else {
                tail = node.prev;             // 删的是尾
            }
        }

        // 正向打印
        void printForward() {
            DNode cur = head;
            while (cur != null) {
                System.out.print(cur.data);
                if (cur.next != null) System.out.print(" → ");
                cur = cur.next;
            }
            System.out.println(" → null");
        }

        // 反向打印 —— 单链表做不到！
        void printBackward() {
            DNode cur = tail;
            while (cur != null) {
                System.out.print(cur.data);
                if (cur.prev != null) System.out.print(" ← ");
                cur = cur.prev;
            }
            System.out.println(" ← null");
        }

        boolean contains(int val){
            DNode cur = tail;
            while(cur != null){
                if (cur.data == val)return true;
                cur = cur.prev;
            }
            return false;
        }
    }
    

    public static void main(String[] args) {
        DList list = new DList();

        System.out.println("====== 头插和尾插 ======");
        list.addFirst(2);
        list.addFirst(1);
        list.addLast(3);
        list.addLast(4);
        System.out.print("正向: "); list.printForward();   // 1 → 2 → 3 → 4
        System.out.print("反向: "); list.printBackward();  // 4 ← 3 ← 2 ← 1

        System.out.println("\n====== 删除节点2 ======");
        DNode node2 = list.head.next;   // 找到值为2的节点
        list.remove(node2);
        System.out.print("正向: "); list.printForward();   // 1 → 3 → 4

        System.out.println("\n====== 删除头和尾 ======");
        list.remove(list.head);         // 删头
        list.remove(list.tail);         // 删尾
        System.out.print("正向: "); list.printForward();   // 3

        System.out.println("\n====== contains ======");
        System.out.println("包含3? " + list.contains(3));  // true
        System.out.println("包含5? " + list.contains(5));  // false
    }
}
