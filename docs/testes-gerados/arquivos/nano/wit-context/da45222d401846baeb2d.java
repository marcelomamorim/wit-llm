/*
 * @test
 * @summary Test for LinkedList.add(E e) method to observe behavior and exception handling.
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testAddNormal();
        testAddNull();
        testConcurrentModification();
    }

    static void testAddNormal() {
        LinkedList<String> list = new LinkedList<>();
        list.add("element");
        if (!list.isEmpty() && "element".equals(list.get(0))) {
            System.out.println("testAddNormal passed");
        } else {
            System.out.println("testAddNormal failed");
        }
    }

    static void testAddNull() {
        LinkedList<String> list = new LinkedList<>();
        list.add(null);
        if (list.size() == 1 && list.get(0) == null) {
            System.out.println("testAddNull passed");
        } else {
            System.out.println("testAddNull failed");
        }
    }

    static void testConcurrentModification() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        try {
            for (String s : list) {
                list.remove(s);
                // Modifying list during iteration to trigger exception
            }
            System.out.println("testConcurrentModification failed");
        } catch (java.util.ConcurrentModificationException e) {
            System.out.println("testConcurrentModification passed");
        } catch (Exception e) {
            System.out.println("testConcurrentModification failed with unexpected exception: " + e);
        }
    }
}
