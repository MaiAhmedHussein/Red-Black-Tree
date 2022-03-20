public class RBMain {
    public static void main(String[] arg){
        RBTree tree=new RBTree();
        tree.insert(3);
        tree.insert(21);
        tree.insert(32);
        tree.insert(15);

        tree.traversePreOrder();
    }
}
