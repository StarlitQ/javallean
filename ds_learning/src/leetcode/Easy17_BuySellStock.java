/**
 * LeetCode #121 — 买卖股票的最佳时机 (Easy)
 *
 * ────────── 题目 ──────────
 * 给定一个数组 prices，prices[i] 是第 i 天的价格。
 * 你只能选择某一天买入，并在未来的某一天卖出。求最大利润。
 * 如果不能获利，返回 0。
 *
 * 例1：prices = [7,1,5,3,6,4]  → 输出: 5
 *      买入 1（第2天），卖出 6（第5天），6-1=5
 *
 * 例2：prices = [7,6,4,3,1]    → 输出: 0
 *      一直在跌，不买不卖，利润 0
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
public class Easy17_BuySellStock {

    static int maxProfit(int[] prices) {
        // int buy = prices[0];
        // int max = 0;
        // int small = prices[0];
        // int maxP = prices[max] - prices[buy];
        // for (int cur=1; cur<prices.length; cur++){
        //     if (prices[cur] > max){
        //         max = prices[cur];
        //     }
        //     if (prices[cur] < buy){
        //         small = prices[cur];
        //     }
        //     if (max - small > maxP){
        //         maxP = max - small;
        //         buy = small;
        //         max = 0;
        //     }
            

        // }
        int small = 0;
        int maxP = 0;
        for (int cur=1; cur<prices.length; cur++){
            if (prices[cur] < prices[small]){
                small = cur;
            }
            if (prices[cur] - prices[small] > maxP){
                maxP = prices[cur] - prices[small];
            }
        }
        return maxP;
    }

    public static void main(String[] args) {
        System.out.println(maxProfit(new int[]{7, 1, 5, 3, 6, 4}));  // 5
        System.out.println(maxProfit(new int[]{7, 6, 4, 3, 1}));     // 0
        System.out.println(maxProfit(new int[]{2, 4, 1}));           // 2
    }
}
