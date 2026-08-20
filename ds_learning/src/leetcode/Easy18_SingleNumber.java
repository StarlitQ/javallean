/**
 * LeetCode #136 — 只出现一次的数字 (Easy)
 *
 * ────────── 题目 ──────────
 * 给定一个非空整数数组，除了某个元素只出现一次外，其余每个元素均出现两次。
 * 找出那个只出现一次的元素。要求 O(n) 时间，O(1) 额外空间。
 *
 * 例1：nums = [2,2,1]      → 输出: 1
 * 例2：nums = [4,1,2,1,2]  → 输出: 4
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
public class Easy18_SingleNumber {

    static int singleNumber(int[] nums) {
        int result = 0;
        for (int n : nums) result ^= n;
        return result;
    }

    public static void main(String[] args) {
        System.out.println(singleNumber(new int[]{2, 2, 1}));         // 1
        System.out.println(singleNumber(new int[]{4, 1, 2, 1, 2}));   // 4
        System.out.println(singleNumber(new int[]{1}));               // 1
    }
}
