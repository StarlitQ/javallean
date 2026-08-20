/**
 * LeetCode #128 — 最长连续序列 (Medium)
 *
 * ────────── 题目 ──────────
 * 给定未排序的整数数组，找出最长连续序列的长度。要求 O(n)。
 *
 * 例1：nums = [100,4,200,1,3,2]
 *  输出: 4  （最长连续序列是 [1,2,3,4]）
 *
 * 例2：nums = [0,3,7,2,5,8,4,6,0,1]
 *  输出: 9
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
import java.util.*;

public class Medium29_LongestConsecutive {

    static int longestConsecutive(int[] nums) {
        HashSet<Integer> map = new HashSet<>();
        for (int n : nums){
            map.add(n);
        }
        int max = 0;
        for (int n : nums){
            if (map.contains(n - 1)){
                continue;
            }
            int len = 1;
            while (map.contains(n + 1)){
                n++;
                len++;
            }
            max = Math.max(len, max);
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(longestConsecutive(new int[]{100,4,200,1,3,2}));          // 4
        System.out.println(longestConsecutive(new int[]{0,3,7,2,5,8,4,6,0,1}));      // 9
    }
}
