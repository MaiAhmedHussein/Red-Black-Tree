public class RBTree {
    private class RBNode{
        private RBNode left;
        private RBNode right;
        private RBNode parent;
        private RBNode sibling;
        private int value;


        private char color;
        RBNode(int value){
            this.value=value;
             color='R';
        }
        public boolean isLeftChild(){return this==parent.left;}
        public boolean isRightChild(){return this==parent.right;}
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
    /////////////////////////////////////////////////
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
        root.color='B';
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
        //update the left branch
        node.left=newNode.right;
        if(node.left!=null){
            node.left.parent=node;
        }
        //update the Right branch
        //make the right child of new node the previous root
        newNode.right=node;
         //update parent of newnode to be as the parent of previous
        newNode.parent=node.parent;
        updateParents(node,newNode);
        //finally the newnode will be the parent of the node
        node.parent=newNode;
        return newNode;
    }
     public void updateParents( RBNode node,RBNode newNode){
        //must do so the traverse be correct
         if(node.parent==null)
             //if parent of old root was null so newnode is the root
             root=newNode;
         else if(node.isLeftChild())
             node.parent.left=newNode;
         else
             node.parent.right=newNode;
    }
    private RBNode rotateLeft(RBNode node){
        //right heavy
        RBNode newNode =node.right;
        RBNode temp=newNode.left;
        newNode.left=node;
        node.right=temp;
        return newNode;
    }

    ///////////////////////////////////////////////////////Maiiiiii/////////////////////////////////////////////////////////////
    public RBNode sibling(RBNode node){
        if(node.isLeftChild()){
            return node.parent.right;
        }
        return node.parent.left;
    }
    public RBNode doubleBlack(RBNode node){
        //CASE 0: IF DB IS ROOT
        if(node.parent == null) {
            return null;
        }
        //CASE 1: IF SIBLING IS RED
        RBNode sibling = sibling(node);
        if(sibling.color=='R'){
            node.parent.color='R';
            sibling.color='B';
            if(node.isLeftChild()) {
                rotateLeft(node.getParent());
            }
            else {
                rotateRight(node.getParent());
            }
            return doubleBlack(node);
        }

        //CASE 2: IF SIBLING IS BLACK AND BOTH CHILDREN ARE BLACK /Nil
        else if(sibling.left.color=='B' && sibling.right.color=='B'){
            RBNode parent = node.parent;
            sibling.color='R';
            if(parent.color=='R') {
                parent.color = 'B';
            }else {
                return doubleBlack(parent);
            }

        }
        //CASE 3: If black sibling has at least one red child.
        //Case near:
        else if (sibling.isLeftChild() && sibling.left.color=='R' || sibling.isRightChild() && sibling.right.color=='R')
        {
            char f = node.parent.color;
            node.getParent().setColor(sibling.getColor());
            sibling.setColor(f);
            //TODO dasd
            if(sibling.isLeftChild()){
                sibling.left.color='B';
            }else {
                sibling.right.color='B';
            }

            if( node.isLeftChild()) {
                rotateLeft(node.getParent());
            }else {
                rotateRight(node.getParent());
            }

        }
        //Case Far:
        else{
            sibling.color='R';
            if(sibling.isLeftChild()){
                sibling.right.color='B';
            }else {
                sibling.left.color='B';
            }
            if(sibling.isLeftChild()){
                rotateLeft(sibling);
            }else{
                rotateRight(sibling);
            }
            return doubleBlack(node);
        }

        return null;

    }
    public void delete(int value){
       delete(findDeletedNode(root,value));
    }
    private RBNode findDeletedNode(RBNode node, int value){
        while(node!=null){
            if(value>node.value)
                node=node.right;
            else if(value< node.value)
                node =node.left;
            else
                return node;
        }
        return null;
    }


    private void delete(RBNode node){

        if(node==null){
            System.out.println("Node not found!!");
            return;
        }
        //Case 1: The node has no child.
        // If this node is the root.

        if(node.left==null && node.right==null){
           if(node == root) {
               node = null;
           }else{
               if(node.getColor()==('R')){
                   node=null;return;
               }else{
                   doubleBlack(node);
               }
               node=null;
           }
           //Case 2: If the node has 1 child, this child replaces it.
        }else if(node.right==null || node.left==null){

             if(node.left==null){
                 //If the node is the root.
                 if (node==root){
                     root = node.right;
                 }else{
                     // if the node is red then it's child is 100% black, so we don't need to change it.
                     //Else we have to change it's child's color to black.
                     if(node.color=='B') {
                         node.right.setColor('B');
                     }
                     RBNode rightChild=node.right;
                     rightChild.parent=node.parent;
                     //Let the child replace it's parent place.
                     if(node.isRightChild()) {
                         node.parent.right=rightChild;
                     }else{
                         node.parent.left=rightChild;
                     }


                 }
             }else{
                 //If the node is the root.
                 if (node==root){
                     root = node.left;
                 }else{
                     // if the node is red then it's child is 100% black, so we don't need to change it.
                     //Else we have to change it's child's color to black.
                     if(node.color=='B') {
                         node.left.setColor('B');
                     }
                     RBNode leftChild=node.left;
                     leftChild.parent=node.parent;
                     //Let the child replace it's parent place.
                     if(node.isRightChild()) {
                         node.parent.right=leftChild;
                     }else{
                         node.parent.left=leftChild;
                     }


                 }
             }
         //Case 3: The node has 2 children.
         //Replace the value of the node with the value of the minimum node on it's right subtree,
         //keeping it's original color as it is.
        }else{
            RBNode minRight= getMin(node.right);
            node.value=minRight.value;
            delete(minRight);
            //Again let's remove the minimum node on the original node right subtree, that we have prev. calculated.
        }

    }
    private RBNode getMin(RBNode node){
        if(node==null) {
            return null;
        }
        while (node.left!=null) {
            node = node.left;
        }
        return node;
    }

    ///////////////////////////////////////////////////////Maiiiii//////////////////////////////////////////////////////




    //    EXAMPLE rotate right   ///
    /*           'R'10   //grand parent (root)
    *             /   \
    (leftnode)'R'6     null
    (newnode) / \
    *     'R'4  9'R'
    * */

}
/* private RBNode rotateRight(RBNode node){
        RBNode newNode=node.left;
        //update the left branch
        // left of 10 (6) will be new node
        node.left=newNode.right;
        //link left of 10 will be 9
        if(node.left!=null){
            //check that left heavy
            //set 10 to be parent of 9
            node.left.parent=node;
        }
        //update the Right branch
        //make the right child of new node the previous root
        //let the right child of 6 be 10
        newNode.right=node;
         //update parent of newnode to be as the parent of previous
        newNode.parent=node.parent;
        updateParents(node,newNode);
        //finally the newnode will be the parent of the node
        node.parent=newNode;
        return newNode;
    }
*
*
*
* */