/**
 * LeetCode #141 — 环形链表 (Easy)
 *
 * ────────── 题目 ──────────
 * 给你一个链表的头节点 head，判断链表中是否有环。
 * 如果链表中有环，返回 true；否则返回 false。
 *
 * 例1：3 → 2 → 0 → -4
 *          ↑         ↓
 *          └─────────┘     (2 到 -4 再回到 2，有环)
 *      输出: true
 *
 * 例2：1 → 2 → 1 → 2 → ...  (有环)
 *      输出: true
 *
 * 例3：1 → 2 → null  (无环)
 *      输出: false
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
public class Easy08_LinkedListCycle {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    static boolean hasCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast)return true;
        }
        return false;
    }

    public static void main(String[] args) {
        // 造一个有环的: 3 → 2 → 0 → -4 → (回到2)
        ListNode n1 = new ListNode(3);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(0);
        ListNode n4 = new ListNode(-4);
        n1.next = n2; n2.next = n3; n3.next = n4; n4.next = n2; // 环
        System.out.println(hasCycle(n1));  // true

        // 无环: 1 → 2 → null
        ListNode m1 = new ListNode(1);
        m1.next = new ListNode(2);
        System.out.println(hasCycle(m1));  // false
    }
}
