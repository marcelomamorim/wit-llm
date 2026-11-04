/*
 * @test
 * @summary Basic observable behavior of AtomicReferenceFieldUpdater.newUpdater and its run() field lookup
 * @run main AtomicReferenceFieldUpdaterWitupTest
 */

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class AtomicReferenceFieldUpdaterWitupTest {

    public static class Node {
        public volatile Object value;
        public volatile String name;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testGetReturnsCurrentValue() {
        Node n = new Node();
        Object initial = new Object();
        n.value = initial;
        AtomicReferenceFieldUpdater<Node, Object> u =
            AtomicReferenceFieldUpdater.newUpdater(Node.class, Object.class, "value");
        check(u.get(n) == initial, "get should return the current field value");
    }

    static void testCompareAndSetSuccess() {
        Node n = new Node();
        Object a = new Object();
        Object b = new Object();
        n.value = a;
        AtomicReferenceFieldUpdater<Node, Object> u =
            AtomicReferenceFieldUpdater.newUpdater(Node.class, Object.class, "value");
        check(u.compareAndSet(n, a, b), "compareAndSet with matching expect should succeed");
        check(n.value == b, "compareAndSet should update the field");
    }

    static void testCompareAndSetFailure() {
        Node n = new Node();
        Object a = new Object();
        Object b = new Object();
        Object other = new Object();
        n.value = a;
        AtomicReferenceFieldUpdater<Node, Object> u =
            AtomicReferenceFieldUpdater.newUpdater(Node.class, Object.class, "value");
        check(!u.compareAndSet(n, other, b), "compareAndSet with non-matching expect should fail");
        check(n.value == a, "failed compareAndSet must not change the field");
    }

    static void testSetThenGet() {
        Node n = new Node();
        Object v = new Object();
        AtomicReferenceFieldUpdater<Node, Object> u =
            AtomicReferenceFieldUpdater.newUpdater(Node.class, Object.class, "value");
        u.set(n, v);
        check(u.get(n) == v, "get after set should return the stored value");
    }

    static void testStringField() {
        Node n = new Node();
        String s = "x";
        n.name = s;
        AtomicReferenceFieldUpdater<Node, String> u =
            AtomicReferenceFieldUpdater.newUpdater(Node.class, String.class, "name");
        check(u.get(n) == s, "updater for String field should read the field");
        check(u.compareAndSet(n, s, "y"), "compareAndSet on String field should succeed");
        check("y".equals(n.name), "String field should be updated");
    }

    static void testMissingFieldThrows() {
        boolean thrown = false;
        try {
            AtomicReferenceFieldUpdater.newUpdater(Node.class, Object.class, "doesNotExist");
        } catch (RuntimeException expected) {
            thrown = true;
        }
        check(thrown, "newUpdater with a nonexistent field name should throw RuntimeException");
    }

    public static void main(String[] args) {
        testGetReturnsCurrentValue();
        testCompareAndSetSuccess();
        testCompareAndSetFailure();
        testSetThenGet();
        testStringField();
        testMissingFieldThrows();
    }
}
