/**
 * LeetCode #3 — 无重复字符的最长子串 (Medium)
 *
 * ────────── 题目 ──────────
 * 给定一个字符串 s，找出其中不含有重复字符的最长子串的长度。
 *
 * 例1：s = "abcabcbb"  → 输出: 3  （子串 "abc"，长度为 3）
 * 例2：s = "bbbbb"     → 输出: 1  （子串 "b"）
 * 例3：s = "pwwkew"    → 输出: 3  （子串 "wke"，长度为 3）
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
import java.util.*;

public class Medium01_LongestSubstring {

    static int lengthOfLongestSubstring(String s) {
        int max = 0;
        int slow = 0;
        int len = s.length();
        List<Character> seen = new LinkedList<>();
        for (int i=0;i<len;i++){
            while (seen.contains(s.charAt(i))){
                
                seen.remove((Character)s.charAt(slow));
                slow++;
                
            }
            seen.add(s.charAt(i));
            
            if(i - slow + 1 > max) max = i - slow + 1;
        }


        return max;
    }

    // HashSet 版：一样的逻辑，但 contains 和 remove 都是 O(1)
    static int lengthOfLongestSubstringSet(String s) {
        int max = 0, left = 0;
        HashSet<Character> seen = new HashSet<>();
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            while (seen.contains(c)) {
                seen.remove(s.charAt(left));
                left++;
            }
            seen.add(c);
            if (right - left + 1 > max) max = right - left + 1;
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcabcbb"));  // 3
        System.out.println(lengthOfLongestSubstring("bbbbb"));     // 1
        System.out.println(lengthOfLongestSubstring("pwwkew"));    // 3
    }
}
