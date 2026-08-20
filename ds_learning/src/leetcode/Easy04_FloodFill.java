/**
 * LeetCode #733 — 图像渲染 / Flood Fill (Easy)
 *
 * ────────── 题目 ──────────
 * 有一幅以 m×n 的二维整数数组表示的图画 image，其中 image[i][j] 表示该像素值。
 * 你也会得到三个整数 sr, sc 和 color。
 * 从 image[sr][sc] 开始，将所有与该起始像素颜色相同、且上下左右相邻的像素
 * 全部替换为 color。然后返回修改后的图像。
 *
 * 例1：image = [[1,1,1],[1,1,0],[1,0,1]], sr=1, sc=1, color=2
 *      起始位置 image[1][1] = 1
 *      把所有相连的 1 变成 2
 *      输出: [[2,2,2],[2,2,0],[2,0,1]]
 *
 * 例2：image = [[0,0,0],[0,0,0]], sr=0, sc=0, color=0
 *      起始已经是 0（和目标颜色相同！）→ 直接返回原图
 *
 * ────────── 提示 ──────────
 * DFS 或 BFS！四方向扩散，跟图的遍历完全一样，只是邻居变成了上下左右。
 * 注意边界：行 0..m-1，列 0..n-1。
 * 注意死循环：如果起始颜色 == 新颜色，直接返回（什么都别做）。
 */
import java.util.*;
public class Easy04_FloodFill {

    static int[][] floodFill(int[][] image, int sr, int sc, int color) {
        if (image[sr][sc] == color) return image;
        int rows = image.length;
        int cols = image[0].length;
        Queue<int[]> wait = new LinkedList<>();
        wait.offer(new int[]{sr, sc});
        while (!wait.isEmpty()) {
            int[] cur = wait.poll();
            int r = cur[0], c = cur[1];
            if (image[r][c] == color) continue;
            if (r - 1 >= 0 && image[r - 1][c] == image[r][c])
                wait.offer(new int[]{r - 1, c});
            if (r + 1 < rows && image[r + 1][c] == image[r][c])
                wait.offer(new int[]{r + 1, c});
            if (c - 1 >= 0 && image[r][c - 1] == image[r][c])
                wait.offer(new int[]{r, c - 1});
            if (c + 1 < cols && image[r][c + 1] == image[r][c])
                wait.offer(new int[]{r, c + 1});
            image[r][c] = color;
        }
        return image;
    }

    // ============================================
    // DFS 版（跟 BFS 版对比着看）
    // ============================================
    static int[][] floodFillDFS(int[][] image, int sr, int sc, int color) {
        if (image[sr][sc] == color) return image;
        dfs(image, sr, sc, image[sr][sc], color);
        return image;
    }

    static void dfs(int[][] image, int r, int c, int oldColor, int newColor) {
        // 越界 or 不是旧颜色 → 回头
        if (r < 0 || r >= image.length || c < 0 || c >= image[0].length) return;
        if (image[r][c] != oldColor) return;

        image[r][c] = newColor;   // 染色 = 标记来过

        dfs(image, r - 1, c, oldColor, newColor);  // 上
        dfs(image, r + 1, c, oldColor, newColor);  // 下
        dfs(image, r, c - 1, oldColor, newColor);  // 左
        dfs(image, r, c + 1, oldColor, newColor);  // 右
    }

    // 工具：打印二维数组
    static void printImage(int[][] img) {
        for (int[] row : img) {
            for (int v : row) System.out.print(v + " ");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // 测试1：题目给的例子
        int[][] img1 = {{1,1,1},{1,1,0},{1,0,1}};
        System.out.print("测试1 原图:  "); printImage(img1);
        floodFill(img1, 1, 1, 2);
        System.out.print("      渲染后: "); printImage(img1);
        System.out.println();

        // 测试2：颜色相同 → 不动
        int[][] img2 = {{0,0,0},{0,0,0}};
        System.out.print("测试2 原图:  "); printImage(img2);
        floodFill(img2, 0, 0, 0);
        System.out.print("      渲染后: "); printImage(img2);
        System.out.println();

        // 测试3：单像素
        int[][] img3 = {{5}};
        System.out.print("测试3 原图:  "); printImage(img3);
        floodFill(img3, 0, 0, 9);
        System.out.print("      渲染后: "); printImage(img3);
        System.out.println();

        // 测试4：蛇形连通
        int[][] img4 = {
            {0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0},
            {0, 1, 1, 1, 0},
            {0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0}
        };
        System.out.println("测试4 原图:");
        printImage(img4);
        floodFill(img4, 2, 2, 9);
        System.out.println("      渲染后:");
        printImage(img4);
    }
}
