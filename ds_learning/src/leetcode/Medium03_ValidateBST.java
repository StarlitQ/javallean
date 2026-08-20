/**
 * LeetCode #98 — 验证二叉搜索树 (Medium)
 *
 * ────────── 题目 ──────────
 * 给定一个二叉树的根节点，判断其是否是一个有效的二叉搜索树（BST）。
 *
 * 有效 BST 定义：
 *   - 左子树所有节点的值 ＜ 当前节点的值
 *   - 右子树所有节点的值 ＞ 当前节点的值
 *   - 左右子树也必须都是 BST
 *
 * 例1：  2        例2：  5
 *      / \             / \
 *     1   3           1   4
 *                               / \
 *                              3   6
 *     true           false（4的左子3＜4但＜5，不对）
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
public class Medium03_ValidateBST {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    static boolean isValidBST(TreeNode root) {
        return check(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    static boolean check(TreeNode root, int min, int max){
        if (root == null) return true;
        int mid = root.val;
        if (mid <= min || mid >= max) return false;
        boolean lBST = check(root.left, min, mid);
        boolean rBST = check(root.right, mid, max);
        if (lBST && rBST) return true;
        return false;
    }

    public static void main(String[] args) {
        TreeNode r1 = new TreeNode(2);
        r1.left = new TreeNode(1); r1.right = new TreeNode(3);
        System.out.println(isValidBST(r1));  // true

        TreeNode r2 = new TreeNode(5);
        r2.left = new TreeNode(1);
        r2.right = new TreeNode(4);
        r2.right.left = new TreeNode(3); r2.right.right = new TreeNode(6);
        System.out.println(isValidBST(r2));  // false
    }
}
