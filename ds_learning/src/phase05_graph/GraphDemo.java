package phase05_graph;

import java.util.*;

/**
 * 👀 Demo — 图（Graph）
 *
 * 图 = 顶点 + 边
 *
 * 两种存储方式：
 * ① 邻接矩阵：int[][] matrix，matrix[i][j]=1 表示 i→j 有边
 *    优点：O(1) 判断是否相邻
 *    缺点：O(V²) 空间，适合稠密图
 *
 * ② 邻接表：List<Integer>[] adj，adj[i] 存 i 的所有邻居
 *    优点：O(V+E) 空间，适合稀疏图
 *    缺点：判断是否相邻 O(degree)
 *
 * 两种遍历：
 * DFS: 一条路走到黑（用栈 / 递归）
 * BFS: 一层一层扩散（用队列）
 */
public class GraphDemo {

    // ============================================
    // ① 邻接表建图
    // ============================================
    // 无向图：
    //    0 —— 1 —— 2
    //    |    |
    //    3 —— 4
    // 顶点数 V=5，边数 E=5
    static List<Integer>[] buildGraph() {
        int V = 5;
        List<Integer>[] adj = new ArrayList[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new ArrayList<>();
        }

        // 无向图：a-b 意味着两边都要加
        addUndirectedEdge(adj, 0, 1);
        addUndirectedEdge(adj, 0, 3);
        addUndirectedEdge(adj, 1, 2);
        addUndirectedEdge(adj, 1, 4);
        addUndirectedEdge(adj, 3, 4);

        return adj;
    }

    static void addUndirectedEdge(List<Integer>[] adj, int a, int b) {
        adj[a].add(b);
        adj[b].add(a);
    }

    // ============================================
    // ② DFS：一条路走到黑
    // ============================================
    static void dfs(List<Integer>[] adj, int start) {
        boolean[] visited = new boolean[adj.length];
        System.out.print("DFS 从 " + start + " 出发: ");
        dfsHelper(adj, start, visited);
        System.out.println();
    }

    // 递归版本：进来→标记→打印→逐个邻居递归
    static void dfsHelper(List<Integer>[] adj, int v, boolean[] visited) {
        visited[v] = true;                    // ① 标记来过
        System.out.print(v + " ");            // ② 做事（打印）

        for (int neighbor : adj[v]) {         // ③ 逐个邻居
            if (!visited[neighbor]) {          // ④ 没去过？
                dfsHelper(adj, neighbor, visited); // ⑤ 一头扎进去！
            }
        }
    }

    // ============================================
    // ③ BFS：一层一层扩散
    // ============================================
    static void bfs(List<Integer>[] adj, int start) {
        boolean[] visited = new boolean[adj.length];
        Queue<Integer> queue = new LinkedList<>();  // 用队列！

        visited[start] = true;
        queue.offer(start);

        System.out.print("BFS 从 " + start + " 出发: ");
        while (!queue.isEmpty()) {
            int v = queue.poll();              // ① 队头出列
            System.out.print(v + " ");         // ② 做事（打印）

            for (int neighbor : adj[v]) {      // ③ 逐个邻居
                if (!visited[neighbor]) {       // ④ 没去过？
                    visited[neighbor] = true;    // ⑤ 标记（入队时就标记！）
                    queue.offer(neighbor);       // ⑥ 排队等
                }
            }
        }
        System.out.println();
    }

    static int countComponents(List<Integer>[] adj){
        int count = 0;
        boolean[] visited = new boolean[adj.length];
        Queue<Integer> queue = new LinkedList<>();
        for (int i=0; i<adj.length; i++){
            if (!visited[i]){
                queue.offer(i);
                visited[i] = true;
                while(!queue.isEmpty()){
                    int v = queue.poll();
                    for (int neighbor : adj[v]){
                        if (!visited[neighbor]){
                            visited[neighbor] = true;
                            queue.offer(neighbor);
                        }
                    }
                }
                count++;
            }
        }
        return count;
    }

    // ============================================
    // ④ main
    // ============================================
    public static void main(String[] args) {
        List<Integer>[] adj = buildGraph();

        // 打印邻接表
        System.out.println("====== 邻接表 ======");
        for (int i = 0; i < adj.length; i++) {
            System.out.println("顶点 " + i + " 的邻居: " + adj[i]);
        }

        // DFS 遍历
        System.out.println("\n====== DFS 遍历 ======");
        dfs(adj, 0);

        // BFS 遍历
        System.out.println("\n====== BFS 遍历 ======");
        bfs(adj, 0);

        // 可视化遍历顺序的区别
        System.out.println("\n====== DFS vs BFS 路线对比 ======");
        System.out.println("图结构:   0——1——2");
        System.out.println("          |   |");
        System.out.println("          3——4");
        System.out.println();
        System.out.println("DFS(0):   0 → 1 → 2 → 4 → 3    （扎到底，再回头）");
        System.out.println("BFS(0):   0 → 1 → 3 → 2 → 4    （一圈一圈往外）");
    }
}
