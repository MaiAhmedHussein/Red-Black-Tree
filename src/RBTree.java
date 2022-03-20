public class RBTree {
    class RBNode {
        RBNode left;
        RBNode right;
        RBNode parent;
        RBNode sibling;
        String value;

        public boolean isRightChild() {
            return this == parent.right;
        }

        private char color;

        RBNode(String value) {
            this.value = value;
            color = 'R';
        }

        public boolean isLeftChild() {
            return this == parent.left;
        }


    }

    ////////////////////////////////////////
    public RBNode root;

    public RBTree() {
        super();
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public RBNode getRoot() {
        return root;
    }

    public void clear() {
        root = null;
    }

    public String search(String value) {
        var current = root;
        while (current != null) {
            if (value.compareToIgnoreCase(current.value)>0)
                current = current.right;
            else if (value.compareToIgnoreCase(current.value)<0)
                current = current.left;
            else
                return "The searched  word" + " " + current.value + " " + "\'" + current.color + "\'" + "is Found";
        }
        return null;
    }

    public boolean contain(String value) {
        var current = root;
        while (current != null) {
            if (value.compareToIgnoreCase(current.value)>0)
                current = current.right;
            else if (value.compareToIgnoreCase(current.value)<0)
                current = current.left;
            else
                return true;
        }
        return false;
    }

    //means needs red black rules
    boolean isRedParent = false;
    boolean leftRotation = false;
    boolean rightRotation = false;
    boolean leftRightRotation = false;
    boolean rightLeftRotation = false;

    public void insert(String value) {
        if (this.root == null) {
            this.root = new RBNode(value);
            this.root.color = 'B';
        } else
            this.root = insert(this.root, value);
    }

    /*
    insert and check if need red black rules
    * */
    private RBNode insert(RBNode node, String value) {
        //recursive calls to insert at proper position according to BST properties.
        if (node == null)
            return (new RBNode(value));
        else if (value.compareToIgnoreCase(node.value) < 0) {

            node.left = insert(node.left, value);
            node.left.parent = node;
            if (node != this.root) {
                if (node.color == 'R' && node.left.color == 'R')
                    isRedParent = true;
            }
        } else if(value.compareToIgnoreCase(node.value) > 0) {
            node.right = insert(node.right, value);
            node.right.parent = node;
            if (node != this.root) {
                if (node.color == 'R' && node.right.color == 'R')
                    isRedParent = true;
            }

        }


        node = rotatingAndRecolouring(node);
        if (isRedParent) {
            checkingCases(node);
        }
        return node;
    }

    private void checkingCases(RBNode node) {
        if (!node.isLeftChild()) // to check which child is the current node of its parent
        {
            //if it is the right child therefore uncle is left child
            RBNode uncle = node.parent.left;
            RBNode grandparent = node.parent;
            if (uncle == null || uncle.color == 'B') {
                //case uncle is black -> rotate &recolor there is 2 cases

                if (node.left != null && node.left.color == 'R')
                    //parent in the right&child in left
                    this.rightLeftRotation = true;
                else if (node.right != null && node.right.color == 'R')
                    this.leftRotation = true;
            } else {
                //case uncle is red -> recolor
                uncle.color = 'B';
                node.color = 'B'; //parent become black
                if (grandparent != this.root)
                    grandparent.color = 'R';
            }
        } else {
            //if parent is left child
            RBNode uncle = node.parent.right;
            RBNode grandparent = node.parent;
            if (uncle == null || uncle.color == 'B') {
                if (node.left != null && node.left.color == 'R')
                    this.rightRotation = true;
                else if (node.right != null && node.right.color == 'R')
                    this.leftRightRotation = true;
            } else {
                uncle.color = 'B';
                node.color = 'B';
                if (grandparent != this.root)
                    grandparent.color = 'R';
            }
        }
        isRedParent = false;
    }

    private RBNode rotatingAndRecolouring(RBNode node) {
        // now lets rotate.
        if (this.leftRotation) // for left rotate.
        {
            node = rotateLeft(node);
            node.color = 'B';
            node.left.color = 'R';
            this.leftRotation = false;
        } else if (this.rightRotation) // for right rotate
        {
            node = rotateRight(node);
            node.color = 'B';
            node.right.color = 'R';
            this.rightRotation = false;
        } else if (this.rightLeftRotation) // for right and then left
        {
            node.right = rotateRight(node.right);
            node.right.parent = node;
            node = rotateLeft(node);
            node.color = 'B';
            node.left.color = 'R';

            this.rightLeftRotation = false;
        } else if (this.leftRightRotation) // for left and then right.
        {
            node.left = rotateLeft(node.left);
            node.left.parent = node;
            node = rotateRight(node);
            node.color = 'B';
            node.right.color = 'R';
            this.leftRightRotation = false;
        }

        return node;

    }

    private RBNode rotateRight(RBNode node) {
        RBNode newNode = node.left;
        //update the left branch
        node.left = newNode.right;
        if (node.left != null) {
            node.left.parent = node;
        }
        //update the Right branch
        //make the right child of new node the previous root
        newNode.right = node;
        //update parent of newnode to be as the parent of previous
        newNode.parent = node.parent;
        updateParents(node, newNode);
        //finally the newnode will be the parent of the node
        node.parent = newNode;
        return newNode;
    }

    public void updateParents(RBNode node, RBNode newNode) {
        //must do so the traverse be correct
        if (node.parent == null)
            //if parent of old root was null so newnode is the root
            root = newNode;
        else if (node.isLeftChild())
            node.parent.left = newNode;
        else
            node.parent.right = newNode;
    }

    private RBNode rotateLeft(RBNode node) {

        RBNode newNode = node.right;
        node.right = newNode.left;
        if (node.right != null) {
            node.right.parent = node;
        }
        newNode.left = node;
        newNode.parent = node.parent;
        updateParents(node, newNode);
        node.parent = newNode;
        return newNode;

    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    //printing
    public void traversePreOrder() {
        traversePreOrder(this.root);
    }

    private void traversePreOrder(RBNode root) {
        if (root != null) {
            System.out.print(root.value + " " + "\'" + root.color + "\'" + " ");
            traversePreOrder(root.left);
            traversePreOrder(root.right);
        }
    }

    ////////////////////////////////////////DELETE/////////////////////////////////////////////////
    public RBNode sibling(RBNode node) {
        if (node.isLeftChild()) {
            return node.parent.right;
        }
        return node.parent.left;
    }

    public RBNode doubleBlack(RBNode node) {
        //CASE 0: IF DB IS ROOT
        if (node.parent == null) {
            return null;
        }
        //CASE 1: IF SIBLING IS RED
        RBNode sibling = sibling(node);
        if(sibling!=null) {
            if (sibling.color == 'R') {
                node.parent.color = 'R';
                sibling.color = 'B';
                if (node.isLeftChild()) {
                    RBNode n =node.parent;
                    n.left = null;
                    rotateLeft(n);
                } else {
                    RBNode n =node.parent;
                    n.right = null;
                    rotateRight(n);
                }
                return doubleBlack(node);
            }

//CASE 2: IF SIBLING IS BLACK AND BOTH CHILDREN ARE BLACK /Nil
            else if (sibling.left != null && sibling.right != null && sibling.left.color == 'B' && sibling.right.color == 'B') {
                RBNode parent = node.parent;
                sibling.color = 'R';
                if (parent.color == 'R') {
                    parent.color = 'B';
                } else {
                    return doubleBlack(parent);
                }

            }
//CASE 3: If black sibling has at least one red child.
//Case near:
            else if (sibling.isLeftChild() && sibling.left != null && sibling.left.color == 'R' || sibling.isRightChild() && sibling.right != null && sibling.right.color == 'R') {
                //System.out.println("llll");
                //traversePreOrder();
                char f = node.parent.color;
                node.parent.color = sibling.color;
                sibling.color = f;
                if (sibling.left != null && sibling.isLeftChild()) {
                    sibling.left.color = 'B';
                } else if(sibling.right != null){
                    sibling.right.color = 'B';
                }

                if (node.isLeftChild()) {
                    RBNode n =node.parent;
                    n.left = null;
                    //traversePreOrder();
                    //System.out.println("kkkkk");
                    rotateLeft(n);
                    //traversePreOrder();
                } else {
                    RBNode n =node.parent;
                    n.right = null;
                    rotateRight(n);
                }

            }
//Case Far:
            else {
                sibling.color = 'R';
                if (sibling.right != null && sibling.isLeftChild()) {
                    sibling.right.color = 'B';
                } else if(sibling.left != null){

                    sibling.left.color = 'B';
                }
                if (sibling.right != null && sibling.isLeftChild()) {
                    node.parent.left = null;
                    rotateLeft(sibling);
                } else if(sibling.left != null){
                    node.parent.right = null;
                    rotateRight(sibling);
                }
                return doubleBlack(node);
            }
        }
        return null;

    }
    boolean flagdelete=true;
    public boolean delete(String value) {
        flagdelete=true;
        delete(findDeletedNode(root, value));
        return flagdelete;
    }

    private RBNode findDeletedNode(RBNode node, String value) {
        while (node != null) {
            if (value.compareToIgnoreCase(node.value)>0)
                node = node.right;
            else if (value.compareToIgnoreCase(node.value)<0)
                node = node.left;
            else
                return node;
        }
        return null;
    }


    private void delete(RBNode node) {

        if (node == null) {
           flagdelete=false;
            return;
        }
        //Case 1: The node has no child.
        // If this node is the root.

        if (node.left == null && node.right == null) {
            if (node == root) {
                node = null;
            } else {
                if (node.color == ('R')) {
                    // node=null;
                    if (node.isLeftChild()) {
                        node.parent.left = null;
                    } else {
                        node.parent.right = null;
                    }
                    return;
                } else {
                    // System.out.println("hhhh");
                    // traversePreOrder();
                    doubleBlack(node);
                }
                //node=null;
            }
            //Case 2: If the node has 1 child, this child replaces it.
        } else if (node.right == null || node.left == null) {

            if (node.left == null) {
                //If the node is the root.
                if (node == root) {
                    root = node.right;
                } else {
                    // if the node is red then it's child is 100% black, so we don't need to change it.
                    //Else we have to change it's child's color to black.
                    if (node.color == 'B') {
                        node.right.color = 'B';
                    }
                    RBNode rightChild = node.right;
                    rightChild.parent = node.parent;
                    //Let the child replace it's parent place.
                    if (node.isRightChild()) {
                        node.parent.right = rightChild;
                    } else {
                        node.parent.left = rightChild;
                    }


                }
            } else {
                //If the node is the root.
                if (node == root) {
                    root = node.left;
                } else {
                    // if the node is red then it's child is 100% black, so we don't need to change it.
                    //Else we have to change it's child's color to black.
                    if (node.color == 'B') {
                        node.left.color = 'B';
                    }
                    RBNode leftChild = node.left;
                    leftChild.parent = node.parent;
                    //Let the child replace it's parent place.
                    if (node.isRightChild()) {
                        node.parent.right = leftChild;
                    } else {
                        node.parent.left = leftChild;
                    }


                }
            }
            //Case 3: The node has 2 children.
            //Replace the value of the node with the value of the minimum node on it's right subtree,
            //keeping it's original color as it is.
        } else {
            RBNode minRight = getMin(node.right);
            node.value = minRight.value;
            delete(minRight);
            //Again let's remove the minimum node on the original node right subtree, that we have prev. calculated.
        }
    // System.out.println("deleted successfully");
    }

    private RBNode getMin(RBNode node) {
        if (node == null) {
            return null;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
}
//    EXAMPLE rotate right   ///
    /*           'R'10   //grand parent (root)
    *             /   \
    (leftnode)'R'6     null
    (newnode) / \
    *     'R'4  9'R'
    * */


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
