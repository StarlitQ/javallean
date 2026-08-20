package phase04_tree;

/**
 * 👀 Demo — 二叉树
 *
 * 树的基本术语：
 * ┌─────────┐
 * │   root  │ ← 根节点（最上面那个）
 * ├────┬────┤
 * │ L  │ R  │ ← 左孩子、右孩子
 * └────┴────┘
 * 叶子节点 — 没有孩子的节点
 * 高度     — 从根到最远叶子的层数
 *
 * 三种遍历（递归的角度看）：
 * 前序：我 → 左 → 右    （根最先）
 * 中序：左 → 我 → 右    （根中间）
 * 后序：左 → 右 → 我    （根最后）
 */
public class BinaryTreeDemo {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int v) { this.val = v; }
    }

    // ===== 三种递归遍历 =====

    // 前序：根 → 左 → 右
    static void preOrder(TreeNode node) {
        if (node == null) return;
        System.out.print(node.val + " ");   // 先访问自己
        preOrder(node.left);                // 再走左边
        preOrder(node.right);               // 最后走右边
    }

    // 中序：左 → 根 → 右  （二叉搜索树的中序 = 排序结果！）
    static void inOrder(TreeNode node) {
        if (node == null) return;
        inOrder(node.left);
        System.out.print(node.val + " ");
        inOrder(node.right);
    }

    // 后序：左 → 右 → 根  （删除文件：先删子文件夹，最后删自己）
    static void postOrder(TreeNode node) {
        if (node == null) return;
        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.val + " ");
    }

    public static void main(String[] args) {
        // 手造一棵树：
        //        1
        //      /   \
        //     2     3
        //    / \   / \
        //   4   5 6   7
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);

        System.out.print("前序 (根左右): ");
        preOrder(root);   // 1 2 4 5 3 6 7
        System.out.print("\n中序 (左根右): ");
        inOrder(root);    // 4 2 5 1 6 3 7
        System.out.print("\n后序 (左右根): ");
        postOrder(root);  // 4 5 2 6 7 3 1
        System.out.println();
    }
}
