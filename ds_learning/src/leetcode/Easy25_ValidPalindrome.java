/**
 * LeetCode #125 — 验证回文串 (Easy)
 *
 * ────────── 题目 ──────────
 * 给定一个字符串，验证它是否是回文串。只考虑字母和数字字符，忽略大小写。
 * 空字符串定义为有效回文。
 *
 * 例1：s = "A man, a plan, a canal: Panama"  → true
 *      （只取字母数字: amanaplanacanalpanama，正反一样）
 * 例2：s = "race a car"     → false
 * 例3：s = " "              → true  （空串算回文）
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
public class Easy25_ValidPalindrome {

    static boolean isPalindrome(String s) {
        int len = s.length();
        int left = 0;
        int right = len - 1;
        while(left < right){
            char front = s.charAt(left);
            char back = s.charAt(right);
            if (!Character.isLetterOrDigit(front)){
                left++;
                continue;
            }
            if (!Character.isLetterOrDigit(back)){
                right--;
                continue;
            }
            if (Character.toLowerCase(front) != Character.toLowerCase(back)) return false;
            left++; right--;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome("A man, a plan, a canal: Panama"));  // true
        System.out.println(isPalindrome("race a car"));                       // false
        System.out.println(isPalindrome(" "));                                // true
    }
}
