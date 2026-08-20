/**
 * LeetCode #238 — 除自身以外数组的乘积 (Medium)
 *
 * ────────── 题目 ──────────
 * 给定数组 nums，返回数组 answer，其中 answer[i] 等于 nums 中除了 nums[i]
 * 之外其余各元素的乘积。要求 O(n)，不能用除法。
 *
 * 例1：nums = [1,2,3,4]
 *  输出: [24,12,8,6]
 *  解释: [2×3×4, 1×3×4, 1×2×4, 1×2×3]
 *
 * 例2：nums = [-1,1,0,-3,3]
 *  输出: [0,0,9,0,0]
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
import java.util.*;

public class Medium20_ProductExceptSelf {

    static int[] productExceptSelf(int[] nums) {
        int[] left = new int[nums.length];
        int[] right = new int[nums.length];
        left[0] = 1;
        right[nums.length - 1] = 1;
        for (int i=0;i<nums.length - 1;i++){
            left[i + 1] = left[i] * nums[i];
            right[nums.length - i - 2] = nums[nums.length - i - 1] * right[nums.length - i - 1];
        }
        for (int i=0;i<nums.length;i++){
            nums[i] = left[i] * right[i];
        }



        return nums;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(productExceptSelf(new int[]{1,2,3,4})));       // [24,12,8,6]
        System.out.println(Arrays.toString(productExceptSelf(new int[]{-1,1,0,-3,3})));   // [0,0,9,0,0]
    }
}
