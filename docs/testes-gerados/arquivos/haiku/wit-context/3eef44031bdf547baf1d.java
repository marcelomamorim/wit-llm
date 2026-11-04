/*
 * @test
 * @summary WITUP-derived checks for AtomicReferenceFieldUpdater.newUpdater field name handling
 * @run main AtomicReferenceFieldUpdaterWitupTest
 */

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class AtomicReferenceFieldUpdaterWitupTest {

    public static class Node {
        public volatile Object value;
    }

    public static void main(String[] args) {
        testNullFieldNameThrowsNPE();
        testMissingFieldThrowsRuntimeException();
        testValidFieldUpdatesValue();
    }

    static void testNullFieldNameThrowsNPE() {
        try {
            AtomicReferenceFieldUpdater.newUpdater(Node.class, Object.class, null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null field name");
    }

    static void testMissingFieldThrowsRuntimeException() {
        try {
            AtomicReferenceFieldUpdater.newUpdater(Node.class, Object.class, "missingField");
        } catch (NullPointerException e) {
            throw new RuntimeException("unexpected NullPointerException for missing field", e);
        } catch (RuntimeException expected) {
            return;
        }
        throw new RuntimeException("expected RuntimeException for missing field");
    }

    static void testValidFieldUpdatesValue() {
        AtomicReferenceFieldUpdater<Node, Object> updater =
            AtomicReferenceFieldUpdater.newUpdater(Node.class, Object.class, "value");
        Node node = new Node();
        if (!updater.compareAndSet(node, null, "x")) {
            throw new RuntimeException("compareAndSet from null should succeed");
        }
        if (!"x".equals(updater.get(node))) {
            throw new RuntimeException("unexpected value after compareAndSet");
        }
        if (updater.compareAndSet(node, null, "y")) {
            throw new RuntimeException("compareAndSet with stale expected value should fail");
        }
    }
}
