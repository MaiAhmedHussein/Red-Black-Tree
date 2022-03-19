public class RBTree {
    private class RBNode{
        private RBNode left;
        private RBNode right;
        private RBNode parent;
        private int value;
        private char color;
        RBNode(int value){this.value=value;}
        public boolean isLeftChild(){return this==parent.left;}
        public void setColor(char color) {
            this.color = color;
        }
        public void switchColour(){
            setColor(color=='R'? 'B':'R');
        }

    }
    /////////////////////////////////////////////////////////
    private RBNode root;


    public boolean isEmpty(){
        return root==null;
    }
    public RBNode getRoot() {
        return root;
    }

    public void clear(){
        root=null;
    }

    public String search(int value){
        var current=root;
        while(current!=null){
            if(value>current.value)
                current=current.right;
            else if(value< current.value)
                current=current.left;
            else
                return current.value+" "+ +current.color;
        }
        return null;
    }

    public boolean contain(int value){
        var current=root;
        while(current!=null){
            if(value>current.value)
                current=current.right;
            else if(value< current.value)
                current=current.left;
            else
                return true;
        }
        return false;
    }

    //printing
    public void traversePreOrder(){
        traversePreOrder(root);
    }
    private void traversePreOrder(RBNode root){
        if(root!=null) {
            System.out.print (root.value +" " + "\'"+ root.color+"\'"+ " ");
            traversePreOrder(root.left);
            traversePreOrder(root.right);
        }
    }
    ////////////////////////////////////


}
