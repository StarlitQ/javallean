package phase05_graph;

import java.util.*;

/**
 * 👀 Demo — Dijkstra 最短路径（加权图）
 *
 * 为什么需要 Dijkstra？
 * BFS 求最短路径的前提是每条边"一样长"（权重=1）。
 * 但现实世界每条路不一样长：
 *    北京 → 上海 1200km
 *    北京 → 天津 120km
 * BFS 不管权重，Dijkstra 管。
 *
 *
 * 核心思想：贪心 —— 每次挑"当前距离最小"的顶点往外扩
 *
 * 类比：你站在北京，手里有一张全国地图。你不知道去每个城市的最短距离。
 * 但你做一件事：
 *    ① 看看当前城市能直达哪些城市，更新它们的最短距离
 *    ② 从所有"未确定"的城市中，挑一个距离最小的，走过去，标记为"已确定"
 *    ③ 重复，直到所有城市都确定
 *
 * 为什么贪心是对的？因为边权非负！如果有一条更短的路，那它早就被处理了。
 * （这也是 Dijkstra 不能处理负权边的原因——后面可能出现更短的）
 *
 *
 * 数据结构：
 *   ① dist[v]   = 从起点到 v 的当前最短距离（初始 ∞，逐步缩小）
 *   ② prev[v]   = 到达 v 的上一个顶点（跟 BFS 一样）
 *   ③ visited[v] = v 是否已经"确定"（= 从优先队列里弹出来过）
 *   ④ 优先队列（小顶堆）→ 每次 O(log V) 取出距离最小的顶点
 *
 *
 * 图例（加权图）：
 *         0 --4--> 1 --1--> 2
 *         |        |        |
 *         2        3        5
 *         |        |        |
 *         v        v        v
 *         3 --1--> 4 --2--> (回到0? 不，这是有向图)
 *
 * 用有向图演示，因为 Dijkstra 最常见于有向加权图（道路不一定是双向的）。
 *
 *
 * 时间复杂度：O((V+E) log V)  — 用堆优化
 */
public class Dijkstra {

    // ============================================
    // ① 建图（有向加权图，用邻接表 + Edge 类）
    // ============================================
    // 无向加权图（↔ 双向通行，数字是路的长度）：
    //
    //        4         1
    //     0 ↔ 1 ↔ 2
    //     │   │   │
    //    2│  3│  5│
    //     ↓   ↓   ↓
    //     3 ↔ 4
    //        1
    //
    // 一共 6 条无向边（每条 = 正反两条有向边）：
    //   0↔1(4)  0↔3(2)  1↔2(1)  1↔4(3)  2↔4(5)  3↔4(1)
    //
    // 观察：改成无向后，0→2 现在有更短路：0→3→4→1→2 = 2+1+3+1 = 7
    //       不，还有 0→1→2 = 4+1 = 5，等等 Dijkstra 会找到最短的

    static class Edge {
        int to;      // 目标顶点
        int weight;  // 边的权重

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    static List<Edge>[] buildGraph() {
        int V = 5;
        List<Edge>[] adj = new ArrayList[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new ArrayList<>();
        }

        // 无向图：每条边加两次（正反各一次）
        addUndirectedEdge(adj, 0, 1, 4);
        addUndirectedEdge(adj, 0, 3, 2);
        addUndirectedEdge(adj, 1, 2, 1);
        addUndirectedEdge(adj, 1, 4, 3);
        addUndirectedEdge(adj, 2, 4, 5);
        addUndirectedEdge(adj, 3, 4, 1);

        return adj;
    }

    static void addDirectedEdge(List<Edge>[] adj, int from, int to, int w) {
        adj[from].add(new Edge(to, w));
    }

    // 无向边 = 双向各加一条有向边
    static void addUndirectedEdge(List<Edge>[] adj, int a, int b, int w) {
        adj[a].add(new Edge(b, w));
        adj[b].add(new Edge(a, w));
    }

    // ============================================
    // ② Dijkstra 核心算法
    // ============================================
    // 输入：邻接表、起点
    // 输出：dist[] 最短距离数组
    //       prev[] 前驱数组（通过参数传出，跟 BFS 一样）
    //
    // dist[v] = Integer.MAX_VALUE 表示 v 不可达
    static int[] dijkstra(List<Edge>[] adj, int start, int[] prev) {
        int V = adj.length;
        int[] dist = new int[V];
        boolean[] visited = new boolean[V];  // visited[v]=true 表示 v 已"确定"

        Arrays.fill(dist, Integer.MAX_VALUE); // ∞ = 还没算出来
        Arrays.fill(prev, -1);                // -1 = 没有前驱
        dist[start] = 0;

        // 小顶堆：按当前距离排序，距离最小的在堆顶
        // 存 int[]{顶点编号, 距离}
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.offer(new int[]{start, 0});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int v = cur[0];

            if (visited[v]) continue;    // 已经确定过，跳过
            visited[v] = true;            // 标记为"已确定"

            // 松弛操作（Relaxation）—— 核心！
            // "经过 v 到 neighbor，会不会比现在已知的路更短？"
            for (Edge e : adj[v]) {
                int neighbor = e.to;
                if (visited[neighbor]) continue;  // 已确定的不用再更新

                int newDist = dist[v] + e.weight;  // 经过 v 到 neighbor 的距离
                if (newDist < dist[neighbor]) {     // ← 这就是"松弛"：拉紧绳子
                    dist[neighbor] = newDist;
                    prev[neighbor] = v;
                    pq.offer(new int[]{neighbor, newDist});
                    // 注意：旧的距离更大的 [neighbor, oldDist] 还在堆里
                    // 但没关系，visited 判断会把它跳过去
                }
            }
        }

