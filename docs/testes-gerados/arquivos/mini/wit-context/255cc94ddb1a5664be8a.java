/*
 * @test
 * @summary Test java.util.ArrayList addAll(Collection) method behavior
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testAddAllWithEmptyCollection();
        testAddAllWithNonEmptyCollection();
        testAddAllWithNullCollection();
    }

    private static void testAddAllWithEmptyCollection() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        boolean result = list.addAll(Collections.emptyList());
        if (result) {
            throw new RuntimeException("addAll with empty collection should return false");
        }
        if (list.size() != 2) {
            throw new RuntimeException("List size changed after addAll with empty collection");
        }
        if (!"a".equals(list.get(0)) || !"b".equals(list.get(1))) {
            throw new RuntimeException("List contents changed after addAll with empty collection");
        }
    }

    private static void testAddAllWithNonEmptyCollection() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");
        List<String> toAdd = new ArrayList<>();
        toAdd.add("1");
        toAdd.add("2");
        boolean result = list.addAll(toAdd);
        if (!result) {
            throw new RuntimeException("addAll with non-empty collection should return true");
        }
        if (list.size() != 4) {
            throw new RuntimeException("List size incorrect after addAll");
        }
        if (!"x".equals(list.get(0)) || !"y".equals(list.get(1)) || !"1".equals(list.get(2)) || !"2".equals(list.get(3))) {
            throw new RuntimeException("List contents incorrect after addAll");
        }
    }

    private static void testAddAllWithNullCollection() {
        ArrayList<String> list = new ArrayList<>();
        try {
            list.addAll(null);
            throw new RuntimeException("addAll with null collection should throw NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
