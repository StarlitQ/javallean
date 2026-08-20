/**
 * LeetCode #94 — 二叉树的中序遍历 (Easy)
 *
 * ────────── 题目 ──────────
 * 给定一个二叉树的根节点 root，返回它的中序遍历（按左→根→右顺序访问）。
 *
 * 例1：  1
 *         \
 *          2
 *         /
 *        3
 *      输入: root = [1,null,2,3]
 *      输出: [1,3,2]
 *
 * 例2：  输入: root = []
 *        输出: []
 *
 * 例3：  1
 *       /
 *      2
 *      输入: root = [1,2]
 *      输出: [2,1]
 *
 * ────────── 提示 ──────────
 * Phase 4 学过的！中序 = 左 → 根 → 右，递归三行搞定。
 * 进阶：试试迭代版（用栈模拟递归）。
 */
import java.util.*;

public class Easy05_TreeTraversal {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Inorder(result, root);
        return result;
    }

    static void Inorder(List<Integer> result, TreeNode root){
        if (root == null){
            return;
        }
        Inorder(result, root.left);
        result.add(root.val);
        Inorder(result, root.right);
    }

    public static void main(String[] args) {
        // 构建: 1 → right → 2 → left → 3
        TreeNode root = new TreeNode(1);
        root.right = new TreeNode(2);
        root.right.left = new TreeNode(3);
        System.out.println(inorderTraversal(root));  // [1, 3, 2]
    }
}
