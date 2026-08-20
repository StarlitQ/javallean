/**
 * LeetCode #206 — 反转链表 (Easy)
 *
 * ────────── 题目 ──────────
 * 给你单链表的头节点 head，请你反转链表，并返回反转后的链表头。
 *
 * 例1：1 → 2 → 3 → 4 → 5
 *      反转后：5 → 4 → 3 → 2 → 1
 *
 * 例2：1 → 2
 *      反转后：2 → 1
 *
 * 例3：空链表 → 返回 null
 *
 * ────────── 提示 ──────────
 * 你 Phase 2 学过单链表。反转就是让每个节点的 next 指回前一个。
 * 需要三个指针：prev（前一个）、cur（当前）、next（暂存下一个）。
 * 画图！画几个节点然后手动改箭头就明白了。
 */
public class Easy02_ReverseList {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    static ListNode reverseList(ListNode head) {
        if(head == null || head.next == null)return head;
        ListNode prev = null;
        ListNode cur = head;
        while(cur != null){
            ListNode tmp = cur.next;
            cur.next = prev;
            prev = cur;
            cur = tmp;
        }
        return prev;
    }

    // 工具：从数组建链表
    static ListNode buildList(int[] arr) {
        ListNode dummy = new ListNode(0), cur = dummy;
        for (int v : arr) {
            cur.next = new ListNode(v);
            cur = cur.next;
        }
        return dummy.next;
    }

    // 工具：打印链表
    static void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        ListNode head = buildList(new int[]{1, 2, 3, 4, 5});
        System.out.print("原链表: "); printList(head);
        head = reverseList(head);
        System.out.print("反转后: "); printList(head);
    }
}
