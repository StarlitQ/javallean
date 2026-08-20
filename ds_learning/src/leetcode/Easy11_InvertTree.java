/**
 * LeetCode #226 — 翻转二叉树 (Easy)
 *
 * ────────── 题目 ──────────
 * 翻转一棵二叉树——把每个节点的左右孩子互换。
 *
 * 例：     4             4
 *        /   \         /   \
 *       2     7       7     2
 *      / \   / \     / \   / \
 *     1   3 6   9   9   6 3   1
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
public class Easy11_InvertTree {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    static TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        TreeNode tmp = root.left;
        root.left = invertTree(root.right);
        root.right = invertTree(tmp);
        return root;
    }

    // 打印（前序）
    static void printTree(TreeNode root) {
        if (root == null) { System.out.print("null "); return; }
        System.out.print(root.val + " ");
        printTree(root.left);
        printTree(root.right);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(7);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(9);

        System.out.print("原树: "); printTree(root);
        root = invertTree(root);
        System.out.print("\n翻转: "); printTree(root);
    }
}
