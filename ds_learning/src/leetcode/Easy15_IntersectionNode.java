/**
 * LeetCode #160 — 相交链表 (Easy)
 *
 * ────────── 题目 ──────────
 * 找出两个单链表相交的起始节点。如果没有相交，返回 null。
 * 链表必须保持原始结构。
 *
 * 例1：a1 → a2
 *             ↘
 *              c1 → c2 → c3
 *             ↗
 *       b1 → b2 → b3
 *      相交节点: c1
 *
 * 例2：1 → 9 → 1
 *             ↘
 *              2 → 4
 *             ↗
 *             3
 *      相交节点: 2
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
public class Easy15_IntersectionNode {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    // 暴力 O(m×n)
    static ListNode getIntersectionNodeBrute(ListNode headA, ListNode headB) {
        for (ListNode pA = headA; pA != null; pA = pA.next)
            for (ListNode pB = headB; pB != null; pB = pB.next)
                if (pA == pB) return pA;
        return null;
    }

    // 双指针 O(m+n)：各走一遍 A+B，必同时到交点（或同时到 null）
    static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode pA = headA, pB = headB;
        while (pA != pB) {
            pA = pA == null ? headB : pA.next;
            pB = pB == null ? headA : pB.next;
        }
        return pA;  // 要么是交点，要么两个都 null
    }

    public static void main(String[] args) {
        // 构造: A: 4→1→8→4→5
        //       B:    5→6→1→8→4→5  (后半段 8→4→5 共享)
        ListNode c1 = new ListNode(8);
        c1.next = new ListNode(4);
        c1.next.next = new ListNode(5);

        ListNode a1 = new ListNode(4);
        a1.next = new ListNode(1);
        a1.next.next = c1;

        ListNode b1 = new ListNode(5);
        b1.next = new ListNode(6);
        b1.next.next = new ListNode(1);
        b1.next.next.next = c1;

        ListNode result = getIntersectionNode(a1, b1);
        System.out.println(result != null ? result.val : "null");  // 8
    }
}
