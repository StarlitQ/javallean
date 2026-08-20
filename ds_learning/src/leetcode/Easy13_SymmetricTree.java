/**
 * LeetCode #101 — 对称二叉树 (Easy)
 *
 * ────────── 题目 ──────────
 * 判断一棵二叉树是否关于中心轴对称（镜像对称）。
 *
 * 例1：    1           例2：    1
 *        /   \                /   \
 *       2     2              2     2
 *      / \   / \              \     \
 *     3   4 4   3              3     3
 *     输出: true              输出: false
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
public class Easy13_SymmetricTree {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    static boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        
        return isMirror(root.left, root.right);
    }

    static boolean isMirror(TreeNode t1, TreeNode t2){
        if (t1 == null && t2 == null) return true;
        if (t1 == null || t2 == null) return false;
        return t1.val == t2.val && isMirror(t1.left, t2.right) && isMirror(t1.right, t2.left);
    }

    // static boolean Judge(TreeNode left, TreeNode right){
    //     boolean flag1 = false;
    //     boolean flag2 = false;
    //     if (left.val != right.val) return false;
    //     if (left.left != null && right.right != null) {
    //         flag1 = Judge(left.left, right.right);
    //     }
    //     else if (left.left == null && right.right == null){
    //         flag1 = true;
    //     }
    //     else return false;

    //     if (left.right != null && right.left !=null){
    //         flag2 = Judge(left.right, right.left);
    //     }
    //     else if (left.right == null && right.left == null){
    //         flag2 = true;
    //     }
    //     else return false;

    //     return flag1 && flag2;

    // }

    public static void main(String[] args) {
        // 对称树
        TreeNode r1 = new TreeNode(1);
        r1.left = new TreeNode(2);  r1.right = new TreeNode(2);
        r1.left.left = new TreeNode(3);  r1.left.right = new TreeNode(4);
        r1.right.left = new TreeNode(4); r1.right.right = new TreeNode(3);
        System.out.println(isSymmetric(r1));  // true

        // 不对称
        TreeNode r2 = new TreeNode(1);
        r2.left = new TreeNode(2);  r2.right = new TreeNode(2);
        r2.left.right = new TreeNode(3);
        r2.right.right = new TreeNode(3);
        System.out.println(isSymmetric(r2));  // false
    }
}
