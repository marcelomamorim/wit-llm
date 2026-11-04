/*
 * @test
 * @summary Verifies LinkedBlockingDeque.removeFirstOccurrence for null, absent, duplicate, head and empty-deque cases
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testNullReturnsFalseAndLeavesDequeUnchanged();
        testAbsentElementReturnsFalse();
        testEmptyDequeReturnsFalse();
        testRemovesOnlyFirstOccurrence();
        testRemovesHeadElement();
        testEqualsBasedMatching();
        testRemovesLastElementAndSizeUpdates();
    }

    static void testNullReturnsFalseAndLeavesDequeUnchanged() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<String>();
        d.add("a");
        d.add("b");
        check(!d.removeFirstOccurrence(null), "null should return false");
        check(d.size() == 2, "size changed after null removal");
        check(snapshot(d).equals(list("a", "b")), "contents changed after null removal");
    }

    static void testAbsentElementReturnsFalse() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<String>();
        d.add("a");
        d.add("b");
        check(!d.removeFirstOccurrence("z"), "absent element should return false");
        check(snapshot(d).equals(list("a", "b")), "contents changed after absent removal");
    }

    static void testEmptyDequeReturnsFalse() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<String>();
        check(!d.removeFirstOccurrence("a"), "empty deque should return false");
        check(d.isEmpty(), "empty deque should remain empty");
    }

    static void testRemovesOnlyFirstOccurrence() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<String>();
        d.add("a");
        d.add("b");
        d.add("a");
        check(d.removeFirstOccurrence("a"), "should report removal");
        check(snapshot(d).equals(list("b", "a")), "only first occurrence should be removed, got " + snapshot(d));
        check(d.size() == 2, "size should be 2");
    }

    static void testRemovesHeadElement() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<String>();
        d.add("x");
        d.add("y");
        d.add("z");
        check(d.removeFirstOccurrence("x"), "head removal should return true");
        check(snapshot(d).equals(list("y", "z")), "head removal produced wrong order");
        check("y".equals(d.peekFirst()), "new head should be y");
    }

    static void testEqualsBasedMatching() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<String>();
        d.add("key");
        check(d.removeFirstOccurrence(new String("key")), "removal should match by equals");
        check(d.isEmpty(), "deque should be empty after equals-based removal");
    }

    static void testRemovesLastElementAndSizeUpdates() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<String>();
        d.add("only");
        check(d.removeFirstOccurrence("only"), "removal of single element should return true");
        check(d.size() == 0, "size should be 0");
        check(d.peekFirst() == null, "deque should have no head");
        check(d.peekLast() == null, "deque should have no tail");
        check(!d.removeFirstOccurrence("only"), "second removal should return false");
    }

    static List<Object> snapshot(LinkedBlockingDeque<?> d) {
        return new ArrayList<Object>(d);
    }

    static List<Object> list(Object... items) {
        List<Object> l = new ArrayList<Object>();
        for (Object o : items) {
            l.add(o);
        }
        return l;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
