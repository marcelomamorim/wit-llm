/*
 * @test
 * @summary Test java.util.ArrayList removeIf(Predicate) method basic behavior
 * @run main java.util.ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.function.Predicate;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testRemoveIfRemovesMatchingElements();
        testRemoveIfReturnsFalseWhenNoMatch();
        testRemoveIfOnEmptyListReturnsFalse();
    }

    private static void testRemoveIfRemovesMatchingElements() {
        ArrayList<String> list = new ArrayList<>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        list.add("date");

        Predicate<String> startsWithB = new Predicate<String>() {
            public boolean test(String s) {
                return s.startsWith("b");
            }
        };

        boolean removed = list.removeIf(startsWithB);

        if (!removed) {
            throw new RuntimeException("Expected removeIf to return true when elements are removed");
        }

        if (list.size() != 3) {
            throw new RuntimeException("Expected list size 3 after removal, got " + list.size());
        }

        if (list.contains("banana")) {
            throw new RuntimeException("Expected 'banana' to be removed");
        }
    }

    private static void testRemoveIfReturnsFalseWhenNoMatch() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        Predicate<Integer> greaterThanTen = new Predicate<Integer>() {
            public boolean test(Integer i) {
                return i > 10;
            }
        };

        boolean removed = list.removeIf(greaterThanTen);

        if (removed) {
            throw new RuntimeException("Expected removeIf to return false when no elements removed");
        }

        if (list.size() != 3) {
            throw new RuntimeException("Expected list size 3 when no elements removed, got " + list.size());
        }
    }

    private static void testRemoveIfOnEmptyListReturnsFalse() {
        ArrayList<Object> list = new ArrayList<>();

        Predicate<Object> alwaysTrue = new Predicate<Object>() {
            public boolean test(Object o) {
                return true;
            }
        };

        boolean removed = list.removeIf(alwaysTrue);

        if (removed) {
            throw new RuntimeException("Expected removeIf to return false on empty list");
        }

        if (!list.isEmpty()) {
            throw new RuntimeException("Expected list to remain empty");
        }
    }
}
