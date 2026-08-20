/**
 * LeetCode #42 — 接雨水 (Hard)
 *
 * ────────── 题目 ──────────
 * 给定 n 个非负整数，表示一个柱状图（每个柱子宽度为 1），
 * 计算下雨之后能接住多少雨水。
 *
 * 例1：height = [0,1,0,2,1,0,1,3,2,1,2,1]
 *  输出: 6
 *
 * 例2：height = [4,2,0,3,2,5]
 *  输出: 9
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
import java.util.*;

public class Medium33_TrappingRainWater {

    static int trap(int[] height) {
        // TODO: 在这里写你的解法
        int left = 0, right = height.length - 1, maxL = 0, maxR = 0;
        int ans = 0;
        while (left < right){
            if (height[left] <= height[right]){
                maxL = Math.max(maxL, height[left]);
                ans += maxL - height[left];
                left++;
            }
            else {
                maxR = Math.max(maxR, height[right]);
                ans += maxR - height[right];
                right--;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}));   // 6
        System.out.println(trap(new int[]{4,2,0,3,2,5}));               // 9
    }
}
