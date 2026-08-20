/**
 * LeetCode #234 — 回文链表 (Easy)
 *
 * ────────── 题目 ──────────
 * 判断一个单链表是否是回文（正着读和反着读一样）。
 *
 * 例1：1→2→2→1  → true
 * 例2：1→2       → false
 * 例3：1         → true  （单节点算回文）
 *
 * 进阶：用 O(n) 时间 + O(1) 空间完成。
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
import java.util.*;
public class Easy16_PalindromeList {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    static boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) return true;

        // 1. 快慢找中点
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2. 反转后半段（slow 即后半开头）
        ListNode prev = null;
        while (slow != null) {
            ListNode tmp = slow.next;
            slow.next = prev;
            prev = slow;
            slow = tmp;
        }

        // 3. 从两头往中间比
        ListNode left = head, right = prev;  // prev 是反转后的头
        while (right != null) {
            if (left.val != right.val) return false;
            left = left.next;
            right = right.next;
        }
        return true;
    }

    static ListNode buildList(int[] arr) {
        ListNode dummy = new ListNode(0), cur = dummy;
        for (int v : arr) { cur.next = new ListNode(v); cur = cur.next; }
        return dummy.next;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome(buildList(new int[]{1, 2, 2, 1})));  // true
        System.out.println(isPalindrome(buildList(new int[]{1, 2})));        // false
        System.out.println(isPalindrome(buildList(new int[]{1})));           // true
    }
}
