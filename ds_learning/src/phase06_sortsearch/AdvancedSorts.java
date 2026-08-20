package phase06_sortsearch;

import java.util.Arrays;

/**
 * 👀 Demo — 归并排序 & 快速排序（O(n log n) 级别）
 *
 * 基础排序都是 O(n²)，数据一大就崩了。这两种用"分治"思想达到 O(n log n)。
 *
 * ========================================================================
 * ① 归并排序（Merge Sort）— 完整递归过程
 * ========================================================================
 *
 * 数组 [5, 3, 8, 1, 6, 2, 7, 4]，共 8 个元素
 *
 * ═══ 拆分阶段（一路拆到底） ═══
 *
 *   拆 [0..7]: [5,3,8,1] 和 [6,2,7,4]
 *   │
 *   ├─ 拆 [0..3]: [5,3] 和 [8,1]
 *   │  │
 *   │  ├─ 拆 [0..1]: [5] 和 [3]
 *   │  │  └─ 到底了！[5] 单独，[3] 单独 → 都是单个元素，直接返回
 *   │  │
 *   │  └─ 拆 [2..3]: [8] 和 [1]
 *   │     └─ 到底了！[8] 单独，[1] 单独 → 都是单个元素，直接返回
 *   │
 *   └─ 拆 [4..7]: [6,2] 和 [7,4]
 *      │
 *      ├─ 拆 [4..5]: [6] 和 [2]
 *      │  └─ 到底了！
 *      │
 *      └─ 拆 [6..7]: [7] 和 [4]
 *         └─ 到底了！
 *
 * ═══ 合并阶段（两两有序合并，一路往回拼） ═══
 *
 *   合 [0..1]: [5] + [3] → i指5, j指3 → 3小 → [3,5]
 *   合 [2..3]: [8] + [1] → i指8, j指1 → 1小 → [1,8]
 *   合 [0..3]: [3,5] + [1,8] → i指3, j指1 → 1小 → 3小 → 5小 → 8小 → [1,3,5,8]
 *
 *   合 [4..5]: [6] + [2] → [2,6]
 *   合 [6..7]: [7] + [4] → [4,7]
 *   合 [4..7]: [2,6] + [4,7] → [2,4,6,7]
 *
 *   合 [0..7]: [1,3,5,8] + [2,4,6,7]
 *        i指1, j指2 → 1小 → i右移 → 2小 → j右移 → 3小 → i右移 → 4小 → j右移
 *        → 5小 → i右移 → 6小 → j右移 → 7小 → j右移 → 8小 → [1,2,3,4,5,6,7,8]
 *特点：永远 O(n log n)，稳定，但需要临时数组 O(n) 空间
 * 
 *
 *
 * ========================================================================
 * ② 快速排序（Quick Sort）— 完整递归过程
 * ========================================================================
 *
 * 同样 [5, 3, 8, 1, 6, 2, 7, 4]，选最右边的元素当 pivot
 *
 * 第1轮: 区间 [0..7], pivot=4（最右边的元素）
 *       遍历所有元素，比 4 小的扔左边：
 *       j=0: 5>4 不管 → j=1: 3<4 扔到i=0 → j=2: 8>4 不管 → j=3: 1<4 扔到i=1
 *       → j=4: 6>4 不管 → j=5: 2<4 扔到i=2 → j=6: 7>4 不管
 *       最后 pivot(4) 和 i=3 交换 → [3,1,2,  4,  6,8,7,5]
 *                                          ↑pivot归位到索引3
 *       然后递归 左边[0..2] 和 右边[4..7]
 *       │                │
 *       │                └─ 第3轮见下
 *       │
 *       └─ 第2轮: 区间 [0..2], pivot=2
 *          [3,1,2] → j=0: 3>2不管 → j=1: 1<2扔到i=0 → pivot和i=1交换
 *          → [1,  2,  3]  pivot归位到索引1
 *          左边[0..0]只有1个元素，右边[2..2]只有1个，都直接返回
 *
 *       第3轮: 区间 [4..7], pivot=5
 *          [6,8,7,5] → 都比5大，pivot不动 → [5, 8,7,6] pivot归位到索引4
 *          右边[5..7]继续
 *          │
 *          └─ 第4轮: 区间 [5..7], pivot=6
 *             [8,7,6] → j=5: 8>6不管 → j=6: 7>6不管 → pivot和i=5交换
 *             → [6, 7, 8] pivot归位到索引5
 *             右边[6..7]继续
 *             │
 *             └─ 第5轮: 区间 [6..7], pivot=8
 *                [7,8] → 7<8 → 不换，pivot归位，结束！
 *
 * 最终: [1,2,3,4,5,6,7,8]
 *
 * 特点：平均 O(n log n)，原地排序省空间，但 pivot 选得不好会退化到 O(n²)
 *
 *
 * ========================================================================
 * 对比总结
 * ========================================================================
 * | 算法   | 平均       | 最坏    | 空间     | 稳定？ |
 * | 归并   | O(n log n) |O(n log n)| O(n)   | 稳定   |
 * | 快速   | O(n log n) | O(n²)   | O(log n)| 不稳定 |
 */
