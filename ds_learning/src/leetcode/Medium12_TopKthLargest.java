/**
 * LeetCode #215 — 数组中的第K个最大元素 (Medium)
 *
 * ────────── 题目 ──────────
 * 给定整数数组 nums 和整数 k，返回数组中第 k 个最大的元素。
 *
 * 例1：nums = [3,2,1,5,6,4], k = 2  → 输出: 5  （第二大为 5）
 * 例2：nums = [3,2,3,1,2,4,5,5,6], k = 4  → 输出: 4
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
import java.util.*;

public class Medium12_TopKthLargest {

    static int findKthLargest(int[] nums, int k) {
        // int min = Integer.MIN_VALUE;
        // for (int i = 0;i<nums.length;i++){
        //     for(int j = i + 1; j < nums.length; j++){
        //         if (nums[j] < min){
        //             min = nums[j];
        //         }
        //     }
        //     if (min == nums[i]) continue;
        //     nums[i] = min;
        //     nums
        // }
        Arrays.sort(nums);


        return nums[nums.length - k];
    }

    // ============================================
    // 三种排序，不碰上面你的代码
    // ============================================

    // 1. 冒泡排序 O(n²)：相邻两两比较，大的往后冒
    static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }
    }

    static void Bubblesort(int[] arr){
        for (int i=0;i<arr.length - 1;i++){
            for (int j = i+1;j<arr.length - i - 1;j++){
                if (arr[j] > arr[j+1]){
                    int tmp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1]=tmp;
                }
            }
        }
    }

    // 2. 插入排序 O(n²)：左边有序，每次把右边一个数插进左边正确位置
    static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    static void InsertSort(int[] arr){
        for (int i = 0; i<arr.length; i++){
            int key = arr[i];
            int j=i-1;
            while(j >= 0 && arr[j] > key){
                arr[j+1] = arr[j];
                j--;
            }
            arr[j+1] = key;
        }
    }

    static void Quicksort(int[] arr, int left, int right){
        if (left >= right) return;
        int pivot = arr[(left +right)/2];
        int i = left, j =right;
        while(i <= j){
            while(arr[i] < pivot) i++;
            while(arr[j] > pivot) j--;
            if (i <= j){
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] =tmp;
                i++; j--;
            }
        }
        Quicksort(arr, left, j);
        Quicksort(arr, j, right);
    }

    // 3. 快速排序 O(n log n)：选 pivot，小的放左边，大的放右边，递归
    static void quickSort(int[] arr, int left, int right) {
        if (left >= right) return;
        int pivot = arr[left + (right - left) / 2];
        int i = left, j = right;
        while (i <= j) {
            while (arr[i] < pivot) i++;
            while (arr[j] > pivot) j--;
            if (i <= j) {
                int tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
                i++; j--;
            }
        }
        quickSort(arr, left, j);
        quickSort(arr, i, right);
    }

    // 4. 选择排序 O(n²)：每轮找最小的放到第 i 位
    static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIdx]) minIdx = j;
            }
            int tmp = arr[i]; arr[i] = arr[minIdx]; arr[minIdx] = tmp;
        }
    }

    static void MergeSort(int[] arr, int left, int right){
        if (left >= right){
            return;
        }
        int mid = (left + right) / 2;
        MergeSort(arr, left, mid);
        MergeSort(arr, mid + 1, right);
        Merge(arr, left, mid, right);
    }

    static void Merge(int[] nums, int left, int mid, int right){
        int i = left;
        int j = mid + 1;
        int k = 0;
        int[] tmp = new int[right - left + 1];
        while(i <=mid && j <=right){
            if (nums[i] <= nums[j]) tmp[k++] = nums[i++];
            else tmp[k++] = nums[j++];
        }
        while(i <= mid) tmp[k++] = nums[i++];
        while(j <= right) tmp[k++] = nums[j++];
        for (int m = left; m <= right; m++){
            nums[m] = tmp[m - left];
        }
    }

    // 5. 归并排序 O(n log n)：从中间切开，分别排好，再合并
    static void mergeSort(int[] arr, int left, int right) {
        if (left >= right) return;
        int mid = left + (right - left) / 2;
        mergeSort(arr, left, mid);           // 左边排好
        mergeSort(arr, mid + 1, right);       // 右边排好
        merge(arr, left, mid, right);         // 合并两个有序段
    }

    static void merge(int[] arr, int left, int mid, int right) {
        int[] tmp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) tmp[k++] = arr[i++];
            else                  tmp[k++] = arr[j++];
        }
        while (i <= mid) tmp[k++] = arr[i++];
        while (j <= right) tmp[k++] = arr[j++];
        for (int p = 0; p < tmp.length; p++) arr[left + p] = tmp[p];
    }

    // 6. 堆排序 O(n log n)：建最大堆，反复取堆顶放末尾
    static void heapSort(int[] arr) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) siftDown(arr, i, n);  // 建堆
        for (int i = n - 1; i > 0; i--) {
            int tmp = arr[0]; arr[0] = arr[i]; arr[i] = tmp;       // 堆顶 → 末尾
            siftDown(arr, 0, i);                                    // 剩余的重新下沉
        }
    }

    static void siftDown(int[] arr, int i, int n) {
        while (2 * i + 1 < n) {
            int child = 2 * i + 1;
            if (child + 1 < n && arr[child + 1] > arr[child]) child++;
            if (arr[i] >= arr[child]) break;
            int tmp = arr[i]; arr[i] = arr[child]; arr[child] = tmp;
            i = child;
        }
    }

    public static void main(String[] args) {
        System.out.println(findKthLargest(new int[]{3,2,1,5,6,4}, 2));           // 5
        System.out.println(findKthLargest(new int[]{3,2,3,1,2,4,5,5,6}, 4));     // 4
    }
}
