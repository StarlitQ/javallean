/**
 * LeetCode #102 — 二叉树的层序遍历 (Medium)
 *
 * ────────── 题目 ──────────
 * 返回二叉树的自顶向下、从左到右的层序遍历结果。
 * 返回一个 List<List<Integer>>，每个内层 List 是一层的所有节点值。
 *
 * 例1：    3
 *        /   \
 *       9     20
 *            /  \
 *           15   7
 *  输出: [[3], [9,20], [15,7]]
 *
 * 例2：输入: 1    → 输出: [[1]]
 * 例3：输入: null  → 输出: []
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
import java.util.*;

public class Medium04_LevelOrder {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> nums = new ArrayList<>();
        if (root == null) return nums;
        Queue<TreeNode> pre = new LinkedList<>();
        pre.offer(root);
        while(!pre.isEmpty()){
            int size = pre.size();
            List<Integer> layer = new ArrayList<>();
            for (int i=0;i<size;i++){
                TreeNode cur = pre.poll();
                layer.add(cur.val);
                if (cur.left != null) pre.offer(cur.left);
                if (cur.right != null) pre.offer(cur.right);
            }
            nums.add(layer);
        }
        return nums;
    }

    public static void main(String[] args) {
        TreeNode r1 = new TreeNode(3);
        r1.left = new TreeNode(9);
        r1.right = new TreeNode(20);
        r1.right.left = new TreeNode(15);
        r1.right.right = new TreeNode(7);
        System.out.println(levelOrder(r1));  // [[3], [9, 20], [15, 7]]
    }
}
