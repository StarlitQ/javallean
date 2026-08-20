/**
 * LeetCode #49 — 字母异位词分组 (Medium)
 *
 * ────────── 题目 ──────────
 * 给定字符串数组 strs，把所有字母异位词（字母相同但顺序不同）分到一组。
 *
 * 例1：strs = ["eat","tea","tan","ate","nat","bat"]
 *  输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
 *
 * 例2：strs = [""]
 *  输出: [[""]]
 *
 * 例3：strs = ["a"]
 *  输出: [["a"]]
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
import java.util.*;

public class Medium27_GroupAnagrams {

    static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();  // value 是列表，不是单个String
        for (String s : strs) {
            char[] cur = s.toCharArray();
            Arrays.sort(cur);
            String key = new String(cur);  // char[] 转 String 要用 new String()，toString()是地址

            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());  // 第一次遇到这个key → 建个空列表
            }
            map.get(key).add(s);  // 把原字符串加进对应列表
        }

        return new ArrayList<>(map.values());  // 把 map 里所有列表直接返回
    }

    public static void main(String[] args) {
        System.out.println(groupAnagrams(new String[]{"eat","tea","tan","ate","nat","bat"}));
        // [["bat"],["nat","tan"],["ate","eat","tea"]]
        System.out.println(groupAnagrams(new String[]{""}));   // [[""]]
        System.out.println(groupAnagrams(new String[]{"a"}));  // [["a"]]
    }
}
