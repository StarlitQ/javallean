/**
 * LeetCode #198 — 打家劫舍 (Medium)
 *
 * ────────── 题目 ──────────
 * 你是一个小偷，沿街房屋用数组 nums 表示金额。不能偷相邻的两家。
 * 返回一夜能偷到的最高金额。
 *
 * 例1：nums = [1,2,3,1]
 *  输出: 4  （偷 1 号(1) 和 3 号(3) = 1+3=4）
 *
 * 例2：nums = [2,7,9,3,1]
 *  输出: 12  （偷 1 号(2) + 3 号(9) + 5 号(1) = 12）
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
import java.util.*;

public class Medium31_HouseRobber {

    static int rob(int[] nums) {
        return 0;
    }

    public static void main(String[] args) {
        System.out.println(rob(new int[]{1,2,3,1}));    // 4
        System.out.println(rob(new int[]{2,7,9,3,1}));  // 12
    }
}
