/**
 * LeetCode #34 — 在排序数组中查找元素的第一个和最后一个位置 (Medium)
 *
 * ────────── 题目 ──────────
 * 给定升序数组 nums 和目标值 target，找出 target 的起始和结束位置。
 * 要求 O(log n)。不存在则返回 [-1, -1]。
 *
 * 例1：nums = [5,7,7,8,8,10], target = 8 → 输出: [3,4]
 * 例2：nums = [5,7,7,8,8,10], target = 6 → 输出: [-1,-1]
 * 例3：nums = [], target = 0              → 输出: [-1,-1]
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
import java.util.*;

public class Medium28_FindFirstLast {

    static int[] searchRange(int[] nums, int target) {
        if (nums.length == 0) return new int[]{-1, -1};

        // 1. 找左边界：nums[mid] >= target 时往左缩
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] >= target)
                right = mid;        // mid 可能是左边界，不跳过
            else
                left = mid + 1;
        }
        if (nums[left] != target) return new int[]{-1, -1};  // 不存在

        int start = left;

        // 2. 找右边界：nums[mid] <= target 时往右缩
        right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left + 1) / 2;  // +1 让 mid 偏右，避免死循环
            if (nums[mid] <= target)
                left = mid;         // mid 可能是右边界，不跳过
            else
                right = mid - 1;
        }

        return new int[]{start, right};
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(searchRange(new int[]{5,7,7,8,8,10}, 8)));  // [3,4]
        System.out.println(Arrays.toString(searchRange(new int[]{5,7,7,8,8,10}, 6)));  // [-1,-1]
        System.out.println(Arrays.toString(searchRange(new int[]{}, 0)));              // [-1,-1]
    }
}
