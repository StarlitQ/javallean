/**
 * LeetCode #200 — 岛屿数量 (Medium)
 *
 * ────────── 题目 ──────────
 * 给定一个由 '1'（陆地）和 '0'（水）组成的二维网格，计算岛屿的数量。
 * 岛屿总是被水包围，每座岛屿只能由水平/竖直方向上相邻的陆地连接而成。
 *
 * 例1：grid = [
 *   ["1","1","1","1","0"],
 *   ["1","1","0","1","0"],
 *   ["1","1","0","0","0"],
 *   ["0","0","0","0","0"]
 * ]  → 输出: 1
 *
 * 例2：grid = [
 *   ["1","1","0","0","0"],
 *   ["1","1","0","0","0"],
 *   ["0","0","1","0","0"],
 *   ["0","0","0","1","1"]
 * ]  → 输出: 3
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
public class Medium05_NumberOfIslands {

    static int numIslands(char[][] grid) {
        int count = 0;
        for(int i=0; i<grid.length; i++){
            for (int j=0; j<grid[0].length; j++){
                if (grid[i][j] == '1'){
                    clear(grid, i, j);
                    count++;
                }
            }
        }
        return count;
    }

    static void clear(char[][] grid, int i, int j){
        if (grid[i][j] == '1'){
            grid[i][j] = '0';
            if (i-1>=0){
                clear(grid,i-1,j);
            }
            if (i+1<grid.length){
                clear(grid,i+1,j);
            }
            if (j-1>=0){
                clear(grid,i,j-1);
            }
            if (j+1<grid[0].length){
                clear(grid,i,j+1);
            }
        }
    }

    public static void main(String[] args) {
        char[][] grid1 = {
            {'1','1','1','1','0'},
            {'1','1','0','1','0'},
            {'1','1','0','0','0'},
            {'0','0','0','0','0'}
        };
        System.out.println(numIslands(grid1));  // 1

        char[][] grid2 = {
            {'1','1','0','0','0'},
            {'1','1','0','0','0'},
            {'0','0','1','0','0'},
            {'0','0','0','1','1'}
        };
        System.out.println(numIslands(grid2));  // 3
    }
}
