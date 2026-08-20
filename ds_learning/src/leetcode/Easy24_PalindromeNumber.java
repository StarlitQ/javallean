/**
 * LeetCode #9 — 回文数 (Easy)
 *
 * ────────── 题目 ──────────
 * 判断一个整数是否是回文数（正着读和反着读一样）。不能转成字符串。
 *
 * 例1：x = 121  → true
 * 例2：x = -121 → false  （负号不是数字，121- 不对）
 * 例3：x = 10   → false  （01 不是 10）
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
public class Easy24_PalindromeNumber {

    // ======== 方法1：数字反转 ========
    static boolean isPalindrome(int x) {
        if (x < 0) return false;
        int tmp = x, rev = 0;
        while (tmp != 0) {
            rev = rev * 10 + tmp % 10;
            tmp /= 10;
        }
        return rev == x;
    }

    // ======== 方法2：逐位从两头取 ========
    static boolean isPalindromeTwoPtr(int x) {
        if (x < 0) return false;
        if (x < 10) return true;
        int cur = x, count = 0;
        while (cur != 0) { cur /= 10; count++; }
        for (int i = 0; i < count / 2; i++) {
            int pre = x / (int) Math.pow(10, count - i - 1) % 10;
            int end = x % ((int) Math.pow(10, i + 1)) / (int) Math.pow(10, i);
            if (pre != end) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome(121));          // true
        System.out.println(isPalindrome(-121));         // false
        System.out.println(isPalindrome(10));           // false
        System.out.println(isPalindrome(0));            // true
        System.out.println(isPalindromeTwoPtr(121));    // true
        System.out.println(isPalindromeTwoPtr(10));     // false
    }
}
