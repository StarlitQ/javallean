/**
 * LeetCode #11 — 盛最多水的容器 (Medium)
 *
 * ────────── 题目 ──────────
 * 给定 n 个非负整数 height[i]，每个代表一根竖线。找出其中两条线，
 * 使得它们与 x 轴共同构成的容器可以容纳最多的水（面积 = 宽 × 矮的那根高）。
 *
 * 例1：height = [1,8,6,2,5,4,8,3,7]  → 输出: 49
 *      索引 1(高度8) 和索引 8(高度7)：
 *      宽=7, 高=min(8,7)=7 → 面积=49
 *
 * 例2：height = [1,1]  → 输出: 1
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
public class Medium02_ContainerWater {

    static int maxArea(int[] height) {
        int maxS = 0;
        for(int i=0;i<height.length - 1;i++){
            int h1 = height[i];
            for (int j=i + 1;j<height.length;j++){
                int h2 = height[j];
                int width = j - i;
                int curS = area(h1, h2, width);
                maxS = (curS > maxS)?curS:maxS;
            }
        }
        return maxS;
    }

    static int area(int h1, int h2, int width){
        int height = (h1<h2)?h1:h2;
        int s = height*width;
        return s;
    }

    // 双指针 O(n)
    static int maxAreaFast(int[] height) {
        // 你的代码
        int left = 0;
        int right = height.length - 1;
        int maxS = 0;
        while(left < right){
            int h = Math.min(height[left], height[right]);
            int w = right - left;
            maxS = Math.max(maxS, h*w);
            if (height[left] > height[right]) right--;
            else left++;
        }
        return maxS;
    }

    public static void main(String[] args) {
        System.out.println(maxArea(new int[]{1,8,6,2,5,4,8,3,7}));  // 49
        System.out.println(maxArea(new int[]{1,1}));                  // 1
    }
}
