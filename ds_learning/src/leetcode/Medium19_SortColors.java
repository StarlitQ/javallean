/**
 * LeetCode #75 — 颜色分类 (Medium)
 *
 * ────────── 题目 ──────────
 * 给定一个包含 0、1、2 的数组，原地对它们排序（0 全在前，1 在中间，2 在后）。
 * 要求一趟扫描，不用库排序函数。
 *
 * 例1：nums = [2,0,2,1,1,0]
 *  输出: [0,0,1,1,2,2]
 *
 * 例2：nums = [2,0,1]
 *  输出: [0,1,2]
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
import java.util.*;

public class Medium19_SortColors {

    static void sortColors(int[] nums) {
        int p0 = 0;
        int p2 = nums.length - 1;
        while (p0 < nums.length && nums[p0] == 0) p0++;
        int cur = p0;
        while (p2 >= 0 && nums[p2] == 2) p2--;
        while(cur <= p2){
            if (nums[cur] == 0){
                int tmp = nums[cur];
                nums[cur] = nums[p0];
                nums[p0] = tmp;
                p0++; cur++;
                continue;
            }
            if (nums[cur] == 2){
                int tmp = nums[cur];
                nums[cur] = nums[p2];
                nums[p2] = tmp;
                p2--;
                continue;
            }
            cur++;
        }
    }

    public static void main(String[] args) {
        int[] a1 = {2,0,2,1,1,0};
        sortColors(a1);
        System.out.println(Arrays.toString(a1));  // [0,0,1,1,2,2]

        int[] a2 = {2,0,1};
        sortColors(a2);
        System.out.println(Arrays.toString(a2));  // [0,1,2]
    }
}
