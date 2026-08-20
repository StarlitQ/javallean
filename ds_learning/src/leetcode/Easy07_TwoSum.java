/**
 * LeetCode #1 — 两数之和 (Easy)
 *
 * ────────── 题目 ──────────
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出
 * 和为目标值的那两个整数，并返回它们的数组下标。
 * 每种输入只会对应一个答案，不能重复使用同一个元素。
 *
 * 例1：nums = [2,7,11,15], target = 9  → 返回 [0,1]（2+7=9）
 * 例2：nums = [3,2,4],     target = 6  → 返回 [1,2]（2+4=6）
 * 例3：nums = [3,3],       target = 6  → 返回 [0,1]
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
import java.util.*;

public class Easy07_TwoSum {

    // 暴力 O(n²)
    static int[] twoSum(int[] nums, int target) {
        for (int fir = 0; fir < nums.length; fir++) {
            for (int sec = fir + 1; sec < nums.length; sec++){
                if (nums[fir] + nums[sec] == target) return new int[]{fir, sec};
            }
        }
        return new int[]{1, -1};
    }

    // ============================================
    // 优化版 O(n)：用 HashMap
    // ============================================
    // HashMap = 键值对，key → value，O(1) 时间查 key 是否存在
    //
    // 思路：遍历数组，对于每个数 nums[i]：
    //   缺多少 = target - nums[i]
    //   缺的这个数见过了吗？
    //     → 见过 → 返回 [见过的索引, i]
    //     → 没见 → 把 {nums[i]: i} 存起来，继续走
    //
    // 例：nums=[2,7,11,15], target=9
    //   i=0: 缺 7，map 空 → 存 {2:0}
    //   i=1: 缺 2，map 里有 2！→ 返回 [0,1]
    static int[] twoSumFast(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();  // 值 → 索引
        for (int i = 0; i < nums.length; i++) {
            int need = target - nums[i];
            if (map.containsKey(need)) {                  // 缺的见过？
                return new int[]{map.get(need), i};       // 找到了
            }
            map.put(nums[i], i);                          // 存 {值: 索引}
        }
        return new int[]{-1, -1};
    }

    public static void main(String[] args) {
        int[] r1 = twoSum(new int[]{2,7,11,15}, 9);
        System.out.println(Arrays.toString(r1));  // [0,1]

        int[] r2 = twoSum(new int[]{3,2,4}, 6);
        System.out.println(Arrays.toString(r2));  // [1,2]

        int[] r3 = twoSum(new int[]{3,3}, 6);
        System.out.println(Arrays.toString(r3));  // [0,1]
    }
}
