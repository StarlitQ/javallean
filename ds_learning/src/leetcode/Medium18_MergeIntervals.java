/**
 * LeetCode #56 — 合并区间 (Medium)
 *
 * ────────── 题目 ──────────
 * 给一个区间数组 intervals，其中 intervals[i] = [start_i, end_i]，
 * 合并所有重叠的区间，返回不重叠的区间数组。
 *
 * 例1：intervals = [[1,3],[2,6],[8,10],[15,18]]
 *  输出: [[1,6],[8,10],[15,18]]
 *  （[1,3] 和 [2,6] 重叠 → 合并为 [1,6]）
 *
 * 例2：intervals = [[1,4],[4,5]]
 *  输出: [[1,5]]
 *  （[1,4] 和 [4,5] 视为重叠）
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
import java.util.*;

public class Medium18_MergeIntervals {

    static int[][] merge(int[][] intervals) {
        int len = intervals.length;
        List<int[]> res = new ArrayList<>();
        
        int[] visited = new int[len];
        int start = 0;
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        while (start < len){
            int left = intervals[start][0];
            int right = intervals[start][1];
            
            if (visited[start] == 1){
                start++;
                continue;
            }
            for (int i = start + 1; i < len; i++){
                if (intervals[i][0] <= right && intervals[i][0] > left){
                    visited[i] = 1;
                    if(intervals[i][1] > right){
                        right = intervals[i][1];
                    }
                    continue;
                }
                if (intervals[i][1] < right && intervals[i][1] >= left){
                    visited[i] = 1;
                    if (intervals[i][0] < left){
                        left = intervals[i][0];
                    }
                    continue;
                }
            }
            start++;
            res.add(new int[]{left, right});
            
        }
        int[][] ret = new int[res.size()][2];
        for (int i=0;i<res.size();i++){
            ret[i] = res.get(i);
        }

        return ret;
    }

    // ============================================
    // 你的写法：排序 + 一遍扫过去合并
    //
    // 排完序后，重叠的区间一定相邻，不需要 visited 也不用双层循环：
    //   1. 用一个变量 curLeft, curRight 记录当前正在合并的区间
    //   2. 遍历每个区间：
    //      - 如果 intervals[i][0] <= curRight（重叠）→ curRight = max(curRight, intervals[i][1])
    //      - 如果不重叠 → 结算上一个(curLeft, curRight)，开辟新区间
    //   3. 遍历完别忘了把最后一个区间也加进结果
    //
    // 示例: [[1,3],[2,6],[8,10]]
    //   从 [1,3] 开始 → [2,6] 重叠(2<=3) → cur=[1,6]
    //   → [8,10] 不重叠(8>6) → 结算[1,6]，新cur=[8,10]
    //   → 结束，结算[8,10]
    //   结果: [[1,6],[8,10]]
    // ============================================
    static int[][] merge2(int[][] intervals) {
        // TODO: 你写
        int len = intervals.length;
        List<int[]> res = new ArrayList<>();
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        int cur = 0;
        int curL = intervals[cur][0];
        int curR = intervals[cur][1];
        while(cur < len){
            if (cur + 1 < len && intervals[cur + 1][0] <= curR){
                if (curR < intervals[cur + 1][1]){
                    curR = intervals[cur + 1][1];
                }
                cur++;
                continue;
            }
            res.add(new int[]{curL, curR});
            cur++;
            if (cur < len){
                curL = intervals[cur][0];
                curR = intervals[cur][1];
            }
        }
        int[][] ret = new int[res.size()][2];
        for (int i=0;i<res.size();i++){
            ret[i] = res.get(i);
        }
        return ret;
    }

    public static void main(String[] args) {
        int[][] r1 = merge(new int[][]{{1,3},{2,6},{8,10},{15,18}});
        System.out.println(Arrays.deepToString(r1));  // [[1,6],[8,10],[15,18]]

        int[][] r2 = merge(new int[][]{{1,4},{4,5}});
        System.out.println(Arrays.deepToString(r2));  // [[1,5]]
    }
}
