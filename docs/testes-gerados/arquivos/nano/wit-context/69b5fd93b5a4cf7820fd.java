/*
 * @test
 * @summary Test for LinkedList.addAll(Collection) method.
 * @run main LinkedListWitupTest
 */

import java.util.LinkedList;
import java.util.Collection;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testAddAllWithNullCollection();
        testAddAllWithEmptyCollection();
        testAddAllWithNonEmptyCollection();
    }

    static void testAddAllWithNullCollection() {
        LinkedList<Integer> list = new LinkedList<>();
        try {
            list.addAll(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static void testAddAllWithEmptyCollection() {
        LinkedList<Integer> list = new LinkedList<>();
        Collection<Integer> empty = java.util.Collections.emptyList();
        boolean result = list.addAll(empty);
        if (result && list.isEmpty()) {
            System.out.println("PASS: addAll with empty collection results in empty list");
        } else {
            System.out.println("FAIL: addAll with empty collection did not behave as expected");
        }
    }

    static void testAddAllWithNonEmptyCollection() {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        Collection<Integer> coll = java.util.Arrays.asList(3, 4);
        boolean result = list.addAll(coll);
        if (result && list.size() == 4 && list.get(2) == 3 && list.get(3) == 4) {
            System.out.println("PASS: addAll with non-empty collection appends elements correctly");
        } else {
            System.out.println("FAIL: addAll with non-empty collection did not behave as expected");
        }
    }
}
