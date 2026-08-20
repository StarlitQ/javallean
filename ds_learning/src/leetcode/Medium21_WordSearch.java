/**
 * LeetCode #79 — 单词搜索 (Medium)
 *
 * ────────── 题目 ──────────
 * 给定 m×n 的字符网格 board 和一个单词 word，
 * 判断 word 是否在网格中存在（相邻单元格水平或垂直连接，同一格只能用一次）。
 *
 * 例1：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
 *  输出: true
 *
 * 例2：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SEE"
 *  输出: true
 *
 * 例3：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCB"
 *  输出: false
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
import java.util.*;

public class Medium21_WordSearch {

    static boolean exist(char[][] board, String word) {
        int depth = 0;
        for (int i = 0;i < board.length; i++){
            for (int j=0;j<board[0].length; j++){
                if (board[i][j] != word.charAt(0)) continue;
                board[i][j] = '$'; 
                boolean res = find(board, word, depth + 1, i, j);
                board[i][j] = word.charAt(0);
                if (res == true) return true;
            }

        }
        

        return false;
    }

    static boolean find(char[][] board, String word, int depth, int row, int col){
        if (depth == word.length()) return true;
        if (row - 1 >=0 && board[row - 1][col] == word.charAt(depth)){
            board[row - 1][col] = '$';
            boolean res = find(board, word, depth + 1, row - 1, col);
            board[row - 1][col] = word.charAt(depth);
            if (res == true) return true;
        }
        if (row + 1 < board.length && board[row + 1][col] == word.charAt(depth)){
            board[row + 1][col] = '$';
            boolean res = find(board, word, depth + 1, row + 1, col);
            board[row + 1][col] = word.charAt(depth);
            if (res == true) return true;
        }
        if (col - 1 >= 0 && board[row][col - 1] == word.charAt(depth)){
            board[row][col - 1] = '$';
            boolean res = find(board, word, depth + 1, row, col - 1);
            board[row][col - 1] = word.charAt(depth);
            if (res == true) return true;
        }
        if (col + 1 < board[0].length && board[row][col + 1] == word.charAt(depth)){
            board[row][col + 1] = '$';
            boolean res = find(board, word, depth + 1, row, col + 1);
            board[row][col + 1] = word.charAt(depth);
            if (res == true) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        char[][] b = {{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}};
        System.out.println(exist(b, "ABCCED"));  // true
        System.out.println(exist(b, "SEE"));     // true
        System.out.println(exist(b, "ABCB"));    // false
    }
}
