/**
 * LeetCode #153 — 寻找旋转排序数组中的最小值 (Medium)
 *
 * ────────── 题目 ──────────
 * 给定一个无重复元素的升序数组，在某位置旋转了一次，找出最小值。要求 O(log n)。
 *
 * 例1：nums = [3,4,5,1,2]
 *  输出: 1
 *
 * 例2：nums = [4,5,6,7,0,1,2]
 *  输出: 0
 *
 * 例3：nums = [11,13,15,17]
 *  输出: 11
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
public class Medium30_FindMinRotated {

    static int findMin(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right){
            int mid = (left + right) / 2;
            if (nums[mid] > nums[right]){
                left = mid + 1;
            }
            else {
                right = mid;
            }
        }
        return nums[left];
    }

    public static void main(String[] args) {
        System.out.println(findMin(new int[]{3,4,5,1,2}));          // 1
        System.out.println(findMin(new int[]{4,5,6,7,0,1,2}));      // 0
        System.out.println(findMin(new int[]{11,13,15,17}));        // 11
    }
}
