/**
 * LeetCode #48 — 旋转图像 (Medium)
 *
 * ────────── 题目 ──────────
 * 给定 n×n 的二维矩阵，原地顺时针旋转 90 度。
 *
 * 例1：matrix = [[1,2,3],[4,5,6],[7,8,9]]
 *  输出: [[7,4,1],[8,5,2],[9,6,3]]
 *
 * 例2：matrix = [[5,1,9,11],[2,4,8,10],[13,3,6,7],[15,14,12,16]]
 *  输出: [[15,13,2,5],[14,3,4,1],[12,6,8,9],[16,7,10,11]]
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
import java.util.*;

public class Medium23_RotateImage {

    static void rotate(int[][] matrix) {
        int len = matrix.length;
        for (int i=0;i<len;i++){
            for (int j=i+1;j<len;j++){
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }
        }
        for (int i=0;i<len;i++){
            for(int j=0;j<len/2;j++){
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[i][len - 1 - j];
                matrix[i][len - 1 - j] = tmp;
            }
        }
    }

    public static void main(String[] args) {
        int[][] m1 = {{1,2,3},{4,5,6},{7,8,9}};
        rotate(m1);
        System.out.println(Arrays.deepToString(m1));  // [[7,4,1],[8,5,2],[9,6,3]]

        int[][] m2 = {{5,1,9,11},{2,4,8,10},{13,3,6,7},{15,14,12,16}};
        rotate(m2);
        System.out.println(Arrays.deepToString(m2));
        // [[15,13,2,5],[14,3,4,1],[12,6,8,9],[16,7,10,11]]
    }
}
