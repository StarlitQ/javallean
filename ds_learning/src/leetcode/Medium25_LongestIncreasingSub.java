/**
 * LeetCode #300 — 最长递增子序列 (Medium)
 *
 * ────────── 题目 ──────────
 * 给定整数数组 nums，找出最长严格递增子序列的长度（子序列不要求连续）。
 *
 * 例1：nums = [10,9,2,5,3,7,101,18]
 *  输出: 4（最长递增子序列是 [2,3,7,101]）
 *
 * 例2：nums = [0,1,0,3,2,3]
 *  输出: 4（[0,1,2,3]）
 *
 * 例3：nums = [7,7,7,7,7]
 *  输出: 1
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
import java.util.*;

public class Medium25_LongestIncreasingSub {

    static int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        for (int i=0;i<nums.length;i++) dp[i] = 1;
        for (int i=0;i<nums.length;i++){
            for (int j=0;j<i;j++){
                if (nums[j] < nums[i]){
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        int max = 1;
        for (int i=0;i<nums.length;i++){
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLIS(new int[]{10,9,2,5,3,7,101,18}));  // 4
        System.out.println(lengthOfLIS(new int[]{0,1,0,3,2,3}));          // 4
        System.out.println(lengthOfLIS(new int[]{7,7,7,7,7}));            // 1
    }
}
