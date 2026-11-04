/*
 * @test
 * @summary LinkedBlockingDeque.remove(Object) removes the first occurrence and returns false for null or absent elements
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testRemoveFirstOccurrence();
        testRemoveNullReturnsFalse();
        testRemoveAbsentReturnsFalse();
        testRemoveOnBoundedDeque();
        testRemoveFromCollectionConstructor();
    }

    static void testRemoveFirstOccurrence() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        d.add("a");
        d.add("b");
        d.add("a");
        check(d.remove("a"), "remove(a) should return true when present");
        check(d.size() == 2, "size should be 2 after removing one occurrence");
        check("b".equals(d.peekFirst()), "first element should be b");
        check(d.contains("a"), "second occurrence of a should remain");
        check(d.remove("a"), "second remove(a) should return true");
        check(!d.contains("a"), "no a should remain");
        check(d.size() == 1, "size should be 1");
    }

    static void testRemoveNullReturnsFalse() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        d.add("x");
        check(!d.remove(null), "remove(null) should return false");
        check(d.size() == 1, "deque should be unchanged after remove(null)");
        check("x".equals(d.peekFirst()), "element x should be unchanged");
    }

    static void testRemoveAbsentReturnsFalse() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        check(!d.remove("missing"), "remove on empty deque should return false");
        d.add("p");
        check(!d.remove("q"), "remove of absent element should return false");
        check(d.size() == 1, "size should remain 1");
    }

    static void testRemoveOnBoundedDeque() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>(2);
        d.add(Integer.valueOf(1));
        d.add(Integer.valueOf(2));
        check(d.remove(Integer.valueOf(1)), "remove(1) should return true on bounded deque");
        check(d.size() == 1, "size should be 1");
        check(d.remainingCapacity() == 1, "remaining capacity should be 1");
    }

    static void testRemoveFromCollectionConstructor() {
        Collection<String> src = Arrays.asList("m", "n", "m");
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>(src);
        check(d.remove("m"), "remove(m) should return true");
        check(d.size() == 2, "size should be 2");
        check("n".equals(d.peekFirst()), "first element should be n");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
