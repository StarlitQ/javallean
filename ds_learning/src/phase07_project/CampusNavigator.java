import java.util.*;

/**
 * 🎓 综合实战 — 校园导航系统
 *
 * 把你 Phase 1~6 学的全部串起来：
 *   图 + Dijkstra  → 任意两栋楼的最短路径
 *   DFS / BFS      → 遍历所有建筑
 *   排序            → 路径排行、热门路线
 *   二分查找        → 按名字搜建筑
 *   队列 / 栈       → 路径存储
 *
 *
 * 校园地图（无向加权图，权重 = 距离/米）：
 *
 *           [0]校门口
 *           / |  \
 *      200 / 350  300
 *         /   |    \
 *      [1]  180  [3]  200  [7]
 *   一教 |  /  图书馆 ─── 行政楼
 *        | /    |  \         \
 *   150  |/  100|   \         \ 400
 *      [2]     |    \         \
 *   二教 |\    |     \        [8]实验楼
 *        | \   |      \
 *   400  |  280|       \
 *        |     |        \
 *      [5]──[4]──[6]    (该看的都在这了)
 *     宿舍 300 食堂 350 体育馆
 *        \         /
 *     200  \     / 320
 *           [8]实验楼
 */

public class CampusNavigator {

    // ============================================
    // ① 校园数据
    // ============================================
    static final String[] BUILDING_NAMES = {
        "校门口", "一教", "二教", "图书馆", "食堂", "宿舍", "体育馆", "行政楼", "实验楼"
    };

    static final String[] BUILDING_DESC = {
        "学校正门，进出校园的主通道",
        "第一教学楼，理论课教室",
        "第二教学楼，多媒体教室",
        "图书馆，自习和借书的地方",
        "学生食堂，有三层",
        "学生宿舍区",
        "体育馆，篮球/羽毛球/游泳",
        "行政办公楼，办各种手续",
        "实验楼，机房和实验室"
    };

    static class Edge{
        int to, weight;
        Edge(int to, int weight){
            this.to = to;
            this.weight = weight;
        }
    }

    static List<Edge>[] buildCampus(){
        int V = BUILDING_NAMES.length;
        List<Edge>[] adj = new ArrayList[V];
        for (int i=0; i<V; i++){
            adj[i] = new ArrayList<>();
        }
        addEdge(adj, 0, 1, 200);
        addEdge(adj, 0, 3, 350);
        addEdge(adj, 0, 7, 300);
        addEdge(adj, 1, 2, 150);
        addEdge(adj, 1, 3, 180);   // 一教 ↔ 图书馆
        addEdge(adj, 1, 4, 250);   // 一教 ↔ 食堂
        addEdge(adj, 2, 5, 400);   // 二教 ↔ 宿舍
        addEdge(adj, 2, 8, 200);   // 二教 ↔ 实验楼
        addEdge(adj, 3, 4, 100);   // 图书馆 ↔ 食堂
        addEdge(adj, 3, 7, 200);   // 图书馆 ↔ 行政楼
        addEdge(adj, 4, 5, 300);   // 食堂 ↔ 宿舍
        addEdge(adj, 4, 6, 350);   // 食堂 ↔ 体育馆
        addEdge(adj, 5, 6, 200);   // 宿舍 ↔ 体育馆
        addEdge(adj, 5, 8, 280);   // 宿舍 ↔ 实验楼
        addEdge(adj, 6, 8, 320);   // 体育馆 ↔ 实验楼
        addEdge(adj, 7, 8, 400);   // 行政楼 ↔ 实验楼

        return adj;
    }

    static void addEdge(List<Edge>[] adj, int a, int b, int weight){
        adj[a].add(new Edge(b, weight));
        adj[b].add(new Edge(a, weight));
    }

    // ============================================
    // ② Dijkstra 最短路径（你的 Phase 5 代码）
    // ============================================
    static class PathResult{
        int[] dist, prev;
    }

