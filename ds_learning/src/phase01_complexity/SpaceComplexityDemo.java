package phase01_complexity;

/**
 * 👀 Demo — 空间复杂度直观感受
 *
 * 我们用"计算斐波那契数列第 n 项"来展示空间换时间。
 *
 * 方法A：递归 — O(n) 栈空间，O(2^n) 时间（指数爆炸）
 * 方法B：数组缓存 — O(n) 空间，O(n) 时间
 * 方法C：滚动变量 — O(1) 空间，O(n) 时间  ← 最优！
 */
public class SpaceComplexityDemo {

    // ===== 方法A：暴力递归 =====
    // 时间 O(2^n)，空间 O(n)（递归调用栈深度）
    static long fibRecursion(int n) {
        if (n <= 1) return n;
        return fibRecursion(n - 1) + fibRecursion(n - 2);
    }

    // ===== 方法B：记忆化数组 =====
    // 时间 O(n)，空间 O(n)（多开了一个数组）
    static long fibArray(int n) {
        if (n <= 1) return n;
        long[] dp = new long[n + 1];   // 额外开了 n+1 个格子
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    // ===== 方法C：滚动变量 =====
    // 时间 O(n)，空间 O(1)（只记前两个数，用完就扔）
    static long fibRolling(int n) {
        if (n <= 1) return n;
        long prev2 = 0;  // 前两个
        long prev1 = 1;  // 前一个
        for (int i = 2; i <= n; i++) {
            long current = prev1 + prev2;
            prev2 = prev1;  // 滚动向前
            prev1 = current;
        }
        return prev1;
    }

    // ============ 计时工具 ============
    static void measure(String label, int n, java.util.function.IntFunction<Long> fn) {
        long start = System.nanoTime();
        long result = fn.apply(n);
        long end = System.nanoTime();
        double ms = (end - start) / 1_000_000.0;
        System.out.printf("%s  fib(%d)=%d  耗时: %.3f ms\n", label, n, result, ms);
    }

    public static void main(String[] args) {
        System.out.println("====== 时间 vs 空间：斐波那契三写法 ======\n");

        // n=10：三种都很快
        System.out.println("--- n=10（小数据量，三种都行）---");
        measure("  方法C 滚动变量 O(1)空间", 10, SpaceComplexityDemo::fibRolling);
        measure("  方法B 数组缓存 O(n)空间", 10, SpaceComplexityDemo::fibArray);
        measure("  方法A 暴力递归 O(n)空间", 10, SpaceComplexityDemo::fibRecursion);
        System.out.println();

        // n=45：递归开始翻车
        System.out.println("--- n=45（递归已经炸了）---");
        measure("  方法C 滚动变量 O(1)空间", 45, SpaceComplexityDemo::fibRolling);
        measure("  方法B 数组缓存 O(n)空间", 45, SpaceComplexityDemo::fibArray);
        System.out.print("  方法A 暴力递归:  计算中...（要算 22 亿次，等不起）\n");
        // fibRecursion(45) 约需要 3-5 秒，不跑了
    }
}
