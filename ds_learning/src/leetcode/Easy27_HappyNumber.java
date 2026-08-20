/**
 * LeetCode #202 — 快乐数 (Easy)
 *
 * ────────── 题目 ──────────
 * 判断一个数 n 是不是快乐数。
 * 快乐数定义：
 *   - 从 n 开始，把每一位数字的平方求和，得到下一个数
 *   - 重复这个过程
 *   - 如果最终得到 1，就是快乐数
 *   - 如果陷入循环永远到不了 1，就不是快乐数
 *
 * 例1：n = 19 → true
 *      1²+9²=82 → 8²+2²=68 → 6²+8²=100
 *      → 1²+0²+0²=1 ✓
 *
 * 例2：n = 2 → false
 *      2²=4 → 4²=16 → 1²+6²=37 → 3²+7²=58
 *      → 5²+8²=89 → 8²+9²=145 → 1²+4²+5²=42
 *      → 4²+2²=20 → 2²+0²=4 → 进入循环：4→16→37→...→4
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
public class Easy27_HappyNumber {

    static boolean isHappy(int n) {
        int fast = n;
        int slow = n;
        while(fast != 1){
            int copys = slow;
            slow = 0;
            while(copys != 0){
                slow += (int)Math.pow(copys % 10, 2);
                copys /= 10;
            }
            int copyf = fast;
            fast = 0;
            while(copyf != 0){
                fast += (int)Math.pow(copyf % 10, 2);
                copyf /= 10;
            }
            int copyf2 = fast;
            fast = 0;
            while(copyf2 != 0){
                fast += (int)Math.pow(copyf2 % 10, 2);
                copyf2 /= 10;
            }
            if (fast == slow) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isHappy(19));   // true
        System.out.println(isHappy(2));    // false
        System.out.println(isHappy(1));    // true
    }
}
