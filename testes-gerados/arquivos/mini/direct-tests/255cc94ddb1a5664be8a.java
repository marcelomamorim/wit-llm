/*
 * @test
 * @summary Test java.util.ArrayList addAll(Collection) method
 * @run main java.util.ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testAddAllWithEmptyCollection();
        testAddAllWithNonEmptyCollection();
        testAddAllWithSelf();
    }

    private static void testAddAllWithEmptyCollection() {
        ArrayList<String> list = new ArrayList<>();
        boolean changed = list.addAll(Collections.emptyList());
        if (changed) {
            throw new RuntimeException("addAll(empty) should return false when no elements added");
        }
        if (!list.isEmpty()) {
            throw new RuntimeException("List should remain empty after addAll(empty)");
        }
    }

    private static void testAddAllWithNonEmptyCollection() {
        ArrayList<String> list = new ArrayList<>();
        List<String> toAdd = new ArrayList<>();
        toAdd.add("a");
        toAdd.add("b");
        boolean changed = list.addAll(toAdd);
        if (!changed) {
            throw new RuntimeException("addAll(non-empty) should return true when elements added");
        }
        if (list.size() != 2) {
            throw new RuntimeException("List size should be 2 after addAll");
        }
        if (!"a".equals(list.get(0)) || !"b".equals(list.get(1))) {
            throw new RuntimeException("List elements not as expected after addAll");
        }
    }

    private static void testAddAllWithSelf() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");
        // Adding the list to itself should add elements at the end
        boolean changed = list.addAll(list);
        if (!changed) {
            throw new RuntimeException("addAll(self) should return true when elements added");
        }
        if (list.size() != 4) {
            throw new RuntimeException("List size should double after addAll(self)");
        }
        if (!"x".equals(list.get(0)) || !"y".equals(list.get(1)) || !"x".equals(list.get(2)) || !"y".equals(list.get(3))) {
            throw new RuntimeException("List elements not as expected after addAll(self)");
        }
    }
}
