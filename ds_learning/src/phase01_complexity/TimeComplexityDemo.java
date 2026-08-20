package phase01_complexity;

/**
 * 👀 Demo — 时间复杂度直观感受
 * 我们写三个求和函数，数据量从小到大运行，感受"算法好坏"的差别
 */
public class TimeComplexityDemo {

    // 方法1：循环累加 — 数据量 n 越大，循环次数越多
    // 执行时间 ∝ n → 这叫 O(n)
    static long sumByLoop(int n) {
        long sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;           // 这行执行了 n 次
        }
        return sum;
    }

    // 方法2：公式 — 不管 n 多大，就做一次运算
    // 执行时间恒定 → 这叫 O(1)
    static long sumByFormula(int n) {
        return (long) n * (n + 1) / 2;  // 只执行 1 次！
    }

    // 方法3：双层循环 — 数据量 n 越大，循环 n² 次
    // 执行时间 ∝ n² → 这叫 O(n²)
    static long sumPairs(int n) {
        long count = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                count++;        // 这行执行了 n × n = n² 次
            }
        }
        return count;
    }

    // ============ 计时工具 ============
    static void measure(String label, Runnable task) {
        long start = System.nanoTime();
        task.run();
        long end = System.nanoTime();
        double ms = (end - start) / 1_000_000.0;
        System.out.printf("%s 花了: %.3f 毫秒\n", label, ms);
    }

    public static void main(String[] args) {
        int[] sizes = {1000, 10000, 100000};  // 从小到大的数据量

        System.out.println("====== O(1) vs O(n) vs O(n²) ======\n");

        for (int n : sizes) {
            System.out.println("--- 数据量 n = " + n + " ---");

            int currentN = n;  // effectively final for lambda
            measure("  O(1) 公式法       ", () -> sumByFormula(currentN));
            measure("  O(n) 循环法       ", () -> sumByLoop(currentN));

            // n² 在 10 万时太慢了（要执行 100 亿次），小数据量才跑
            if (n <= 10000) {
                measure("  O(n²)双层循环   ", () -> sumPairs(currentN));
            } else {
                System.out.println("  O(n²)双层循环   :  n=100000 太慢，跳过... （要跑 100 亿次！）");
            }
            System.out.println();
        }
    }
}
