package phase04_tree;

/**
 * 👀 Demo — 二叉搜索树 (Binary Search Tree)
 *
 * 规则：左边 < 根 < 右边（不允许重复）
 *
 *         8
 *       /   \
 *      3     10
 *     / \      \
 *    1   6     14
 *       / \    /
 *      4   7  13
 *
 * 中序遍历 → 1 3 4 6 7 8 10 13 14（排好序的！）
 * 查找、插入、删除：平均 O(log n)，退化成链时 O(n)
 */
public class BSTDemo {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int v) { val = v; }
    }

    // ===== 查找 O(log n) =====
    // 跟猜数字一样——小了去右边，大了去左边
    static TreeNode search(TreeNode root, int target) {
        if (root == null || root.val == target) return root;
        if (target < root.val) return search(root.left, target);   // 小 → 左边
        else return search(root.right, target);                     // 大 → 右边
    }

    // ===== 插入 O(log n) =====
    // 先找位置，找到空位就插进去

    static TreeNode insert(TreeNode root, int val){
        if (root == null) return new TreeNode(val);
        if (val < root.val) root.left = insert(root.left, val);
        else if (val > root.val) root.right = insert(root.right, val);
        return root;

    }

    // static TreeNode insert(TreeNode root, int val) {
    //     if (root == null) return new TreeNode(val);   // 空位！插在这

    //     if (val < root.val)
    //         root.left = insert(root.left, val);       // 小了去左边
    //     else if (val > root.val)
    //         root.right = insert(root.right, val);     // 大了去右边
    //     // 相等不处理（不允许重复）

    //     return root;
    // }

    // ===== 中序遍历 =====

    static void inOrder(TreeNode root){
        if (root == null) return;
        inOrder(root.left);
        System.out.println(root.val + " ");
        inOrder(root.right);
    }

    // static void inOrder(TreeNode node) {
    //     if (node == null) return;
    //     inOrder(node.left);
    //     System.out.print(node.val + " ");
    //     inOrder(node.right);
    // }


    public static void main(String[] args) {
        // 建树：8 3 1 6 4 7 10 14 13
        TreeNode root = null;
        int[] nums = {8, 3, 10, 1, 6, 14, 4, 7, 13};
        for (int n : nums) {
            root = insert(root, n);
        }

        System.out.print("中序遍历（自动排序！）: ");
        inOrder(root);
        System.out.println();   // 1 3 4 6 7 8 10 13 14

        // 查找
        System.out.println("\n====== 查找 ======");
        System.out.println("找 7: " + (search(root, 7) != null ? "找到了" : "没有"));
        System.out.println("找 99: " + (search(root, 99) != null ? "找到了" : "没有"));

        // 猜数字过程
        System.out.println("\n找 7 的路径:");
        TreeNode cur = root;
        int target = 7;
        while (cur != null) {
            System.out.print(cur.val + " → ");
            if (target == cur.val) { System.out.print("找到了!"); break; }
            cur = (target < cur.val) ? cur.left : cur.right;
        }
        System.out.println();  // 8 → 3 → 6 → 7 → 找到了!
    }
}
