/**
 * LeetCode #46 — 全排列 (Medium)
 *
 * ────────── 题目 ──────────
 * 给定一个不含重复数字的数组 nums，返回其所有可能的全排列。
 *
 * 例1：nums = [1,2,3]
 *  输出: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 *
 * 例2：nums = [0,1]  → 输出: [[0,1],[1,0]]
 * 例3：nums = [1]    → 输出: [[1]]
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
import java.util.*;

public class Medium08_Permutations {

    static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        backtrack(nums, 0, res);
        return res;
        // List<List<Integer>> res = new ArrayList<>();
        // backtrack(nums, 0, res);
        // return res;
    }

    static void backtrack(int[] nums, int start, List<List<Integer>> res){
        if (start == nums.length - 1){
            List<Integer> list = new ArrayList<>();
            for (int i=0;i<nums.length;i++){
                list.add(nums[i]);
            }
            res.add(list);
        }
        for (int i=start; i<nums.length; i++){
            swap(nums, start, i);
            backtrack(nums, start+1, res);
            swap(nums, start, i);
        }
    }

    // 回溯：固定 start 位置，然后递归处理右边
    // static void backtrack(int[] nums, int start, List<List<Integer>> res) {
    //     // base case: start 走到末尾 → 整个排列完成
    //     if (start == nums.length - 1) {
    //         List<Integer> list = new ArrayList<>();
    //         for (int n : nums) list.add(n);
    //         res.add(list);
    //         return;
    //     }

    //     for (int i = start; i < nums.length; i++) {
    //         swap(nums, start, i);                // 把 i 号元素换到 start 位固定
    //         backtrack(nums, start + 1, res);     // 递归固定下一位
    //         swap(nums, start, i);                // 换回来（撤销），试下一个
    //     }
    // }

    static void swap(int[] nums, int a, int b) {
        int tmp = nums[a];
        nums[a] = nums[b];
        nums[b] = tmp;
    }

    public static void main(String[] args) {
        System.out.println(permute(new int[]{1, 2, 3}));  // 6 种排列
        System.out.println(permute(new int[]{0, 1}));     // [[0,1],[1,0]]
        System.out.println(permute(new int[]{1}));        // [[1]]
    }
}
