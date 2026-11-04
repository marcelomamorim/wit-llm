/*
 * @test
 * @summary Checks AtomicIntegerFieldUpdater.newUpdater and the field lookup performed by its privileged action
 * @run main AtomicIntegerFieldUpdaterWitupTest
 */

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdaterWitupTest {

    public static class Node {
        public volatile int count;
        public int plain;
        public volatile long wide;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        AtomicIntegerFieldUpdater<Node> updater =
                AtomicIntegerFieldUpdater.newUpdater(Node.class, "count");
        Node node = new Node();

        check(updater.get(node) == 0, "initial value should be 0");

        updater.set(node, 5);
        check(updater.get(node) == 5, "set/get should observe 5");
        check(node.count == 5, "field should reflect set value");

        check(updater.compareAndSet(node, 5, 7), "CAS with matching expected value should succeed");
        check(node.count == 7, "field should be 7 after successful CAS");

        check(!updater.compareAndSet(node, 1, 2), "CAS with mismatched expected value should fail");
        check(node.count == 7, "field should remain 7 after failed CAS");

        check(updater.incrementAndGet(node) == 8, "incrementAndGet should return 8");
        check(node.count == 8, "field should be 8 after increment");

        try {
            AtomicIntegerFieldUpdater.newUpdater(Node.class, "plain");
            throw new RuntimeException("non-volatile field should be rejected");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        try {
            AtomicIntegerFieldUpdater.newUpdater(Node.class, "wide");
            throw new RuntimeException("volatile long field should be rejected");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        try {
            AtomicIntegerFieldUpdater.newUpdater(Node.class, "missing");
            throw new RuntimeException("missing field should be rejected");
        } catch (RuntimeException expected) {
            // expected: nested reflection-based failure from getDeclaredField
        }
    }
}
