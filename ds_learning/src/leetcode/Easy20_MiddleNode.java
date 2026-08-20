/**
 * LeetCode #876 — 链表的中间节点 (Easy)
 *
 * ────────── 题目 ──────────
 * 给定一个非空单链表，返回中间节点。如果有两个中间节点，返回第二个。
 *
 * 例1：1→2→3→4→5    → 输出: 3→4→5  （返回节点 3）
 * 例2：1→2→3→4→5→6  → 输出: 4→5→6  （返回节点 4，即第二个中间节点）
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
public class Easy20_MiddleNode {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    static ListNode middleNode(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    static ListNode buildList(int[] arr) {
        ListNode dummy = new ListNode(0), cur = dummy;
        for (int v : arr) { cur.next = new ListNode(v); cur = cur.next; }
        return dummy.next;
    }

    public static void main(String[] args) {
        ListNode head1 = buildList(new int[]{1, 2, 3, 4, 5});
        System.out.println(middleNode(head1).val);  // 3

        ListNode head2 = buildList(new int[]{1, 2, 3, 4, 5, 6});
        System.out.println(middleNode(head2).val);  // 4
    }
}
