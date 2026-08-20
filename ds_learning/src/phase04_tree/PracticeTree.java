package phase04_tree;

public class PracticeTree {
    static class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int val){
            this.val = val;
        }

    }
    static int height(TreeNode root){
        if (root == null)return 0;
        int leftH = height(root.left);
        int rightH = height(root.right);
        return max(leftH, rightH) + 1;
    }

    static int max(int a, int b){
        return a > b ? a : b;
    }

    static int countLeaves(TreeNode root){
        if (root == null)return 0;
        if (root.left == null && root.right == null)return 1;
        return countLeaves(root.left)+countLeaves(root.right);

    }

    static TreeNode findMin(TreeNode root){
        if (root == null) return null;
        while (root.left != null){
            root = root.left;
        }
        return root;
    }

    static TreeNode findMax(TreeNode root){
        if (root == null) return null;
        while (root.right != null){
            root = root.right;
        }
        return root;
    }

}
