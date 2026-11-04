/*
 * @test
 * @summary Black-box checks for ConcurrentLinkedDeque.remove(Object), which delegates to removeFirstOccurrence
 * @run main ConcurrentLinkedDequeWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ConcurrentLinkedDequeWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testEmptyDequeReturnsFalse() {
        ConcurrentLinkedDeque<String> d = new ConcurrentLinkedDeque<>();
        check(!d.remove("a"), "remove on empty deque must return false");
        check(d.isEmpty(), "deque must remain empty");
    }

    static void testRemovesOnlyFirstOccurrence() {
        ConcurrentLinkedDeque<String> d = new ConcurrentLinkedDeque<>();
        d.add("a");
        d.add("b");
        d.add("a");
        check(d.remove("a"), "remove of present element must return true");
        check(d.size() == 2, "size must decrease by one, was " + d.size());
        check("b".equals(d.peekFirst()), "first element must be b");
        check("a".equals(d.peekLast()), "second occurrence of a must remain");
        check(d.contains("a"), "second occurrence of a must still be contained");
    }

    static void testAbsentElementReturnsFalseAndKeepsContent() {
        ConcurrentLinkedDeque<String> d = new ConcurrentLinkedDeque<>(Arrays.asList("x", "y"));
        check(!d.remove("z"), "remove of absent element must return false");
        check(d.size() == 2, "size must be unchanged");
        check("x".equals(d.peekFirst()) && "y".equals(d.peekLast()), "content must be unchanged");
    }

    static void testUsesEquals() {
        ConcurrentLinkedDeque<String> d = new ConcurrentLinkedDeque<>();
        d.add(new String("key"));
        check(d.remove("key"), "remove must match by equals, not identity");
        check(d.isEmpty(), "deque must be empty after removing the only element");
    }

    static void testRemoveSingleElementThenEmpty() {
        ConcurrentLinkedDeque<Integer> d = new ConcurrentLinkedDeque<>();
        d.add(Integer.valueOf(7));
        check(d.remove(Integer.valueOf(7)), "remove of sole element must return true");
        check(d.isEmpty(), "deque must be empty");
        check(!d.remove(Integer.valueOf(7)), "second remove must return false");
    }

    static void testRemoveNullReturnsFalse() {
        ConcurrentLinkedDeque<String> d = new ConcurrentLinkedDeque<>(Arrays.asList("p"));
        check(!d.remove(null), "remove(null) must return false");
        check(d.size() == 1 && "p".equals(d.peekFirst()), "content must be unchanged after remove(null)");
    }

    static void testRemoveFromCopyConstructedDeque() {
        List<String> src = new ArrayList<>();
        src.add("m");
        src.add("n");
        ConcurrentLinkedDeque<String> d = new ConcurrentLinkedDeque<>(src);
        check(d.remove("n"), "remove must succeed on copy-constructed deque");
        check(d.size() == 1 && "m".equals(d.peekFirst()), "remaining content must be [m]");
    }

    public static void main(String[] args) {
        testEmptyDequeReturnsFalse();
        testRemovesOnlyFirstOccurrence();
        testAbsentElementReturnsFalseAndKeepsContent();
        testUsesEquals();
        testRemoveSingleElementThenEmpty();
        testRemoveNullReturnsFalse();
        testRemoveFromCopyConstructedDeque();
    }
}
