/**
 * LeetCode #55 — 跳跃游戏 (Medium)
 *
 * ────────── 题目 ──────────
 * 给定非负整数数组 nums，你最初位于第一个下标。
 * 每个元素代表你在该位置能跳跃的最大长度。
 * 判断是否能到达最后一个下标。
 *
 * 例1：nums = [2,3,1,1,4]
 *  输出: true  （从 0 跳 1 步到 1，再跳 3 步到末尾）
 *
 * 例2：nums = [3,2,1,0,4]
 *  输出: false  （无论如何都会停在 3，因为到不了 4）
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
public class Medium22_JumpGame {

    static boolean canJump(int[] nums) {
        int maxL = 0;
        for (int i = 0; i < nums.length; i++){
            if (i > maxL) return false;
            maxL = (int)Math.max(maxL, i + nums[i]);
            if (maxL >= nums.length - 1) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(canJump(new int[]{2,3,1,1,4}));  // true
        System.out.println(canJump(new int[]{3,2,1,0,4}));  // false
    }
}
