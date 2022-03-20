public class RBMain {
    public static void main(String[] arg){
        RBTree tree=new RBTree();
        tree.insert(8);
        tree.insert(18);
        tree.insert(5);
        tree.insert(15);
        tree.insert(17);
        tree.insert(25);


       /* long startTime = System.currentTimeMillis();
        while ((line = br.readLine()) != null) {
            tree.insert(Math.random());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime-startTime) + "ms");*/
        tree.traversePreOrder();
        System.out.println("sea");
        System.out.println(tree.search(15));
        System.out.println( tree.isEmpty());
        tree.clear();
        System.out.println("cleared");
        tree.traversePreOrder();
    }
}
