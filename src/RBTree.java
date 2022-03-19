public class RBTree {
    private class RBNode{
        private RBNode left;
        private RBNode right;
        private RBNode parent;
        private int value;


        private char color;
        RBNode(int value){
            this.value=value;
             color='R';
        }
        public boolean isLeftChild(){return this==parent.left;}
        public void setColor(char color) {
            this.color = color;
        }
        public char getColor() {
            return color;
        }

        public RBNode getParent() {
            return parent;
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
    public void  insert(int value){
             root=insert(root,value);

    }
    private RBNode insert(RBNode node,int value){
        if(node==null)
            return new RBNode(value);
        if(value>node.value)
            node.right= insert(node.right,value);
        else if(value<node.value)
            node.left= insert(node.left,value);
        //not yet
        checkRecolorRotate(node);
        return node;
    }
    private void checkRecolorRotate(RBNode node){
        RBNode parent=node.getParent();
           // not the root & parent red
        if(node!=root&& parent.getColor()=='R'){
            RBNode grandparent=node.getParent().getParent();
            //if parent left then uncle right
            RBNode uncle=(parent.isLeftChild()? grandparent.right:grandparent.left);
            //1-case uncle red -> recolour
            if(uncle!=null && uncle.getColor()=='R'){
                reColor(parent,uncle,grandparent);
            }
            //2- case uncle black &leftheavy -> rotate(right|leftright) &recolor
            else if(parent.isLeftChild()){
                handleLeftCase(node,parent,grandparent);
            }
            //2- case uncle black &rightheavy -> rotate(left|rightleft) &recolor
            else if(!parent.isLeftChild()){
                handleRightCase(node,parent,grandparent);
            }

        }
    }
    private void reColor(RBNode parent,RBNode uncle ,RBNode grandparent){
        uncle.switchColour();
        parent.switchColour();
        grandparent.switchColour();
        //recursive call to loop from inserted node up to root
        checkRecolorRotate(grandparent);
    }
    private void handleLeftCase(RBNode node,RBNode parent,RBNode grandparent){
        parent.switchColour();
        grandparent.switchColour();
        rotateRight(grandparent);
        //if node is left child then it is right rotation
        //but if it is right child then it is left right rotation
        checkRecolorRotate(node.isLeftChild()? parent : grandparent);
    }
    private void handleRightCase(RBNode node,RBNode parent,RBNode grandparent){
        parent.switchColour();
        grandparent.switchColour();
        rotateLeft(grandparent);
        //if node is left child then it is right rotation
        //but if it is right child then it is left right rotation
        checkRecolorRotate(node.isLeftChild()? grandparent : parent);
    }

    private RBNode rotateRight(RBNode node){
        RBNode newNode=node.left;
        RBNode temp=newNode.right;
        newNode.right=node;
        node.left=temp;
        return newNode;
    }
    private RBNode rotateLeft(RBNode node){
        //right heavy
        RBNode newNode =node.right;
        RBNode temp=newNode.left;
        newNode.left=node;
        node.right=temp;
        return newNode;
    }

}
