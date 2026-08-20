/**
 * LeetCode #169 — 多数元素 (Easy)
 *
 * ────────── 题目 ──────────
 * 给定一个大小为 n 的数组，返回其中的多数元素。
 * 多数元素是指在数组中出现次数大于 n/2 的元素。假设数组总是存在多数元素。
 *
 * 例1：nums = [3,2,3]           → 输出: 3
 * 例2：nums = [2,2,1,1,1,2,2]   → 输出: 2
 *
 * 进阶：O(n) 时间 + O(1) 空间。
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
import java.util.*;
public class Easy19_MajorityElement {

    // ======== 方法1：HashMap 暴力 ========
    static int majorityElementHashMap(int[] nums) {
        int len = nums.length;
        int half = len / 2;
        HashMap<Integer, Integer> tried = new HashMap<>();
        for (int i = 0; i < len; i++) {
            tried.put(nums[i], tried.getOrDefault(nums[i], 0) + 1);
        }
        for (int key : tried.keySet()) {
            if (tried.get(key) > half) return key;
        }
        return -1;  // 题目保证不会到这
    }

    // ======== 方法2：Boyer-Moore 投票 O(1) 空间 ========
    static int majorityElement(int[] nums) {
        int candidate = 0;
        int count = 0;
        // 你的代码
        for (int n : nums){
            if (count == 0) candidate = n;
            count += (n == candidate)? 1: -1;
        }
        return candidate;
    }

    public static void main(String[] args) {
        System.out.println(majorityElementHashMap(new int[]{3, 2, 3}));               // 3
        System.out.println(majorityElementHashMap(new int[]{2, 2, 1, 1, 1, 2, 2}));   // 2
        System.out.println(majorityElement(new int[]{3, 2, 3}));                      // 3
        System.out.println(majorityElement(new int[]{2, 2, 1, 1, 1, 2, 2}));          // 2
    }
}
