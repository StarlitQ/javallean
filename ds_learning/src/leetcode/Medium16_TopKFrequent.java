/**
 * LeetCode #347 — 前K个高频元素 (Medium)
 *
 * ────────── 题目 ──────────
 * 给定一个整数数组，返回出现频率前 k 高的元素。
 *
 * 例1：nums = [1,1,1,2,2,3], k = 2  → 输出: [1,2]
 * 例2：nums = [1], k = 1             → 输出: [1]
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
import java.util.*;

public class Medium16_TopKFrequent {

    // ============================================
    // 你的写法：哈希表 + 按次数排序取前 k
    // ============================================
    static int[] topKFrequent(int[] nums, int k) {
        // 1. 统计频率
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }

        // 2. Entry 扔进 List，按 value（出现次数）降序排
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort((a, b) -> b.getValue() - a.getValue());  // 降序：b - a

        // 3. 取前 k 个的 key
        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = list.get(i).getKey();
        }
        return res;
    }

    // ============================================
    // 解法2：桶排序 — O(n)
    //
    // 核心想法：
    //   统计完频率后，不用排序。
    //
    //   用一个"桶数组"，下标 = 出现次数，值 = 出现这么多次的元素列表。
    //   比如元素 1 出现了 3 次，就把 1 扔进 bucket[3] 这个桶里。
    //
    //   一个元素最多出现 n 次（整个数组全是同一个数），所以桶数组大小是 n+1。
    //
    //   最后从高频往低频遍历桶（bucket[n] → bucket[1]），
    //   把桶里的元素往结果里塞，塞满 k 个就停。
    //
    //   为什么是 O(n)？
    //     - 哈希统计: O(n)
    //     - 入桶: O(n)（每个不同的元素进一次桶）
    //     - 从桶里取 k 个: O(n)（最坏遍历所有桶）
    //     - 没有排序！
    //
    // 示例：nums = [1,1,1,2,2,3], k=2
    //   频率: {1→3, 2→2, 3→1}
    //   桶: bucket[1]=[3], bucket[2]=[2], bucket[3]=[1]
    //   从后往前取: bucket[3]→取1, bucket[2]→取2 → 满k=2 → 返回[1,2]
    // ============================================
    static int[] topKFrequent2(int[] nums, int k) {
        // 1. 统计频率
        Map<Integer, Integer> freq = new HashMap<>();
        for (int n : nums) {
            freq.put(n, freq.getOrDefault(n, 0) + 1);
        }

        // 2. 桶数组：bucket[i] 存出现 i 次的所有元素
        //    最多出现 nums.length 次，所以桶大小是 nums.length + 1
        List<Integer>[] buckets = new List[nums.length + 1];
        for (Map.Entry<Integer, Integer> e : freq.entrySet()) {
            int count = e.getValue();
            int num = e.getKey();
            if (buckets[count] == null) {
                buckets[count] = new ArrayList<>();
            }
            buckets[count].add(num);
        }

        // 3. 从高频往低频遍历桶，取出 k 个
        int[] res = new int[k];
        int idx = 0;
        for (int i = buckets.length - 1; i >= 0 && idx < k; i--) {
            if (buckets[i] != null) {
                for (int num : buckets[i]) {
                    res[idx++] = num;
                    if (idx == k) break;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(topKFrequent(new int[]{1,1,1,2,2,3}, 2)));  // [1,2]
        System.out.println(Arrays.toString(topKFrequent(new int[]{1}, 1)));            // [1]
    }
}
