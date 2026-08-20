package phase05_graph;

import java.util.*;

/**
 * 👀 Demo — BFS 求最短路径（无权图）
 *
 * 为什么 BFS 能求最短路径？
 * BFS 是一层一层往外扩散的：
 *    第 0 层：起点（距离 = 0）
 *    第 1 层：起点的邻居（距离 = 1）
 *    第 2 层：邻居的邻居（距离 = 2）
 *    ...
 *
 * 第一次到达一个顶点时，一定走的是最短路线！
 * 就像石头扔水里，第一圈波纹先到达岸边 → 最短路线。
 *
 *
 * 核心数据结构：两个数组
 *   ① dist[v]  = 从起点到 v 的最短距离
 *   ② prev[v]  = 到达 v 的上一个顶点（用来回溯整条路径）
 *
 * 图例（跟 GraphDemo 一样的图）：
 *       0 —— 1 —— 2
 *       |    |
 *       3 —— 4
 *
 * 求 0 到 2 的最短路径：
 *   BFS(0)  → dist[2] = 2
 *   回溯：2 ← 1 ← 0  → 路径 = [0, 1, 2]
 */
public class ShortestPath {

    // ============================================
    // ① 建图（跟 GraphDemo 一样）
    // ============================================
    static List<Integer>[] buildGraph() {
        int V = 5;
        List<Integer>[] adj = new ArrayList[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new ArrayList<>();
        }

        // 无向图：
        //   0 —— 1 —— 2
        //   |    |
        //   3 —— 4
        addUndirectedEdge(adj, 0, 1);
        addUndirectedEdge(adj, 0, 3);
        addUndirectedEdge(adj, 1, 2);
        addUndirectedEdge(adj, 1, 4);
        addUndirectedEdge(adj, 3, 4);
        addUndirectedEdge(adj, 2, 3);

        return adj;
    }

    static void addUndirectedEdge(List<Integer>[] adj, int a, int b) {
        adj[a].add(b);
        adj[b].add(a);
    }

    // ============================================
    // ② BFS 求最短路径（核心！）
    // ============================================
    // 返回值：dist[] 距离数组，prev[] 前驱数组
    // dist[v] = -1 表示 v 不可达
    static int[] bfsShortestPath(List<Integer>[] adj, int start, int[] prev) {
        int V = adj.length;
        int[] dist = new int[V];
        Arrays.fill(dist, -1);          // -1 = 还没到过（"无穷远"）
        Arrays.fill(prev, -1);          // -1 = 没有前驱

        Queue<Integer> queue = new LinkedList<>();
        dist[start] = 0;                // 起点距离 = 0（没有 prev，因为它是起点）
        queue.offer(start);

        while (!queue.isEmpty()) {
            int v = queue.poll();

            for (int neighbor : adj[v]) {
                if (dist[neighbor] == -1) {       // 第一次到！（= 最短）
                    dist[neighbor] = dist[v] + 1;  // 距离 = 上一层 + 1
                    prev[neighbor] = v;            // 记录"从哪来的"
                    queue.offer(neighbor);
                }
            }
        }

        return dist;
    }

    static int farthestNode(List<Integer>[] adj, int start){
        int farthest = start;
        int max_L = 0;
        int V = adj.length;
        int[] prev = new int[V];
        int[] dist = bfsShortestPath(adj, start, prev);
        for (int i = 0; i < V; i++){
            if (dist[i] > max_L){
                farthest = i;
                max_L = dist[i];
            }
        }
        return farthest;
    }
    // ============================================
    // ③ 回溯路径
    // ============================================
    // 从 prev[] 一路倒推，再反转
    static List<Integer> reconstructPath(int[] prev, int start, int end) {
        List<Integer> path = new ArrayList<>();

        if (prev[end] == -1 && start != end) {
            // prev[end] 还是 -1，说明 BFS 没走到 end → 不可达
            return path;  // 返回空 list
        }

        // 从终点一路往前找
        for (int cur = end; cur != -1; cur = prev[cur]) {
            path.add(cur);
        }
        Collections.reverse(path);  // 倒过来才是起点→终点
        return path;
    }

    // ============================================
    // ④ 打印所有顶点的最短距离 + 路径
    // ============================================
    static void printAllShortestPaths(List<Integer>[] adj, int start) {
        int[] prev = new int[adj.length];
        int[] dist = bfsShortestPath(adj, start, prev);

        System.out.println("从顶点 " + start + " 到各顶点的最短路径：");
        System.out.println("顶点\t距离\t路径");
        System.out.println("----\t----\t----");

        for (int v = 0; v < adj.length; v++) {
            System.out.print("  " + v + "\t  " + dist[v] + "\t");
            if (dist[v] == -1) {
                System.out.println("不可达");
            } else {
                List<Integer> path = reconstructPath(prev, start, v);
                System.out.println(path);
            }
        }
    }

    // ============================================
    // ⑤ main
    // ============================================
    public static void main(String[] args) {
        List<Integer>[] adj = buildGraph();

        System.out.println("====== BFS 求最短路径（无权图） ======\n");

        // 手动追踪 BFS 求 0→2 最短路径
        System.out.println("【手动追踪】BFS(0) 的过程：");
        System.out.println("  初始: 队=[0], dist[0]=0");
        System.out.println("  出0: 邻居[1,3] → dist[1]=1, dist[3]=1, 队=[1,3]");
        System.out.println("  出1: 邻居[2,4] → dist[2]=2, dist[4]=2, 队=[3,2,4]");
        System.out.println("  出3: 邻居[0,4] → 都已访问，跳过");
        System.out.println("  出2: 邻居[1] → 已访问，跳过");
        System.out.println("  出4: 邻居[1,3] → 都已访问，跳过");
        System.out.println("  最终: dist = [0, 1, 2, 1, 2]");
        System.out.println();

        // 打印所有最短路径
        printAllShortestPaths(adj, 0);

        // 演示路径回溯
        System.out.println("\n====== 路径回溯演示 ======");
        int start = 0, end = 2;
        int[] prev = new int[adj.length];
        bfsShortestPath(adj, start, prev);

        System.out.println("prev 数组: " + Arrays.toString(prev));
        System.out.println("   prev[2]=1 → prev[1]=0 → prev[0]=-1（起点）");
        System.out.print("   反转后: " + reconstructPath(prev, start, end));
        System.out.println();

        // 验证：从不同起点出发
        System.out.println("\n====== 从顶点 2 出发 ======");
        printAllShortestPaths(adj, 2);

        // ============================================
        // ✍️ 练习
        // ============================================
        System.out.println("\n====== ✍️ 练习 ======");

        // 练习 1：加一条边 2-3，预测 0→2 的最短路径会变吗？
        //         先脑跑，再改代码验证。
        // 提示：原来 0→2 距离是 2，加边后呢？

        // 练习 2：自己写一个 farthestNode(adj, start) 方法
        //         返回离 start 最远的顶点编号（距离相同时随便返回一个）
        //         提示：直接调用 bfsShortestPath，再遍历 dist[] 找最大值
    }
}
