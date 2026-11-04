/*
 * @test
 * @summary Tests LinkedBlockingDeque.removeLastOccurrence observable behavior
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {
    public static void main(String[] args) {
        testNullArgument();
        testEmptyDeque();
        testRemovesLastOccurrence();
        testAbsentElement();
        testSingleElement();
        testEqualsBasedMatch();
        testBoundedDequeFreesCapacity();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testNullArgument() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        d.addLast("a");
        check(!d.removeLastOccurrence(null), "null argument must return false");
        check(d.size() == 1, "size must be unchanged after null argument");
    }

    static void testEmptyDeque() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        check(!d.removeLastOccurrence("a"), "empty deque must return false");
        check(d.size() == 0, "empty deque must remain empty");
    }

    static void testRemovesLastOccurrence() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        d.addLast("a");
        d.addLast("b");
        d.addLast("a");
        d.addLast("c");
        check(d.removeLastOccurrence("a"), "existing element must be removed");
        check(d.size() == 3, "size must decrease by one");
        check("a".equals(d.pollFirst()), "first element must be the earlier 'a'");
        check("b".equals(d.pollFirst()), "second element must be 'b'");
        check("c".equals(d.pollFirst()), "third element must be 'c'");
        check(d.pollFirst() == null, "deque must now be empty");
    }

    static void testAbsentElement() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        d.addLast("a");
        d.addLast("b");
        check(!d.removeLastOccurrence("z"), "absent element must return false");
        check(d.size() == 2, "size must be unchanged for absent element");
        check(d.contains("a") && d.contains("b"), "elements must be preserved");
    }

    static void testSingleElement() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        d.addLast(Integer.valueOf(1000));
        check(d.removeLastOccurrence(Integer.valueOf(1000)), "single element must be removed");
        check(d.size() == 0, "deque must be empty after removal");
        check(d.peekFirst() == null, "peekFirst must return null");
    }

    static void testEqualsBasedMatch() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        d.addLast(new String("x"));
        check(d.removeLastOccurrence("x"), "removal must use equals, not identity");
        check(d.size() == 0, "deque must be empty after equals-based removal");
    }

    static void testBoundedDequeFreesCapacity() {
        LinkedBlockingDeque<Integer> d = new LinkedBlockingDeque<>(2);
        check(d.offerLast(Integer.valueOf(1)), "first offer must succeed");
        check(d.offerLast(Integer.valueOf(2)), "second offer must succeed");
        check(!d.offerLast(Integer.valueOf(3)), "offer beyond capacity must fail");
        check(d.removeLastOccurrence(Integer.valueOf(2)), "element must be removed");
        check(d.offerLast(Integer.valueOf(3)), "capacity must be freed after removal");
        check(d.size() == 2, "size must be 2 after re-adding");
    }
}