        return dist;
    }

    // ============================================
    // ③ 回溯路径（跟 BFS 版本完全一样！）
    // ============================================
    static List<Integer> reconstructPath(int[] prev, int start, int end) {
        List<Integer> path = new ArrayList<>();

        if (prev[end] == -1 && start != end) {
            return path;  // 不可达
        }

        for (int cur = end; cur != -1; cur = prev[cur]) {
            path.add(cur);
        }
        Collections.reverse(path);
        return path;
    }

    // ============================================
    // ④ 打印所有结果
    // ============================================
    static void printAllPaths(List<Edge>[] adj, int start) {
        int[] prev = new int[adj.length];
        int[] dist = dijkstra(adj, start, prev);

        System.out.println("====== Dijkstra 从 " + start + " 出发 ======");
        System.out.println("顶点\t最短距离\t路径");
        System.out.println("----\t--------\t----");
        for (int v = 0; v < adj.length; v++) {
            System.out.print("  " + v + "\t");
            if (dist[v] == Integer.MAX_VALUE) {
                System.out.println("   ∞\t不可达");
            } else {
                System.out.print("   " + dist[v] + "\t");
                System.out.println(reconstructPath(prev, start, v));
            }
        }
    }

    // ============================================
    // ⑤ main
    // ============================================
    public static void main(String[] args) {
        List<Edge>[] adj = buildGraph();

        // 打印图结构
        System.out.println("====== 无向加权图 ======");
        System.out.println("    4         1");
        System.out.println(" 0 ↔ 1 ↔ 2");
        System.out.println(" │   │   │");
        System.out.println("2│  3│  5│");
        System.out.println(" ↓   ↓   ↓");
        System.out.println(" 3 ↔ 4");
        System.out.println("    1");
        System.out.println();
        System.out.println("6条无向边: 0↔1(4) 0↔3(2) 1↔2(1) 1↔4(3) 2↔4(5) 3↔4(1)");

        // 手动追踪 Dijkstra(0) — 无向图版
        System.out.println("【手动追踪】Dijkstra(0) 无向图：\n");

        System.out.println("初始: dist = [0, ∞, ∞, ∞, ∞]");
        System.out.println("      堆 = [(0,0)]\n");

        System.out.println("① 弹出 v=0 (dist=0)，标记确定");
        System.out.println("   松弛: 0→1=4 → dist[1]=4");
        System.out.println("   松弛: 0→3=2 → dist[3]=2");
        System.out.println("   dist = [0, 4, ∞, 2, ∞]，堆顶=(3,2)\n");

        System.out.println("② 弹出 v=3 (dist=2)，标记确定");
        System.out.println("   松弛: 3→4=1 → dist[4]=2+1=3");
        System.out.println("   （3→0 已确定，跳过）");
        System.out.println("   dist = [0, 4, ∞, 2, 3]，堆顶=(4,3)\n");

        System.out.println("③ 弹出 v=4 (dist=3)，标记确定");
        System.out.println("   松弛: 4→2=5 → dist[2]=3+5=8");
        System.out.println("   松弛: 4→1=3 → 3+3=6 > 4, 不更新");
        System.out.println("   dist = [0, 4, 8, 2, 3]\n");

        System.out.println("④ 弹出 v=1 (dist=4)，标记确定");
        System.out.println("   松弛: 1→2=1 → dist[2]=4+1=5 < 8, 更新！← 发现更短路");
        System.out.println("   （1→0 和 1→4 都不更新）");
        System.out.println("   dist = [0, 4, 5, 2, 3]\n");

        System.out.println("⑤ 弹出 v=2 (dist=5)，标记确定，无更新");
        System.out.println("堆空，结束！最终 dist = [0, 4, 5, 2, 3]\n");

        // 重点观察
        System.out.println("【关键观察】");
        System.out.println("  ① 0→4 最短 = 3（走 0→3→4），不是 0→1→4(总长7)");
        System.out.println("  ② 0→2 最短 = 5（走 0→1→2），跟有向版一样——反向边这次没帮上忙");
        System.out.println("  ③ dist[2] 经历了 8→5 的更新过程，这就是\"松弛\"\n");

        // 打印完整结果
        printAllPaths(adj, 0);

        // ============================================
        // ✍️ 练习
        // ============================================
        System.out.println("\n====== ✍️ 练习 ======");
        System.out.println("练习 1：在纸上手动追踪 Dijkstra(1)，预测每个顶点的最短距离。");
        System.out.println("        提示：从顶点 1 出发，先看 1 能直接到哪。\n");
        System.out.println("练习 2：把图改成无向的（每条有向边变成两条），重新跑 Dijkstra(0)，");
        System.out.println("        观察 dist[2] 从 5 变短了多少。");
    }
}
