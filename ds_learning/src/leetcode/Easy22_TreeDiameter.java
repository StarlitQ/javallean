/**
 * LeetCode #543 — 二叉树的直径 (Easy)
 *
 * ────────── 题目 ──────────
 * 给定一棵二叉树，计算它的直径（任意两个节点之间最长路径的边数）。
 *
 * 例1：    1
 *        /   \
 *       2     3
 *      / \
 *     4   5
 *  直径 = 3（路径 4→2→1→3 或 5→2→1→3，共 3 条边）
 *
 * 例2：输入: 1
 *            /
 *           2
 *      直径 = 1（路径 1→2）
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
public class Easy22_TreeDiameter {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    static int max;

    static int diameterOfBinaryTree(TreeNode root) {
        if (root == null) return 0;
        max = 0;
        find(root);
        return max;
    }

    static int find(TreeNode root) {
        if (root == null) return 0;
        int left = find(root.left);
        int right = find(root.right);
        max = Math.max(max, left + right);
        return Math.max(left, right) + 1;
    }

    public static void main(String[] args) {
        TreeNode r1 = new TreeNode(1);
        r1.left = new TreeNode(2);
        r1.right = new TreeNode(3);
        r1.left.left = new TreeNode(4);
        r1.left.right = new TreeNode(5);
        System.out.println(diameterOfBinaryTree(r1));  // 3

        TreeNode r2 = new TreeNode(1);
        r2.left = new TreeNode(2);
        System.out.println(diameterOfBinaryTree(r2));  // 1
    }
}
