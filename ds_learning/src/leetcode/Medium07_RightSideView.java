/**
 * LeetCode #199 — 二叉树的右视图 (Medium)
 *
 * ────────── 题目 ──────────
 * 给定一个二叉树，从右侧看它，返回你能看到的节点值（从上到下）。
 *
 * 例1：    1
 *        /   \
 *       2     3
 *        \     \
 *         5     4
 *  输出: [1, 3, 4]  （右视图中 5 被 3 挡住，看不到）
 *
 * 例2：  1
 *         \
 *          3
 *  输出: [1, 3]
 *
 * ────────── 提示 ──────────
 * 无官方提示。
 */
import java.util.*;

public class Medium07_RightSideView {

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    static List<Integer> rightSideView(TreeNode root) {
        List<Integer> nums = new ArrayList<>();
        Queue<TreeNode> pre = new LinkedList<>();
        pre.offer(root);
        while(!pre.isEmpty()){
            int size = pre.size();
            for (int i = 0; i < size; i++){
                TreeNode cur = pre.poll();
                if (cur.left != null){
                    pre.offer(cur.left);
                }
                if (cur.right != null){
                    pre.offer(cur.right);
                }
                if (i == size - 1){
                    nums.add(cur.val);
                }
            }
        }
        return nums;
    }

    public static void main(String[] args) {
        TreeNode r1 = new TreeNode(1);
        r1.left = new TreeNode(2);
        r1.right = new TreeNode(3);
        r1.left.right = new TreeNode(5);
        r1.right.right = new TreeNode(4);
        System.out.println(rightSideView(r1));  // [1, 3, 4]
    }
}
