public class InOrder {
    public static void main(String[] args) {
        TreeNode root=new TreeNode(2,null,null);
        root.left=new TreeNode(1,null,null);
        root.right=new TreeNode(3,null,null);
        inOrder(root);
    }

    public static void inOrder(TreeNode root){
        if(root.left!=null){
            inOrder(root.left);
        }
        System.out.println(root.val);
        if(root.right!=null){
            inOrder(root.right);
        }
    }
}
