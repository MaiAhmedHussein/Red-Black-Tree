public class RBTree {
    private class RBNode{
        private RBNode left;
        private RBNode right;
        private RBNode parent;
        private int value;
        private char color;
        RBNode(int value){this.value=value;}

    }
    private RBNode root;
    public boolean isEmpty(){
        return root==null;
    }

}
