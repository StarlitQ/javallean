/**
 * LeetCode #21 — 合并两个有序链表 (Easy)
 *
 * ────────── 题目 ──────────
 * 将两个升序链表合并为一个新的升序链表并返回。
 *
 * 例：l1 = 1 → 2 → 4
 *     l2 = 1 → 3 → 4
 *     合并: 1 → 1 → 2 → 3 → 4 → 4
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
public class Easy09_MergeSortedLists {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        ListNode result = new ListNode(0);
        ListNode cur = result;
        int small;
        while(l1!=null && l2!=null){
            if (l1.val <= l2.val){small = l1.val; l1 = l1.next;}
            else {small = l2.val; l2 = l2.next;}
            cur.next = new ListNode(small);
            cur = cur.next;
        }
        if (l1 != null) cur.next = l1;
        else cur.next = l2;

        return result.next;
    }

    static ListNode buildList(int[] arr) {
        ListNode dummy = new ListNode(0), cur = dummy;
        for (int v : arr) { cur.next = new ListNode(v); cur = cur.next; }
        return dummy.next;
    }

    static void printList(ListNode head) {
        while (head != null) { System.out.print(head.val + " "); head = head.next; }
        System.out.println();
    }

    public static void main(String[] args) {
        ListNode l1 = buildList(new int[]{1, 2, 4});
        ListNode l2 = buildList(new int[]{1, 3, 4});
        ListNode merged = mergeTwoLists(l1, l2);
        printList(merged);  // 1 1 2 3 4 4
    }
}
