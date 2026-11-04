/*
 * @test
 * @summary Behavioral checks for public ArrayList.removeIf(Predicate)
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ArrayListWitupTest {
    public static void main(String[] args) {
        testRemoveIfRemovesMatchingElements();
        testRemoveIfReturnsFalseWhenNoMatch();
        testRemoveIfOnEmptyList();
        testRemoveIfRemovesNullElements();
        testRemoveIfNullFilterOnNonEmptyListThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemoveIfRemovesMatchingElements() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        Predicate<Integer> even = x -> x % 2 == 0;
        boolean modified = list.removeIf(even);
        check(modified, "removeIf should return true when elements are removed");
        check(list.size() == 5, "expected 5 odd elements, got " + list.size());
        for (Integer x : list) {
            check(x % 2 == 1, "even element remained: " + x);
        }
    }

    static void testRemoveIfReturnsFalseWhenNoMatch() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        boolean modified = list.removeIf(s -> s.equals("z"));
        check(!modified, "removeIf should return false when nothing matches");
        check(list.size() == 2, "list size should be unchanged");
        check(list.get(0).equals("a") && list.get(1).equals("b"), "list contents should be unchanged");
    }

    static void testRemoveIfOnEmptyList() {
        List<String> list = new ArrayList<>();
        boolean modified = list.removeIf(s -> true);
        check(!modified, "removeIf on empty list should return false");
        check(list.isEmpty(), "empty list should remain empty");
    }

    static void testRemoveIfRemovesNullElements() {
        List<String> list = new ArrayList<>();
        list.add(null);
        list.add("x");
        list.add(null);
        boolean modified = list.removeIf(s -> s == null);
        check(modified, "removeIf should remove null elements");
        check(list.size() == 1, "expected one remaining element, got " + list.size());
        check("x".equals(list.get(0)), "remaining element should be x");
    }

    static void testRemoveIfNullFilterOnNonEmptyListThrowsNPE() {
        List<String> list = new ArrayList<>();
        list.add("a");
        try {
            list.removeIf(null);
            throw new RuntimeException("expected NullPointerException for null filter");
        } catch (NullPointerException expected) {
            check(list.size() == 1, "list should be unchanged after NPE");
        }
    }
}
