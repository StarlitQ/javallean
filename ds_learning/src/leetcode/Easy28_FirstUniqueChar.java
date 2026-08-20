/**
 * LeetCode #387 — 字符串中的第一个唯一字符 (Easy)
 *
 * ────────── 题目 ──────────
 * 给定一个字符串 s，找到它的第一个不重复的字符，并返回它的索引。
 * 如果不存在不重复的字符，返回 -1。
 *
 * 例1：s = "leetcode"    → 返回 0  （'l' 是第一个只出现一次的字符）
 * 例2：s = "loveleetcode" → 返回 2  （'v' 是第一个只出现一次的字符）
 * 例3：s = "aabb"        → 返回 -1 （没有只出现一次的字符）
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
import java.util.*;
public class Easy28_FirstUniqueChar {

    static int firstUniqChar(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i=0; i<s.length();i++){
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }
        for (int i=0;i<s.length();i++){
            if (map.get(s.charAt(i)) == 1) return i;
        }


        return -1;
    }

    public static void main(String[] args) {
        System.out.println(firstUniqChar("leetcode"));      // 0
        System.out.println(firstUniqChar("loveleetcode"));   // 2
        System.out.println(firstUniqChar("aabb"));           // -1
    }
}
