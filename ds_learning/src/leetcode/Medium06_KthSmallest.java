/**
 * LeetCode #230 — 二叉搜索树中第K小的元素 (Medium)
 *
 * ────────── 题目 ──────────
 * 给定一个二叉搜索树，找出其中第 k 小的元素。
 *
 * 例1：  3        例2：    5
 *      / \               /   \
 *     1   4             3     6
 *      \              /   \
 *       2            2     4
 *    k=1 → 1         \
 *                      1
 *                    k=3 → 3
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
import java.util.*;
public class Medium06_KthSmallest {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    static int result, count = 0;
    static int kthSmallest(TreeNode root, int k) {
        count = 0;
        // List<Integer> nums = new ArrayList<>();
        // Inorder(nums, root);
        // return nums.get(k - 1);
        Inorder(root, k);
        return result;

    }

    static void Inorder(TreeNode root, int k){
        if (root == null) return;
        // Inorder(nums, root.left);
        // nums.add(root.val);
        // Inorder(nums, root.right);
        Inorder(root.left, k);
        count++;
        if (count == k){
            result = root.val;
            return;
        }
        Inorder(root.right, k);
    }

    public static void main(String[] args) {
        TreeNode r1 = new TreeNode(3);
        r1.left = new TreeNode(1);
        r1.right = new TreeNode(4);
        r1.left.right = new TreeNode(2);
        System.out.println(kthSmallest(r1, 1));  // 1

        TreeNode r2 = new TreeNode(5);
        r2.left = new TreeNode(3);
        r2.right = new TreeNode(6);
        r2.left.left = new TreeNode(2);
        r2.left.right = new TreeNode(4);
        r2.left.left.left = new TreeNode(1);
        System.out.println(kthSmallest(r2, 3));  // 3
    }
}
