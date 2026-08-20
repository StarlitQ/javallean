package phase06_sortsearch;

import java.util.Arrays;

/**
 * 👀 Demo — 二分查找（Binary Search）
 *
 * 场景：你在图书馆找一本书，书架上有 10000 本书按书名排序。
 * 你不可能从第一本翻到最后一本——你会翻到中间，看要找的书在左边还是右边，
 * 然后砍掉一半，再翻中间……这就是二分查找。
 *
 *
 * 前提：数组必须有序！（乱序的数组二分查找没意义）
 *
 * 核心：每次砍一半
 *   左右指针 [left, right]，取中间 mid
 *   arr[mid] == target → 找到了
 *   arr[mid] <  target → 目标在右边，left = mid + 1
 *   arr[mid] >  target → 目标在左边，right = mid - 1
 *
 * 时间复杂度：O(log n)  —— 每次砍半，10000 个数只需约 14 次比较
 * 空间复杂度：O(1)       —— 只有几个变量
 *
 *
 * 对比你已经学过的：
 *   顺序查找：O(n)   —— 一个个翻，慢
 *   二分查找：O(log n) —— 砍半翻，快
 *   BST查找：O(log n) —— 树结构上的二分查找！本质一样
 */
public class BinarySearch {

    // ============================================
    // ① 标准二分查找（迭代版）
    // ============================================
    // 返回 target 的索引，找不到返回 -1
    static int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {               // 注意：<= 不是 <
            int mid = left + (right - left) / 2; // 防溢出写法，等价于 (left+right)/2

            if (arr[mid] == target) {
                return mid;                    // 找到了！
            } else if (arr[mid] < target) {
                left = mid + 1;                // 目标在右边，砍掉左半
            } else {
                right = mid - 1;               // 目标在左边，砍掉右半
            }
        }
        return -1;                             // 没找到
    }

    // ============================================
    // ② 二分查找（递归版）
    // ============================================
    // 跟迭代版完全一样的逻辑，只是用递归写
    static int binarySearchRecursive(int[] arr, int target) {
        return bsRecur(arr, target, 0, arr.length - 1);
    }

    static int bsRecur(int[] arr, int target, int left, int right) {
        if (left > right) return -1;           // 终止条件：区间空了

        int mid = left + (right - left) / 2;

        if (arr[mid] == target) return mid;
        if (arr[mid] < target)
            return bsRecur(arr, target, mid + 1, right);
        else
            return bsRecur(arr, target, left, mid - 1);
    }

    // ============================================
    // ③ 找第一个 / 最后一个出现的位置
    // ============================================
    // 数组可能有重复元素，比如 [1, 2, 2, 2, 3]，找 2 的左右边界

    // 找 target 第一次出现的位置
    static int findFirst(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                result = mid;         // 先记下来
                right = mid - 1;      // 但继续往左找，说不定还有更早的
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }

    // 找 target 最后一次出现的位置
    static int findLast(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                result = mid;         // 先记下来
                left = mid + 1;       // 但继续往右找，说不定还有更晚的
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return result;
    }

    // ============================================
    // ④ main
    // ============================================
    public static void main(String[] args) {
        int[] arr = {2, 5, 8, 12, 16, 23, 38, 45, 56, 67, 78, 89};

        System.out.println("====== 二分查找 ======");
        System.out.println("有序数组: " + Arrays.toString(arr));
        System.out.println("数组长度: " + arr.length);
        System.out.println("最多需要比较: log₂(" + arr.length + ") ≈ " +
                           (int)(Math.log(arr.length) / Math.log(2)) + " 次\n");

        // 测试查找
        System.out.println("查找 23: 索引 = " + binarySearch(arr, 23));    // 在数组中
        System.out.println("查找 50: 索引 = " + binarySearch(arr, 50));    // 不在数组中
        System.out.println("查找 2:  索引 = " + binarySearch(arr, 2));     // 边界（最左）
        System.out.println("查找 89: 索引 = " + binarySearch(arr, 89));    // 边界（最右）

        // 递归版
        System.out.println("\n递归版查找 23: 索引 = " + binarySearchRecursive(arr, 23));

        // 重复元素的左右边界
        System.out.println("\n====== 重复元素找边界 ======");
        int[] arr2 = {1, 2, 2, 2, 3, 4, 5};
        System.out.println("数组: " + Arrays.toString(arr2));
        System.out.println("2 第一次出现: " + findFirst(arr2, 2));  // 索引 1
        System.out.println("2 最后一次:   " + findLast(arr2, 2));   // 索引 3
        System.out.println("2 出现次数:   " + (findLast(arr2, 2) - findFirst(arr2, 2) + 1)); // 3次

        // 手动追踪
        System.out.println("\n====== 手动追踪：在 arr 中找 23 ======");
        System.out.println("[2, 5, 8, 12, 16, 23, 38, 45, 56, 67, 78, 89]");
        System.out.println(" 0  1  2  3   4   5   6   7   8   9  10  11");
        System.out.println();
        System.out.println("① left=0, right=11 → mid=5 → arr[5]=23 → 找到了！✅");
        System.out.println("   log₂(12) ≈ 4 次比较以内必找到，这次运气好一步到位。\n");

        System.out.println("手动追踪：找 50（不存在）");
        System.out.println("① left=0, right=11, mid=5, arr[5]=23 < 50 → left=6");
        System.out.println("② left=6, right=11, mid=8, arr[8]=56 > 50 → right=7");
        System.out.println("③ left=6, right=7,  mid=6, arr[6]=38 < 50 → left=7");
        System.out.println("④ left=7, right=7,  mid=7, arr[7]=45 < 50 → left=8");
        System.out.println("⑤ left=8 > right=7 → 退出，返回 -1");
    }
}
