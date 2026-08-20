package phase06_sortsearch;

import java.util.Arrays;

/**
 * 👀 Demo — 三种基础排序（O(n²) 级别）
 *
 * 这三种排序都很直观，但都慢。学它们是为了理解"为什么需要更好的排序"。
 *
 *
 * ① 冒泡排序（Bubble Sort）
 *    像气泡一样，大的往后冒。
 *    每轮比较相邻两个，把最大的"冒"到最后。
 *
 *    过程（数组 [5, 3, 8, 1]）：
 *    第1轮: 5>3换 → 3,5,8,1 → 5<8不动 → 8>1换 → 3,5,1,8  ← 8 归位
 *    第2轮: 3<5不动 → 5>1换 → 3,1,5,8                      ← 5 归位
 *    第3轮: 3>1换 → 1,3,5,8                                ← 3 归位
 *    结束！
 *
 *    优化：如果某一轮没有发生交换，说明已经有序，可以提前结束。
 *
 *
 * ② 选择排序（Selection Sort）
 *    每轮从未排序的部分选出最小的，放到已排序的末尾。
 *
 *    过程（数组 [5, 3, 8, 1]）：
 *    第1轮: 遍历找最小 → 1，和 5 换 → [1, 3, 8, 5]  ← 1 归位
 *    第2轮: 从[3,8,5]找最小 → 3，不用换 → [1, 3, 8, 5] ← 3 归位
 *    第3轮: 从[8,5]找最小 → 5，和 8 换 → [1, 3, 5, 8] ← 5,8 归位
 *
 *    比冒泡好一点：每轮只交换一次，但还是要 O(n²) 次比较。
 *
 *
 * ③ 插入排序（Insertion Sort）
 *    像打牌时整理手牌——从第二张开始，每张牌往前插到正确位置。
 *
 *    过程（数组 [5, 3, 8, 1]）：
 *    初始: [5 | 3, 8, 1]    5 已就位
 *    拿 3: [3, 5 | 8, 1]    3 插入 5 前面
 *    拿 8: [3, 5, 8 | 1]    8 插入最后
 *    拿 1: [1, 3, 5, 8]     1 一路往前移到最前面
 *
 *    优势：数据基本有序时接近 O(n)！实际开发中 Arrays.sort() 对短数组就用它。
 *
 *
 * ─────────── 三种对比 ───────────
 * | 算法   | 最好   | 最坏   | 稳定？ |
 * | 冒泡   | O(n)   | O(n²)  | 稳定   |
 * | 选择   | O(n²)  | O(n²)  | 不稳定 |
 * | 插入   | O(n)   | O(n²)  | 稳定   |
 *
 * "稳定" = 相等的元素保持原来的顺序不变
 */
public class BasicSorts {

    // ============================================
    // ① 冒泡排序
    // ============================================
    static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {          // 需要 n-1 轮
            boolean swapped = false;                // 优化：本轮有没有交换？
            for (int j = 0; j < n - 1 - i; j++) {   // 每轮比较范围缩小（后面的已归位）
                if (arr[j] > arr[j + 1]) {          // 相邻比较
                    swap(arr, j, j + 1);            // 大的往后冒
                    swapped = true;
                }
            }
            if (!swapped) break;                    // 没交换 = 已有序，提前收工
        }
    }

    // ============================================
    // ② 选择排序
    // ============================================
    static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {           // 每轮确定 arr[i]
            int minIdx = i;                         // 假设 i 位置就是最小的
            for (int j = i + 1; j < n; j++) {       // 在剩余部分找更小的
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            if (minIdx != i) {                      // 找到更小的才交换
                swap(arr, i, minIdx);
            }
        }
    }

    // ============================================
    // ③ 插入排序
    // ============================================
    static void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {               // 从第二张牌开始
            int key = arr[i];                       // 拿起这张牌
            int j = i - 1;
            // 前面比 key 大的都往后挪
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];                // 往后挪一位
                j--;
            }
            arr[j + 1] = key;                       // key 插入正确的坑位
        }
    }

    // ============================================
    // 工具方法
    // ============================================
    static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // ============================================
    // main
    // ============================================
    public static void main(String[] args) {
        System.out.println("====== 三种基础排序 ======\n");

        // 冒泡排序
        int[] arr1 = {5, 3, 8, 1, 6};
        System.out.println("冒泡前: " + Arrays.toString(arr1));
        bubbleSort(arr1);
        System.out.println("冒泡后: " + Arrays.toString(arr1));

        // 选择排序
        int[] arr2 = {5, 3, 8, 1, 6};
        System.out.println("\n选择前: " + Arrays.toString(arr2));
        selectionSort(arr2);
        System.out.println("选择后: " + Arrays.toString(arr2));

        // 插入排序 — 演示基本有序的情况
        int[] arr3 = {1, 2, 4, 3, 5};               // 只有一个 3 位置不对
        System.out.println("\n插入前: " + Arrays.toString(arr3) + " ← 基本有序");
        insertionSort(arr3);
        System.out.println("插入后: " + Arrays.toString(arr3));
        System.out.println("（插入排序在基本有序时接近 O(n)，非常快）");

        // ============================================
        // 对比：哪种更快？
        // ============================================
        System.out.println("\n====== 性能对比（n=10000 随机数组） ======");
        int n = 10000;
        int[] base = new int[n];
        for (int i = 0; i < n; i++) base[i] = (int)(Math.random() * n);

        int[] test = Arrays.copyOf(base, n);
        long start = System.nanoTime();
        bubbleSort(test);
        System.out.printf("冒泡: %.1f ms%n", (System.nanoTime() - start) / 1e6);

        test = Arrays.copyOf(base, n);
        start = System.nanoTime();
        selectionSort(test);
        System.out.printf("选择: %.1f ms%n", (System.nanoTime() - start) / 1e6);

        test = Arrays.copyOf(base, n);
        start = System.nanoTime();
        insertionSort(test);
        System.out.printf("插入: %.1f ms%n", (System.nanoTime() - start) / 1e6);

        System.out.println("\n（三者都是 O(n²)，n=10000 ≈ 1亿次比较，所以都慢）");
    }
}
