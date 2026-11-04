/*
 * @test
 * @summary Tests ArrayList.removeIf(Predicate) null handling and basic observable behavior
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.function.Predicate;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testNullFilterThrowsNPE();
        testNullFilterLeavesListUnchanged();
        testRemoveIfRemovesMatchingElements();
        testRemoveIfWithoutMatchReturnsFalse();
    }

    static void testNullFilterThrowsNPE() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        boolean thrown = false;
        try {
            list.removeIf((Predicate<String>) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null filter");
        }
    }

    static void testNullFilterLeavesListUnchanged() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        try {
            list.removeIf((Predicate<String>) null);
        } catch (NullPointerException e) {
            // expected
        }
        if (list.size() != 2 || !"a".equals(list.get(0)) || !"b".equals(list.get(1))) {
            throw new RuntimeException("List was modified after null filter: " + list);
        }
    }

    static void testRemoveIfRemovesMatchingElements() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        boolean changed = list.removeIf(x -> x % 2 == 0);
        if (!changed) {
            throw new RuntimeException("removeIf should return true when elements are removed");
        }
        if (list.size() != 5) {
            throw new RuntimeException("Unexpected size: " + list.size());
        }
        for (Integer x : list) {
            if (x % 2 == 0) {
                throw new RuntimeException("Even element not removed: " + x);
            }
        }
    }

    static void testRemoveIfWithoutMatchReturnsFalse() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");
        boolean changed = list.removeIf(s -> s.equals("z"));
        if (changed) {
            throw new RuntimeException("removeIf should return false when nothing matches");
        }
        if (list.size() != 2) {
            throw new RuntimeException("List size changed unexpectedly: " + list.size());
        }
    }
}
