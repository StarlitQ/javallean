import java.util.LinkedList;

/**
 * LeetCode #5 — 最长回文子串 (Medium)
 *
 * ────────── 题目 ──────────
 * 给定一个字符串 s，找出其中最长的回文子串。
 *
 * 例1：s = "babad"  → 输出: "bab"  或 "aba"  都行
 * 例2：s = "cbbd"   → 输出: "bb"
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
public class Medium13_LongestPalindrome {

    static String longestPalindrome(String s) {
        String res = "";
        int maxL = 0;
        for (int i = 0;i<s.length();i++){
            String odd = expand(s, i, i);
            String even = expand(s, i, i+1);
            int curL = Math.max(odd.length(), even.length());
            if (curL > maxL){
                maxL = curL;
                if (odd.length() >= even.length()){
                    res = odd;
                }
                else{
                    res = even;
                }
            }
        }
        return res;
    }

    static String expand(String s, int left, int right){
        while(left>=0 && right<s.length() && s.charAt(left) == s.charAt(right)){
            left--;
            right++;
        }
        return s.substring(left + 1, right);
    }

    public static void main(String[] args) {
        System.out.println(longestPalindrome("babad"));   // bab
        System.out.println(longestPalindrome("cbbd"));    // bb
        System.out.println(longestPalindrome("a"));       // a
    }
}
