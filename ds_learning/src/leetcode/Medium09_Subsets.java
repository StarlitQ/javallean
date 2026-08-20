/**
 * LeetCode #78 — 子集 (Medium)
 *
 * ────────── 题目 ──────────
 * 给定一个不含重复元素的整数数组 nums，返回所有可能的子集（幂集）。
 *
 * 例1：nums = [1,2,3]
 *  输出: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
 *
 * 例2：nums = [0]  → 输出: [[],[0]]
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
import java.util.*;

public class Medium09_Subsets {

    static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        backtrack(nums, 0, path, res);
        return res;
    }

    static void backtrack(int[] nums, int i, List<Integer> path, List<List<Integer>> res){
        if (i == nums.length){
            res.add(new ArrayList<>(path));
            return;
        }
        backtrack(nums, i + 1, path, res);

        path.add(nums[i]);
        backtrack(nums, i + 1, path, res);
        path.remove(path.size() - 1);
    }

    public static void main(String[] args) {
        System.out.println(subsets(new int[]{1, 2, 3}));  // 含 [] 共 8 个
        System.out.println(subsets(new int[]{0}));        // [[],[0]]
    }
}