public class AdvancedSorts {

    // ============================================
    // ① 归并排序
    // ============================================
    static void mergeSort(int[] arr) {
        if (arr.length <= 1) return;
        int[] tmp = new int[arr.length];      // 全局临时数组，避免反复 new
        mergeSortRange(arr, tmp, 0, arr.length - 1);
    }

    // 对 [left, right] 区间排序
    static void mergeSortRange(int[] arr, int[] tmp, int left, int right) {
        if (left >= right) return;            // 只剩一个元素，不用排

        int mid = left + (right - left) / 2;
        mergeSortRange(arr, tmp, left, mid);      // 递归排左半
        mergeSortRange(arr, tmp, mid + 1, right); // 递归排右半
        merge(arr, tmp, left, mid, right);        // 合并两个有序半区
    }

    // 合并 arr[left..mid] 和 arr[mid+1..right]（这两个子数组已经各自有序）
    static void merge(int[] arr, int[] tmp, int left, int mid, int right) {
        // 先把当前区间拷到临时数组
        for (int k = left; k <= right; k++) {
            tmp[k] = arr[k];
        }

        int i = left;       // 左半区的指针
        int j = mid + 1;    // 右半区的指针
        int k = left;       // 写回原数组的指针

        // 两个指针比较，谁小就写谁
        while (i <= mid && j <= right) {
            if (tmp[i] <= tmp[j]) {
                arr[k++] = tmp[i++];
            } else {
                arr[k++] = tmp[j++];
            }
        }

        // 左半区有剩余？直接抄过去
        while (i <= mid) {
            arr[k++] = tmp[i++];
        }
        // 右半区有剩余？不需要处理——本来就在原数组里
    }

    // ============================================
    // ② 快速排序
    // ============================================
    static void quickSort(int[] arr) {
        quickSortRange(arr, 0, arr.length - 1);
    }

    static void quickSortRange(int[] arr, int left, int right) {
        if (left >= right) return;

        int pivotIdx = partition(arr, left, right); // 分区，pivot 归位
        quickSortRange(arr, left, pivotIdx - 1);     // 递归排左边
        quickSortRange(arr, pivotIdx + 1, right);    // 递归排右边
    }

    // Lomuto 分区：选最右边的元素当 pivot
    // 返回值：pivot 的最终位置
    static int partition(int[] arr, int left, int right) {
        int pivot = arr[right];           // 选最右边当基准
        int i = left;                     // i 指向"下一个小元素该放的位置"

        for (int j = left; j < right; j++) {
            if (arr[j] < pivot) {         // 当前元素 < pivot
                swap(arr, i, j);           // 扔到左边去
                i++;
            }
            // ≥ pivot 的不动，留在右边
        }

        swap(arr, i, right);              // pivot 放到正确位置
        return i;                         // 返回 pivot 的位置
    }

    // ============================================
    // ③ 分治追踪（帮助你理解递归过程）
    // ============================================
    static void mergeSortWithTrace(int[] arr, int[] tmp, int left, int right, int depth) {
        if (left >= right) return;

        int mid = left + (right - left) / 2;
        mergeSortWithTrace(arr, tmp, left, mid, depth + 1);
        mergeSortWithTrace(arr, tmp, mid + 1, right, depth + 1);
        merge(arr, tmp, left, mid, right);

        // 打印这轮合并的结果
        System.out.print("  ".repeat(depth) + "合并 [" + left + ".." + right + "]: ");
        System.out.println(Arrays.toString(Arrays.copyOfRange(arr, left, right + 1)));
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
        int[] arr1 = {5, 3, 8, 1, 6, 2, 7, 4};
        int[] arr2 = Arrays.copyOf(arr1, arr1.length);

        System.out.println("原数组: " + Arrays.toString(arr1));

        mergeSort(arr1);
        System.out.println("归并排序: " + Arrays.toString(arr1));

        quickSort(arr2);
        System.out.println("快速排序: " + Arrays.toString(arr2));

        // 性能对比
        System.out.println("\n====== 性能对比（n=50000） ======");
        int n = 50000;
        int[] base = new int[n];
        for (int i = 0; i < n; i++) base[i] = (int)(Math.random() * n);

        int[] test = Arrays.copyOf(base, n);
        long start = System.nanoTime();
        mergeSort(test);
        System.out.printf("归并排序: %.1f ms%n", (System.nanoTime() - start) / 1e6);

        test = Arrays.copyOf(base, n);
        start = System.nanoTime();
        quickSort(test);
        System.out.printf("快速排序: %.1f ms%n", (System.nanoTime() - start) / 1e6);

        test = Arrays.copyOf(base, n);
        start = System.nanoTime();
        insertionSort(test);
        System.out.printf("插入排序: %.1f ms  (O(n^2) 作为对比)%n", (System.nanoTime() - start) / 1e6);
    }

    // 插入排序（用于性能对比）
    static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i], j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
}
