/**
 * LeetCode #322 — 零钱兑换 (Medium)
 *
 * ────────── 题目 ──────────
 * 给定不同面额硬币 coins 和一个总金额 amount，计算凑出 amount 所需的最少硬币数。
 * 如果无法凑出，返回 -1。每种硬币数量无限。
 *
 * 例1：coins = [1,2,5], amount = 11
 *  输出: 3  （5 + 5 + 1）
 *
 * 例2：coins = [2], amount = 3
 *  输出: -1
 *
 * 例3：coins = [1], amount = 0
 *  输出: 0
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
import java.util.*;

public class Medium26_CoinChange {

    static int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for(int i=0;i<=amount;i++){
            for (int j : coins){
                if (i >= j){
                    dp[i] = Math.min(dp[i], dp[i - j] + 1);
                }
            }
        }
        if (dp[amount] == amount + 1) return -1;
        return dp[amount];
    }

    public static void main(String[] args) {
        System.out.println(coinChange(new int[]{1,2,5}, 11));  // 3
        System.out.println(coinChange(new int[]{2}, 3));       // -1
        System.out.println(coinChange(new int[]{1}, 0));       // 0
    }
}
