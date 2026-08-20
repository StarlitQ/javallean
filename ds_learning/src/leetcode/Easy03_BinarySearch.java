/**
 * LeetCode #704 — 二分查找 (Easy)
 *
 * ────────── 题目 ──────────
 * 给定一个 n 个元素有序的（升序）整型数组 nums 和一个目标值 target，
 * 写一个函数搜索 nums 中的 target，如果目标值存在返回下标，否则返回 -1。
 *
 * 例1：nums = [-1,0,3,5,9,12], target = 9  → 返回 4
 * 例2：nums = [-1,0,3,5,9,12], target = 2  → 返回 -1
 *
 * ────────── 提示 ──────────
 * 你 Phase 6 刚学的二分查找！while (left <= right)，砍半找。
 *
 * 进阶思考：如果把 target 换成找"第一个 ≥ target 的位置"呢？
 * （这就是 #35 搜索插入位置，做完这道可以试试）
 */
public class Easy03_BinarySearch {

    static int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;  // 防溢出
            if (target == nums[mid]) return mid;
            if (target > nums[mid]) left = mid + 1;
            else                    right = mid - 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] nums = {-1, 0, 3, 5, 9, 12};
        System.out.println(search(nums, 9));   // 4
        System.out.println(search(nums, 2));   // -1
        System.out.println(search(nums, -1));  // 0
        System.out.println(search(nums, 12));  // 5
    }
}
