/*
 * @test
 * @summary Functional tests for ArrayList.removeIf(Predicate)
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testRemovesMatchingElements();
        testNoMatchReturnsFalse();
        testRemoveAllMatching();
        testNullElements();
        testEmptyList();
        testListUsableAfterRemoval();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemovesMatchingElements() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        Predicate<Integer> odd = x -> x % 2 != 0;
        boolean changed = list.removeIf(odd);
        check(changed, "removeIf should return true when elements are removed");
        check(list.equals(Arrays.asList(0, 2, 4, 6, 8)),
              "remaining elements must keep their order: " + list);
        check(list.size() == 5, "size must be 5 after removing odd values");
    }

    static void testNoMatchReturnsFalse() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(2, 4, 6));
        boolean changed = list.removeIf(x -> x < 0);
        check(!changed, "removeIf should return false when nothing matches");
        check(list.equals(Arrays.asList(2, 4, 6)), "list must be unchanged: " + list);
    }

    static void testRemoveAllMatching() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        boolean changed = list.removeIf(x -> true);
        check(changed, "removeIf should return true when all elements match");
        check(list.isEmpty(), "list must be empty after removing all elements");
        check(list.size() == 0, "size must be 0");
    }

    static void testNullElements() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", null, "b", null));
        boolean changed = list.removeIf(s -> s == null);
        check(changed, "removeIf should remove null elements");
        check(list.equals(Arrays.asList("a", "b")), "only non-null elements must remain: " + list);
    }

    static void testEmptyList() {
        ArrayList<String> list = new ArrayList<>();
        boolean changed = list.removeIf(s -> true);
        check(!changed, "removeIf on an empty list must return false");
        check(list.isEmpty(), "empty list must remain empty");
    }

    static void testListUsableAfterRemoval() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        list.removeIf(x -> x > 2);
        list.add(10);
        check(list.equals(Arrays.asList(1, 2, 10)), "list must accept additions after removeIf: " + list);
        List<Integer> expected = Arrays.asList(1, 2, 10);
        check(list.get(2).equals(expected.get(2)), "element at index 2 must be 10");
    }
}
