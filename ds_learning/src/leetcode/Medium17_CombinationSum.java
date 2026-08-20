/**
 * LeetCode #39 — 组合总和 (Medium)
 *
 * ────────── 题目 ──────────
 * 给一个无重复元素的候选数组 candidates 和一个目标值 target，
 * 找出所有和为 target 的组合。同一个数字可以无限制重复选取。
 *
 * 例1：candidates = [2,3,6,7], target = 7
 *  输出: [[2,2,3],[7]]
 *
 * 例2：candidates = [2,3,5], target = 8
 *  输出: [[2,2,2,2],[2,3,3],[3,5]]
 *
 * 例3：candidates = [2], target = 1
 *  输出: []
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
import java.util.*;

public class Medium17_CombinationSum {

    static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        addsum(candidates, target, 0, path, res);
        return res;
    }

    static void addsum(int[] candidates, int target, int start, List<Integer> path, List<List<Integer>> res){
        int sum = 0;
        for (int i = 0; i < path.size(); i++){
            sum+=path.get(i);
        }
        if (sum == target) {
            List<Integer> tmp = new ArrayList<>(path);
            res.add(tmp);
            return;
        }
        if (sum > target) return;
        for (int i = start; i < candidates.length; i++){
            path.add(candidates[i]);
            addsum(candidates, target, i, path, res);
            path.remove(path.size() - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println(combinationSum(new int[]{2,3,6,7}, 7));  // [[2,2,3],[7]]
        System.out.println(combinationSum(new int[]{2,3,5}, 8));    // [[2,2,2,2],[2,3,3],[3,5]]
        System.out.println(combinationSum(new int[]{2}, 1));        // []
    }
}
