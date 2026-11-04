/*
 * @test
 * @summary LinkedBlockingDeque.remove(Object) removes the first occurrence, returns a boolean, and handles absent, null, empty and bounded deques
 * @run main LinkedBlockingDequeWitupTest
 */

import java.util.concurrent.LinkedBlockingDeque;

public class LinkedBlockingDequeWitupTest {

    public static void main(String[] args) {
        testRemovePresentElement();
        testRemoveAbsentElement();
        testRemoveOnlyFirstOccurrence();
        testRemoveNullReturnsFalse();
        testRemoveOnEmptyDeque();
        testRemoveFreesCapacityInBoundedDeque();
        testRemoveUsesEquals();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testRemovePresentElement() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        d.add("a");
        d.add("b");
        d.add("c");
        check(d.remove("b"), "remove of present element should return true");
        check(d.size() == 2, "size should decrease by one");
        check(!d.contains("b"), "removed element should no longer be contained");
        check("a".equals(d.peekFirst()), "head should be unchanged");
        check("c".equals(d.peekLast()), "tail should be unchanged");
    }

    static void testRemoveAbsentElement() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        d.add("a");
        d.add("b");
        check(!d.remove("z"), "remove of absent element should return false");
        check(d.size() == 2, "size should be unchanged when element is absent");
        check("a".equals(d.peekFirst()) && "b".equals(d.peekLast()), "contents should be unchanged");
    }

    static void testRemoveOnlyFirstOccurrence() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        d.add("a");
        d.add("b");
        d.add("a");
        check(d.remove("a"), "remove should return true for duplicate element");
        check(d.size() == 2, "only one occurrence should be removed");
        check("b".equals(d.peekFirst()), "first remaining element should be b");
        check("a".equals(d.peekLast()), "second occurrence of a should remain at tail");
        check(d.remove("a"), "second occurrence should still be removable");
        check(!d.remove("a"), "no more occurrences should remain");
    }

    static void testRemoveNullReturnsFalse() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        d.add("a");
        check(!d.remove(null), "remove(null) should return false");
        check(d.size() == 1, "size should be unchanged after remove(null)");
        check("a".equals(d.peekFirst()), "contents should be unchanged after remove(null)");
    }

    static void testRemoveOnEmptyDeque() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        check(!d.remove("a"), "remove on empty deque should return false");
        check(d.isEmpty(), "deque should remain empty");
    }

    static void testRemoveFreesCapacityInBoundedDeque() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>(1);
        check(d.offer("a"), "offer into empty bounded deque should succeed");
        check(!d.offer("b"), "offer into full bounded deque should fail");
        check(d.remove("a"), "remove of present element should return true");
        check(d.offer("b"), "removal should free capacity for a new element");
        check("b".equals(d.peekFirst()), "new element should be present");
    }

    static void testRemoveUsesEquals() {
        LinkedBlockingDeque<String> d = new LinkedBlockingDeque<>();
        d.add(new String("x"));
        check(d.remove("x"), "remove should match by equals, not identity");
        check(d.isEmpty(), "deque should be empty after removing equal element");
    }
}
