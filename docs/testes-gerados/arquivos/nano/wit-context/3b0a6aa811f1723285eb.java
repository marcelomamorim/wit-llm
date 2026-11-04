/*
 * @test
 * @summary Test removeAll(Collection c) method for ArrayList
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testRemoveAllWithNull();
        testRemoveAllWithEmptyCollection();
        testRemoveAllWithNonEmptyCollection();
    }

    private static void testRemoveAllWithNull() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        try {
            list.removeAll(null);
            System.out.println("testRemoveAllWithNull: Failed - no exception");
        } catch (NullPointerException e) {
            System.out.println("testRemoveAllWithNull: Passed");
        } catch (Exception e) {
            System.out.println("testRemoveAllWithNull: Failed - wrong exception: " + e);
        }
    }

    private static void testRemoveAllWithEmptyCollection() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        Collection<Integer> emptyCol = new ArrayList<>();
        boolean result = list.removeAll(emptyCol);
        if (!result && list.size() == 2) {
            System.out.println("testRemoveAllWithEmptyCollection: Passed");
        } else {
            System.out.println("testRemoveAllWithEmptyCollection: Failed");
        }
    }

    private static void testRemoveAllWithNonEmptyCollection() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Collection<Integer> col = new ArrayList<>();
        col.add(2);
        col.add(3);
        boolean result = list.removeAll(col);
        if (result && list.size() == 1 && list.get(0) == 1) {
            System.out.println("testRemoveAllWithNonEmptyCollection: Passed");
        } else {
            System.out.println("testRemoveAllWithNonEmptyCollection: Failed");
        }
    }
}
