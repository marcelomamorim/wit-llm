/*
 * @test
 * @summary Test removeIf method of CopyOnWriteArrayList for correct removal of elements based on predicate.
 * @run main CopyOnWriteArrayListWitupTest
 */

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

public class CopyOnWriteArrayListWitupTest {

    public static void main(String[] args) {
        testRemoveIfRemovesMatchingElements();
        testRemoveIfNoElementsMatch();
        testRemoveIfAllElementsMatch();
        testRemoveIfEmptyList();
    }

    static void testRemoveIfRemovesMatchingElements() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        Predicate<Integer> isEven = x -> x % 2 == 0;
        boolean result = list.removeIf(isEven);
        assert result : "Expected removeIf to return true when elements are removed";
        assert list.size() == 2 : "Expected list size 2 after removal";
        assert list.contains(1) : "List should contain 1";
        assert list.contains(3) : "List should contain 3";
        assert !list.contains(2) : "List should not contain 2";
        assert !list.contains(4) : "List should not contain 4";
    }

    static void testRemoveIfNoElementsMatch() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        Predicate<String> startsWithZ = s -> s.startsWith("z");
        boolean result = list.removeIf(startsWithZ);
        assert !result : "Expected removeIf to return false when no elements are removed";
        assert list.size() == 3 : "List size should remain 3";
        assert list.contains("a") : "List should contain 'a'";
        assert list.contains("b") : "List should contain 'b'";
        assert list.contains("c") : "List should contain 'c'";
    }

    static void testRemoveIfAllElementsMatch() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        list.add(2);
        list.add(4);
        list.add(6);
        Predicate<Integer> isEven = x -> x % 2 == 0;
        boolean result = list.removeIf(isEven);
        assert result : "Expected removeIf to return true when all elements are removed";
        assert list.isEmpty() : "List should be empty after removal";
    }

    static void testRemoveIfEmptyList() {
        CopyOnWriteArrayList<Object> list = new CopyOnWriteArrayList<>();
        Predicate<Object> alwaysTrue = o -> true;
        boolean result = list.removeIf(alwaysTrue);
        assert !result : "Expected removeIf to return false on empty list";
        assert list.isEmpty() : "List should remain empty";
    }
}
