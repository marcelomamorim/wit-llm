/* @test    @summary Test for ArrayList.size() method to observe behavior and exceptions    @run main ArrayListWitupTest */

import java.util.ArrayList;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testSizeNormal();
        testSizeAfterModification();
        testSizeWithConcurrentModification();
    }

    private static void testSizeNormal() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        int size = list.size();
        System.out.println("Normal size: " + size); // Expect 2
    }

    private static void testSizeAfterModification() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.remove(0);
        int size = list.size();
        System.out.println("Size after removal: " + size); // Expect 1
    }

    private static void testSizeWithConcurrentModification() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        ArrayList<String> sublist = (ArrayList<String>) list.clone();
        list.add("c");
        try {
            int size = sublist.size();
            System.out.println("Sublist size: " + size); // Expect 2
        } catch (java.util.ConcurrentModificationException e) {
            System.out.println("Caught ConcurrentModificationException as expected");
        }
    }
}
