/**
 * LeetCode #62 — 不同路径 (Medium)
 *
 * ────────── 题目 ──────────
 * 机器人位于 m×n 网格的左上角，只能向右或向下移动。
 * 问到达右下角有多少条不同路径。
 *
 * 例1：m = 3, n = 7  → 输出: 28
 * 例2：m = 3, n = 2  → 输出: 3
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
import java.util.*;

public class Medium24_UniquePaths {

    static int uniquePaths(int m, int n) {
        int[][] matrix = new int[m][n];
        for (int i=0;i<m;i++) matrix[i][0] = 1;
        for (int j=0;j<n;j++) matrix[0][j] = 1;
        for (int i=1;i<m;i++){
            for (int j=1;j<n;j++){
                matrix[i][j] = matrix[i-1][j] + matrix[i][j-1];
            }
        }
        return matrix[m - 1][n - 1];
    }

    public static void main(String[] args) {
        System.out.println(uniquePaths(3, 7));  // 28
        System.out.println(uniquePaths(3, 2));  // 3
    }
}