    static PathResult dijkstra(List<Edge>[] adj, int start){
        int V = adj.length;
        int[] dist = new int[V];
        int[] prev = new int[V];
        boolean[] visited = new boolean[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);
        dist[start] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1]-b[1]);
        pq.offer(new int[]{start, 0});
        while(!pq.isEmpty()){
            int v = pq.poll()[0];
            if (visited[v])continue;
            visited[v] = true;
            for (Edge e: adj[v]){
                if (visited[e.to])continue;
                int nl = dist[v] + e.weight;
                if (nl < dist[e.to]){
                    dist[e.to] = nl;
                    prev[e.to] = v;
                    pq.offer(new int[]{e.to, nl});
                }
            }
        }
        PathResult r = new PathResult();
        r.dist = dist;
        r.prev = prev;
        return r;
    }

    static List<Integer> getPath(int[] prev, int end){
        List<Integer> path = new ArrayList<>();
        for (int cur=end; cur!=-1; cur=prev[cur])path.add(cur);
        Collections.reverse(path);
        return path;
    }

    // ============================================
    // ③ BFS 遍历（你的 Phase 5 代码）
    // ============================================
    static List<Integer> bfsOrder(List<Edge>[] adj, int start){
        List<Integer> order = new ArrayList<>();
        boolean[] visited = new boolean[adj.length];
        Queue<Integer> q = new LinkedList<>();
        visited[start] = true;
        q.offer(start);

        while(!q.isEmpty()){
            int v = q.poll();
            order.add(v);
            for (Edge e : adj[v]){
                if (visited[e.to])continue;
                visited[e.to] = true;
                q.offer(e.to);
            }
        }
    }

    // ============================================
    // ④ DFS 遍历
    // ============================================

    // static List<Integer> dfsOrder2(List<Edge>[] adj, int start){
    //     List<Integer> order = new ArrayList<>();
    //     boolean[] visited = new boolean[adj.length];
    //     dfsRecur2(adj, start, visited, order);
    //     return order;


    // }

    // static void dfsRecur2(List<Edge>[] adj, int start, boolean[] visited, List<Integer> order){
    //     visited[start] = true;
    //     order.add(start);
    //     for (Edge e : adj[start]){
    //         if(!visited[e.to]){
    //             dfsRecur2(adj, e.to, visited, order);
    //         }
    //     }
    // }

    static List<Integer> dfsOrder(List<Edge>[] adj, int start) {
        List<Integer> order = new ArrayList<>();
        boolean[] visited = new boolean[adj.length];
        dfsRecur(adj, start, visited, order);
        return order;
    }

    static void dfsRecur(List<Edge>[] adj, int v, boolean[] visited, List<Integer> order) {
        visited[v] = true;
        order.add(v);
        for (Edge e : adj[v]) {
            if (!visited[e.to]) dfsRecur(adj, e.to, visited, order);
        }
    }

    // ============================================
    // ⑤ 二分查找建筑名（你的 Phase 6 代码）
    // ============================================
    // 按名字首字母排序找建筑索引
    static int findBuilding(String name) {
        for (int i = 0; i < BUILDING_NAMES.length; i++) {
            if (BUILDING_NAMES[i].equals(name)) return i;
        }
        return -1;
    }

    // ============================================
    // ⑥ 排序：所有建筑到某点的距离排行
    // ============================================
    static void printDistanceRanking(List<Edge>[] adj, int from) {
        PathResult r = dijkstra(adj, from);

        // 把 (建筑编号, 距离) 放进数组排序
        int[][] pairs = new int[adj.length][2];
        for (int i = 0; i < adj.length; i++) {
            pairs[i][0] = i;
            pairs[i][1] = r.dist[i];
        }

        // 用插入排序（基本有序场景你学过）
        Arrays.sort(pairs, (a, b) -> a[1] - b[1]);

        System.out.println("从 " + BUILDING_NAMES[from] + " 出发，由近到远：");
        for (int i = 0; i < pairs.length; i++) {
            int id = pairs[i][0];
            String distStr = pairs[i][1] == Integer.MAX_VALUE ? "不可达" : pairs[i][1] + "m";
            System.out.printf("  %2d. %s (%s)%n", i + 1, BUILDING_NAMES[id], distStr);
        }
    }

    // ============================================
    // ⑦ 打印校园地图
    // ============================================
    static void printMap() {
        System.out.println("┌─────────────────────────────────────────┐");
        System.out.println("│              校园地图                    │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.println("│                                          │");
        System.out.println("│  [0]校门口 ──200m── [1]一教 ──150m── [2]二教 ");
        System.out.println("│    │   \\           /  |   \\           /  │");
        System.out.println("│  350   300   180  / 250  400   200   280  │");
        System.out.println("│    │      \\     /    |       \\    /     │");
        System.out.println("│  [3]图书馆──200m──[7]行政楼  [5]宿舍   [8]实验楼");
        System.out.println("│    |  \\             \\      /  \\      /   │");
        System.out.println("│  100    \\           400   300  200  320    │");
        System.out.println("│    |      \\             \\ /       \\ /     │");
        System.out.println("│  [4]食堂──350m──[6]体育馆──320m──[8]实验楼  │");
        System.out.println("│                                          │");
        System.out.println("└─────────────────────────────────────────┘");
    }

    // ============================================
    // ⑧ main — 交互菜单
    // ============================================
    public static void main(String[] args) {
        List<Edge>[] campus = buildCampus();
        Scanner sc = new Scanner(System.in);

        System.out.println("🎓 校园导航系统\n");

        while (true) {
            System.out.println("\n====== 功能菜单 ======");
            System.out.println("1. 查看校园地图");
            System.out.println("2. 查看所有建筑");
            System.out.println("3. 查询两栋楼之间的最短路径");
            System.out.println("4. 从某建筑出发，距离排行");
            System.out.println("5. DFS 遍历所有建筑");
            System.out.println("6. BFS 遍历所有建筑");
            System.out.println("0. 退出");
            System.out.print("\n请选择: ");

            int choice = sc.nextInt();
            sc.nextLine(); // 吃掉换行

            switch (choice) {
                case 0:
                    System.out.println("再见！");
                    return;
                case 1:
                    printMap();
                    break;
                case 2:
                    System.out.println("\n校园建筑：");
                    for (int i = 0; i < BUILDING_NAMES.length; i++) {
                        System.out.printf("  %d. %s — %s%n", i, BUILDING_NAMES[i], BUILDING_DESC[i]);
                    }
                    break;
                case 3: {
                    System.out.println("\n可选建筑：");
                    for (int i = 0; i < BUILDING_NAMES.length; i++)
                        System.out.printf("  %d-%s", i, BUILDING_NAMES[i]);
                    System.out.print("\n输入起点编号: ");
                    int from = sc.nextInt();
                    System.out.print("输入终点编号: ");
                    int to = sc.nextInt();

                    PathResult r = dijkstra(campus, from);
                    if (r.dist[to] == Integer.MAX_VALUE) {
                        System.out.println("不可达！");
                    } else {
                        List<Integer> path = getPath(r.prev, to);
                        System.out.print("最短路径: ");
                        for (int i = 0; i < path.size(); i++) {
                            if (i > 0) System.out.print(" → ");
                            System.out.print(BUILDING_NAMES[path.get(i)]);
                        }
                        System.out.println("\n总距离: " + r.dist[to] + " 米");
                    }
                    break;
                }
                case 4:
                    System.out.print("输入起点编号: ");
                    int start = sc.nextInt();
                    printDistanceRanking(campus, start);
                    break;
                case 5:
                    System.out.print("输入起点编号: ");
                    System.out.print("DFS 遍历: ");
                    System.out.println(dfsOrder(campus, sc.nextInt()));
                    break;
                case 6:
                    System.out.print("输入起点编号: ");
                    System.out.print("BFS 遍历: ");
                    System.out.println(bfsOrder(campus, sc.nextInt()));
                    break;
                default:
                    System.out.println("无效选择，重试。");
            }
        }
    }
}
