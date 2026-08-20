/**
 * LeetCode #242 — 有效的字母异位词 (Easy)
 *
 * ────────── 题目 ──────────
 * 给定两个字符串 s 和 t，判断 t 是否是 s 的字母异位词。
 * 字母异位词：两个字符串包含的字母和个数完全相同，只是排列不同。
 *
 * 例1：s = "anagram", t = "nagaram"  → true
 * 例2：s = "rat",     t = "car"      → false
 *
 * 进阶：如果输入包含 Unicode 字符怎么办？
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
import java.util.*;
public class Easy26_ValidAnagram {

    static boolean isAnagram(String s, String t) {
        int len = s.length();
        if (len != t.length())return false;
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < len; i++){
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
            map.put(t.charAt(i), map.getOrDefault(t.charAt(i), 0) - 1);
        }
        for (char c : map.keySet()){
            if (map.get(c) != 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isAnagram("anagram", "nagaram"));  // true
        System.out.println(isAnagram("rat", "car"));           // false
        System.out.println(isAnagram("a", "a"));               // true
    }
}
