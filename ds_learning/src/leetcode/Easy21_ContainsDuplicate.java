/**
 * LeetCode #217 — 存在重复元素 (Easy)
 *
 * ────────── 题目 ──────────
 * 给定一个整数数组，判断是否存在重复元素。
 * 如果存在任一值在数组中出现至少两次，返回 true；否则 false。
 *
 * 例1：nums = [1,2,3,1]    → true
 * 例2：nums = [1,2,3,4]    → false
 * 例3：nums = [1,1,1,3,3,4,3,2,4,2] → true
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
import java.util.*;

public class Easy21_ContainsDuplicate {

    static boolean containsDuplicate(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int n : nums){
            map.put(n, map.getOrDefault(n, 0) + 1);
        }
        for (int key : map.keySet()){
            if(map.get(key) > 1){
                return true;
            }
        }
        return false;
    }

    // ============================================
    // HashSet 版
    // ============================================
    // HashSet = 只有 key、没有 value 的 HashMap。
    // 不能存重复元素——add 同一个元素第二次会返回 false。
    //
    // 常用方法：
    //   set.add(x)      → 加进去。x 已存在就返回 false
    //   set.contains(x) → 查 x 在不在，O(1)
    //   set.remove(x)   → 删掉 x
    //   set.size()      → 有几个元素
    //
    // 这题用 HashSet：边遍历边查是否见过。
    // 见过 → 有重复 → 直接 true。没见过 → 加进去。
    static boolean containsDuplicateSet(int[] nums) {
        HashSet<Integer> seen = new HashSet<>();
        for (int n : nums) {
            if (seen.contains(n)) return true;
            seen.add(n);
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(containsDuplicate(new int[]{1, 2, 3, 1}));              // true
        System.out.println(containsDuplicate(new int[]{1, 2, 3, 4}));              // false
        System.out.println(containsDuplicate(new int[]{1,1,1,3,3,4,3,2,4,2}));     // true
    }
}
