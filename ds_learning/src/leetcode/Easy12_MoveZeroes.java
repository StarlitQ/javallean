/**
 * LeetCode #283 — 移动零 (Easy)
 *
 * ────────── 题目 ──────────
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，
 * 同时保持非零元素的相对顺序。必须在原数组上操作，不能复制数组。
 *
 * 例1：输入: [0,1,0,3,12]
 *      输出: [1,3,12,0,0]
 *
 * 例2：输入: [0] → 输出: [0]
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
import java.util.Arrays;

public class Easy12_MoveZeroes {

    static void moveZeroes(int[] nums) {
        int insert = 0;
        for (int i = 0; i < nums.length; i++)
            if (nums[i] != 0) nums[insert++] = nums[i];
        for (int i = insert; i < nums.length; i++)
            nums[i] = 0;
    }

    public static void main(String[] args) {
        int[] arr1 = {0, 1, 0, 3, 12};
        moveZeroes(arr1);
        System.out.println(Arrays.toString(arr1));  // [1, 3, 12, 0, 0]

        int[] arr2 = {0};
        moveZeroes(arr2);
        System.out.println(Arrays.toString(arr2));  // [0]
    }
}
