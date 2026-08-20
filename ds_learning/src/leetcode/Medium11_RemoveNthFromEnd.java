/**
 * LeetCode #19 — 删除链表的倒数第N个节点 (Medium)
 *
 * ────────── 题目 ──────────
 * 给定一个链表，删除倒数第 n 个节点，返回链表头。
 *
 * 例1：1→2→3→4→5, n=2  → 1→2→3→5  （删掉倒数第2个，即 4）
 * 例2：1, n=1            → null
 * 例3：1→2, n=1          → 1
 *
 * 进阶：一趟扫描完成。
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
public class Medium11_RemoveNthFromEnd {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    static ListNode removeNthFromEnd(ListNode head, int n) {
        int count = 0;
        ListNode cur = head;
        while(cur != null){
            cur = cur.next;
            count++;
        }
        if (count == 0 || count == 1) return null;
        if (n == count) return head.next;
        ListNode pre = head;
        for (int i = 0; i < count - n - 1; i++){
            pre = pre.next;
        }
        ListNode last = pre.next.next;
        pre.next = last;

        return head;
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
        ListNode l1 = buildList(new int[]{1, 2, 3, 4, 5});
        printList(removeNthFromEnd(l1, 2));  // 1 2 3 5

        ListNode l2 = buildList(new int[]{1, 2});
        printList(removeNthFromEnd(l2, 1));  // 1
    }
}
