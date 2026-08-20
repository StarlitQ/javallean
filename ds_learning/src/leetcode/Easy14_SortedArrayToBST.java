/**
 * LeetCode #108 — 将有序数组转换为二叉搜索树 (Easy)
 *
 * ────────── 题目 ──────────
 * 给你一个升序排列的整数数组 nums，将其转换为一棵高度平衡的二叉搜索树。
 * 高度平衡：每个节点的左右子树高度差不超过 1。
 *
 * 例1：nums = [-10,-3,0,5,9]
 *      可能输出: [0,-3,9,-10,null,5]  或  [0,-10,5,null,-3,null,9]
 *      （不止一种答案，只要平衡即可）
 *
 * 例2：nums = [1,3] → 输出: [3,1] 或 [1,null,3]
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
public class Easy14_SortedArrayToBST {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    static TreeNode sortedArrayToBST(int[] nums) {
        // int V = nums.length;
        // if (V == 1) return new TreeNode(nums[0]);
        // int mid = V / 2;
        // TreeNode top = new TreeNode(nums[mid]);
        // int[] arrl = new int[mid];
        // int[] arrr = new int[V - mid - 1];
        // top.left = sortedArrayToBST()
        int left = 0;
        int right = nums.length - 1;
        return build(nums, left, right);
    }

    static TreeNode build(int[] nums, int left, int right){
        if (left > right) return null;
        int mid = (left + right) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = build(nums, left, mid - 1);
        root.right = build(nums, mid + 1, right);
        return root;
    }

    // 前序打印验证
    static void preorder(TreeNode root) {
        if (root == null) { System.out.print("null "); return; }
        System.out.print(root.val + " ");
        preorder(root.left);
        preorder(root.right);
    }

    public static void main(String[] args) {
        int[] nums1 = {-10, -3, 0, 5, 9};
        TreeNode root1 = sortedArrayToBST(nums1);
        preorder(root1);  // 中间元素作根，左右递归
        System.out.println();

        int[] nums2 = {1, 3};
        TreeNode root2 = sortedArrayToBST(nums2);
        preorder(root2);
        System.out.println();
    }
}
