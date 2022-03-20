import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class RBMain {

    public static void main(String[] arg) {
        RBTree tree = new RBTree();
        TestingTime test= new TestingTime();
         test.test();

        /*Scanner input =new Scanner(System.in);
        System.out.println("1-insert");
        System.out.println("2-getRoot ");
        System.out.println("3-delete");
        System.out.println("4-search");
        System.out.println("5-contain");
        System.out.println("6-isEmpty");
        System.out.println("7-print in preorder traverse");
        System.out.println("8-EXIT");
        int op;
        boolean flag = true;
        while (flag) {

            System.out.println("--------------------------------");
            System.out.println("Enter number of what you need:");
            op = input.nextInt();
            switch (op) {
                case 1: {
                    System.out.println("insert the value :");
                    String value = input.next();
                    tree.insert(value);
                }
                break;

                case 2: {
                    System.out.println("The root of the tree :");
                    tree.getRoot();

                }
                break;
                case 3: {
                    System.out.println("delete the value :");
                    String value = input.next();
                   System.out.println(tree.delete(value));
                }
                break;
                case 4: {
                    System.out.println("search for the value :");
                    String value = input.next();
                    tree.search(value);
                }
                break;
                case 5: {
                    System.out.println("enter the value to check if it contains in the tree :");
                    String value = input.next();
                   System.out.println(tree.contain(value));
                }
                break;
                case 6: {
                    if(tree.isEmpty())
                        System.out.println("tree is empty");
                    else
                        System.out.println("tree is  not empty");

                }
                break;
                case 7: {
                    System.out.println("print in preorder traverse: ");
                    tree.traversePreOrder();
                    System.out.println();
                }

                break;

                case 10:
                    flag = false;
                    break;
                default:
                    System.out.print("INVALID INPUT!! ");

            }

        }*/

    }
}