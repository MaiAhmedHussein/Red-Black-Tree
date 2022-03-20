import java.util.ArrayList;

public class TestingTime {

    RBTree tree = new RBTree();

    public  void test() {
        ArrayList<String> list = new ArrayList<>();
        RandomString random = new RandomString();
        long startInsert = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            String s = random.randomString();
            list.add(s);
            tree.insert(s);

        }
        long endInsert = System.currentTimeMillis();
        System.out.println("execution time for  Red Black insertion : " + (endInsert - startInsert) + "ms\n");

        long startDelete = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            double ss = Math.floor(Math.random() * (100 - 1));
            tree.delete(list.get((int) ss));
        }
        long endDelete = System.currentTimeMillis();
        System.out.println("execution time  for Red Black deletion : " + (endDelete - startDelete) + "ms\n");
    }
}

