package phase06_sortsearch;

import java.util.Arrays;

/**
 * 👀 Demo — 堆排序（Heap Sort）
 *
 * 你已经学过堆了（Phase 4），堆排序就是堆的应用：
 *    ① 把数组建成一个大顶堆（heapify）
 *    ② 反复：弹出堆顶（最大值），扔到数组末尾
 *    ③ 剩下的重新调整成堆，重复直到堆空
 *
 * 不需要额外数组！每次"弹出"其实是把堆顶和末尾交换，堆的边界左移。
 *
 *
 * 过程图解（数组 [5, 3, 8, 1, 6]）：
 *
 * ═══ 第一步：建堆（heapify） ═══
 *
 * 把随机数组调整成大顶堆（父 ≥ 子）。从最后一个非叶子节点往前处理：
 *
 * 初始数组: [5, 3, 8, 1, 6]
 * 看成完全二叉树:
 *         5
 *       /   \
 *      3     8
 *     / \
 *    1   6
 *
 * 最后一个非叶子: 3（索引=1, 即n/2-1=1）
 *
 * 处理 3(索引1): 孩子是 1(索引3) 和 6(索引4)
 *   6 最大 → 3和6换 → 树变成:
 *         5
 *       /   \
 *      6     8
 *     / \
 *    1   3
 *
 * 处理 5(索引0): 孩子是 6(索引1) 和 8(索引2)
 *   8 最大 → 5和8换 → 树变成:
 *         8
 *       /   \
 *      6     5
 *     / \
 *    1   3
 *
 * 建堆完成！数组: [8, 6, 5, 1, 3]  堆顶(8)是整个数组的最大值 ✓
 *
 *
 * ═══ 第二步：逐次弹出 ═══
 *
 * 第1次弹出: 交换 8 和 3（末尾）→ [3, 6, 5, 1, | 8]
 *   堆范围缩小到 [0..3]，3 下沉：和 6(左孩子)换
 *   → [6, 3, 5, 1, | 8]  3 再下沉：和 1? 不，孩子中 3>1 不换了
 *   堆恢复: [6, 3, 5, 1, | 8]  堆顶=6 ✓
 *
 * 第2次弹出: 交换 6 和 1 → [1, 3, 5, | 6, 8]
 *   堆范围 [0..2]，1 下沉：5>3 → 和 5 换
 *   → [5, 3, 1, | 6, 8]  堆顶=5 ✓
 *
 * 第3次弹出: 交换 5 和 1 → [1, 3, | 5, 6, 8]
 *   堆范围 [0..1]，1 下沉：3>1 → 和 3 换
 *   → [3, 1, | 5, 6, 8]  堆顶=3 ✓
 *
 * 第4次弹出: 交换 3 和 1 → [1, | 3, 5, 6, 8]
 *   只剩一个，结束！
 *
 * 最终: [1, 3, 5, 6, 8]  升序排列 ✓
 *
 *
 * 关键公式（你学过的，复习一下）：
 *   父 = (i-1)/2    左孩子 = 2i+1    右孩子 = 2i+2
 *
 * 时间复杂度：O(n log n)
 *   建堆 O(n) + 弹 n 次 × 每次下沉 O(log n) = O(n log n)
 * 空间复杂度：O(1) — 原地排序，不需要额外数组
 *
 *
 * ─────────── 四种排序总结 ───────────
 * | 算法   | 平均       | 空间     | 稳定？ |
 * | 冒泡   | O(n²)      | O(1)    | 稳定   |
 * | 选择   | O(n²)      | O(1)    | 不稳定 |
 * | 插入   | O(n²)      | O(1)    | 稳定   |
 * | 归并   | O(n log n) | O(n)    | 稳定   |
 * | 快速   | O(n log n) | O(log n)| 不稳定 |
 * | 堆排序 | O(n log n) | O(1)    | 不稳定 |
 */
public class HeapSort {

    // ============================================
    // ① 堆排序主方法
    // ============================================
    static void heapSort(int[] arr) {
        int n = arr.length;

        // Step 1: 建堆 — 从最后一个非叶子节点往前处理
        for (int i = n / 2 - 1; i >= 0; i--) {
            siftDown(arr, n, i);
        }

        // Step 2: 逐次弹出堆顶（最大值），扔到末尾
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);       // 堆顶(最大) 和 堆尾 交换 → 最大值归位
            siftDown(arr, i, 0);   // 堆范围缩小到 i，从新堆顶开始下沉
        }
    }

    // ============================================
    // ② 下沉操作（你 Phase 4 学过了）
    // ============================================
    // heapSize: 当前堆的大小（随着弹出逐步缩小）
    // parent: 要下沉的节点索引
    static void siftDown(int[] arr, int heapSize, int parent) {
        while (true) {
            int largest = parent;                  // 假设父最大
            int left  = 2 * parent + 1;            // 左孩子
            int right = 2 * parent + 2;            // 右孩子

            // 找三个中最大的
            if (left  < heapSize && arr[left]  > arr[largest]) largest = left;
            if (right < heapSize && arr[right] > arr[largest]) largest = right;

            if (largest == parent) break;          // 父已经最大，不用沉了

            swap(arr, parent, largest);            // 和最大的孩子交换
            parent = largest;                      // 继续往下沉
        }
    }

    // ============================================
    // ③ 工具方法
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
        int[] arr = {5, 3, 8, 1, 6};
        System.out.println("原数组: " + Arrays.toString(arr));

        heapSort(arr);
        System.out.println("堆排序: " + Arrays.toString(arr));

        // 性能对比
        System.out.println("\n====== 三种 O(n log n) 对比（n=50000） ======");
        int n = 50000;
        int[] base = new int[n];
        for (int i = 0; i < n; i++) base[i] = (int)(Math.random() * n);

        int[] test = Arrays.copyOf(base, n);
        long start = System.nanoTime();
        heapSort(test);
        System.out.printf("堆排序: %.1f ms%n", (System.nanoTime() - start) / 1e6);

        // 借用 AdvancedSorts 的方法（同一个包下可以直接调）
        test = Arrays.copyOf(base, n);
        start = System.nanoTime();
        AdvancedSorts.mergeSort(test);
        System.out.printf("归并排序: %.1f ms%n", (System.nanoTime() - start) / 1e6);

        test = Arrays.copyOf(base, n);
        start = System.nanoTime();
        AdvancedSorts.quickSort(test);
        System.out.printf("快速排序: %.1f ms%n", (System.nanoTime() - start) / 1e6);
    }
}
