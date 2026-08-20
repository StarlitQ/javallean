/**
 * LeetCode #33 — 搜索旋转排序数组 (Medium)
 *
 * ────────── 题目 ──────────
 * 有一个升序排列的数组在某个位置进行了旋转，搜索 target。
 * 要求 O(log n)。
 *
 * 例1：nums = [4,5,6,7,0,1,2], target = 0  → 输出: 4
 * 例2：nums = [4,5,6,7,0,1,2], target = 3  → 输出: -1
 * 例3：nums = [1], target = 0             → 输出: -1
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
public class Medium15_SearchRotated {

    static int search(int[] nums, int target) {
        if (nums.length == 1){
            if (nums[0] == target) return 0;
            else return -1;
        }
        int left = 0;
        int right = nums.length - 1;
        int len = nums.length;
        int start = find(nums, left, right);
        int nleft = left + start;
        int nright = right +start;
        while(nleft <= nright){
            int nmid = (nleft + nright) / 2;
            int mid = nmid % len;
            if (nums[mid] == target) return mid;
            if (nums[mid] > target) nright = nmid - 1;
            else nleft =nmid + 1;
        }
        return -1;
    }

    static int find(int[] nums, int left, int right){
        if (right - left == 1) return right;
        
        int mid = (left+right)/2;
        if (nums[mid] < nums[right]){
            return find(nums, left, mid);
        }
        else {
            return find(nums, mid, right);
        }
    }

    // ============================================
    // 解法2：不找旋转点，每次判断哪半边有序
    // ============================================
    static int search2(int[] nums, int target) {
        int left = 0, right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) return mid;

            // 左半边有序（比如 [4,5,6,7,0,1,2]，mid=6，左边 4→6 是升序）
            if (nums[left] <= nums[mid]) {
                // target 正好在左半边范围内 → 去左边找
                if (nums[left] <= target && target < nums[mid])
                    right = mid - 1;
                else
                    left = mid + 1;   // 否则去右边找
            }
            // 右半边有序（比如 nums[left]=7, nums[mid]=0，说明旋转点在左边，右边 0→2 是升序）
            else {
                // target 正好在右半边范围内 → 去右边找
                if (nums[mid] < target && target <= nums[right])
                    left = mid + 1;
                else
                    right = mid - 1;  // 否则去左边找
            }
        }
        return -1;
    }

    // ============================================
    // 你的写法：自己动手填
    // 思路：取 mid，判断哪半边有序（nums[left] <= nums[mid] 则左半有序，否则右半有序）
    //       再看 target 在不在有序那半的范围内，在就去那半，不在就去另一半
    // ============================================
    static int search3(int[] nums, int target) {
        // TODO: 你写
        int left = 0;
        int right = nums.length -1;
        while(left<=right){
            int mid = (left+right)/2;
            if (nums[mid]==target) return mid;
            if (nums[left]<=nums[mid]){
                if (nums[left] <= target && target < nums[mid]){
                    right = mid - 1;
                }
                else left = mid + 1;
            }
            else{
                if (nums[mid] < target && target <= nums[right]){
                    left = mid + 1;
                }
                else{
                    right = mid - 1;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(search(new int[]{4,5,6,7,0,1,2}, 0));   // 4
        System.out.println(search(new int[]{4,5,6,7,0,1,2}, 3));   // -1
        System.out.println(search(new int[]{1}, 0));                // -1
    }
}
