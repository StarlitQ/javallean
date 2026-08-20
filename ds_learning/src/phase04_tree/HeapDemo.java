package phase04_tree;

import java.util.PriorityQueue;

/**
 * 👀 Demo — 堆（Heap）与优先级队列
 *
 * 堆的特性：
 * 1. 完全二叉树 — 每一层从左到右不留空
 * 2. 大顶堆：父 >= 子（堆顶是最大值）
 *    小顶堆：父 <= 子（堆顶是最小值）
 * 3. 不需要指针！用数组就能存
 *
 * 数组存树 Index 公式（i 从 0 开始）：
 *   父节点: (i - 1) / 2
 *   左孩子: 2*i + 1
 *   右孩子: 2*i + 2
 */
public class HeapDemo {

    public static void main(String[] args) {
        // ===== 1. Java 内置堆：PriorityQueue（默认小顶堆）=====
        System.out.println("====== Java 优先级队列 ======");
        PriorityQueue<Integer> min2 = new PriorityQueue<>();
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        minHeap.offer(5);
        minHeap.offer(2);
        minHeap.offer(8);
        minHeap.offer(1);
        System.out.println("插入 5, 2, 8, 1 后: " + minHeap);  // 内部顺序不定，但堆顶一定是1

        System.out.println("堆顶 peek: " + minHeap.peek());     // 1
        System.out.print("依次 poll: ");
        while (!minHeap.isEmpty()) {
            System.out.print(minHeap.poll() + " ");              // 1 2 5 8 → 从小到大！
        }
        System.out.println();

        // 大顶堆：传一个比较器 Comparator.reverseOrder()
        System.out.println("\n====== 大顶堆 ======");
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(java.util.Collections.reverseOrder());
        maxHeap.offer(5);
        maxHeap.offer(2);
        maxHeap.offer(8);
        System.out.print("大顶堆 poll: ");
        while (!maxHeap.isEmpty()) {
            System.out.print(maxHeap.poll() + " ");              // 8 5 2 → 从大到小！
        }
        System.out.println();

        // ===== 2. 手写小顶堆（核心原理）=====
        System.out.println("\n====== 手写小顶堆 ======");
        MinHeap myHeap = new MinHeap(10);
        myHeap.offer(5);
        myHeap.offer(2);
        myHeap.offer(8);
        myHeap.offer(1);
        System.out.println("size: " + myHeap.size());           // 4
        System.out.print("依次 poll: ");
        while (!myHeap.isEmpty()) {
            System.out.print(myHeap.poll() + " ");               // 1 2 5 8
        }
        System.out.println();
    }
}

/**
 * 手写小顶堆 — 理解堆的底层：数组 + 上浮 + 下沉
 */
class MinHeap {
    private int[] data;
    private int size;

    public MinHeap(int cap) {
        data = new int[cap];
        size = 0;
    }

    // 插入 O(log n)
    // public void offer(int val) {
    //     data[size] = val;             // 先塞到末尾
    //     siftUp(size);                 // 然后上浮到正确位置
    //     size++;
    // }

    // 取出最小值 O(log n)
    public int poll(){
        int min = data[0];
        data[0] = data[size - 1];
        size--;
        siftDown(0);
        return min;
    }

    // public int poll() {
    //     int min = data[0];            // 堆顶就是答案
    //     data[0] = data[size - 1];     // 把最后一个元素挪到堆顶
    //     size--;
    //     siftDown(0);                  // 然后下沉到正确位置
    //     return min;
    // }

    public int peek() { return data[0]; }
    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }


    public void offer(int val){
        data[size] = val;
        siftUp(size);
        size++;
    }
    // 上浮：新元素跟父节点比，比父小就交换
    private void siftUp(int i){
        while (i > 0){
            int parent = (i-1)/2;
            if (data[i]>=data[parent]) break;
            swap(i, parent);
            i = parent;
        }

    }

    // private void siftUp(int i) {
    //     while (i > 0) {
    //         int parent = (i - 1) / 2;
    //         if (data[i] >= data[parent]) break;  // 顺序对了，停
    //         swap(i, parent);
    //         i = parent;                           // 继续往上
    //     }
    // }

    // 下沉：堆顶跟最小的孩子比，比孩子大就交换
    private void siftDown(int i){
        while (true){
            int smallest = i;
            int left = 2*i+1;
            int right = 2*i+2;
            if (left<size && data[left]<data[smallest]){
                smallest = left;
            }
            if (right < size && data[right] < data[smallest])smallest = right;
            if (i == smallest)break;
            swap(i, smallest);
            i = smallest;
        }


    }

    // private void siftDown(int i) {
    //     while (true) {
    //         int smallest = i;
    //         int left = 2 * i + 1;
    //         int right = 2 * i + 2;

    //         if (left < size && data[left] < data[smallest]) smallest = left;
    //         if (right < size && data[right] < data[smallest]) smallest = right;

    //         if (smallest == i) break;  // 自己就是最小，停
    //         swap(i, smallest);
    //         i = smallest;              // 继续往下
    //     }
    // }

    private void swap(int a, int b) {
        int tmp = data[a];
        data[a] = data[b];
        data[b] = tmp;
    }

    // 可视化数组中的堆
    public void printArray() {
        System.out.print("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) System.out.print(", ");
            System.out.print(data[i]);
        }
        System.out.println("]");
    }
}
