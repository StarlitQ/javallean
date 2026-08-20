/**
 * LeetCode #53 — 最大子数组和 (Medium)
 *
 * ────────── 题目 ──────────
 * 给定一个整数数组 nums，找到一个具有最大和的连续子数组（子数组最少包含一个元素），
 * 返回其最大和。
 *
 * 例1：nums = [-2,1,-3,4,-1,2,1,-5,4]  → 输出: 6
 *      子数组 [4,-1,2,1] 的和最大 = 6
 *
 * 例2：nums = [1]      → 输出: 1
 * 例3：nums = [5,4,-1,7,8]  → 输出: 23
 *
 * 进阶：分治法。
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
public class Easy23_MaxSubArray {

    static int maxSubArray(int[] nums) {
        int len = nums.length;
        int max = nums[0];
        int cur = 0;
        for(int i = 0; i < len; i++){
            if (cur < 0){
                cur = nums[i];
            }
            else{
                cur += nums[i];
            }
            if (cur > max) max = cur;
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(maxSubArray(new int[]{-2,1,-3,4,-1,2,1,-5,4}));  // 6
        System.out.println(maxSubArray(new int[]{1}));                       // 1
        System.out.println(maxSubArray(new int[]{5,4,-1,7,8}));             // 23
    }
}
