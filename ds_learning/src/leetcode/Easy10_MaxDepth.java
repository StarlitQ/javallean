/**
 * LeetCode #104 — 二叉树的最大深度 (Easy)
 *
 * ────────── 题目 ──────────
 * 给定一个二叉树，找出其最大深度（根节点到最远叶子节点的最长路径上的节点数）。
 *
 * 例1：   3
 *        / \
 *       9  20
 *          / \
 *         15  7
 *      最大深度 = 3（3→20→15 或 3→20→7）
 *
 * 例2：入力: null → 输出: 0
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
public class Easy10_MaxDepth {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    static int maxDepth(TreeNode root) {
        return findD(root, 0);
    }

    static int findD(TreeNode root, int curd) {
        if (root == null) return curd;
        curd++;
        return Math.max(findD(root.left, curd), findD(root.right, curd));
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        System.out.println(maxDepth(root));  // 3
        System.out.println(maxDepth(null));  // 0
    }
}
