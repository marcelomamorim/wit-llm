/*
 * @test
 * @summary Checks observable exceptions of AtomicLongFieldUpdater.newUpdater when the field lookup fails
 * @run main AtomicLongFieldUpdaterWitupTest
 */

import java.util.concurrent.atomic.AtomicLongFieldUpdater;

public class AtomicLongFieldUpdaterWitupTest {

    public static class Node {
        public volatile long value;
        public long plain;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        AtomicLongFieldUpdater<Node> updater =
            AtomicLongFieldUpdater.newUpdater(Node.class, "value");
        Node node = new Node();
        updater.set(node, 5L);
        check(updater.get(node) == 5L, "set/get on valid volatile long field failed");
        check(updater.compareAndSet(node, 5L, 7L), "compareAndSet on valid field failed");
        check(node.value == 7L, "compareAndSet did not update the field");

        try {
            AtomicLongFieldUpdater.newUpdater(Node.class, "missing");
            throw new RuntimeException("expected exception for missing field");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("missing field must not be reported as IllegalArgumentException", e);
        } catch (RuntimeException e) {
            check(e.getCause() instanceof NoSuchFieldException,
                  "cause of missing field must be NoSuchFieldException");
        }

        try {
            AtomicLongFieldUpdater.newUpdater(Node.class, "plain");
            throw new RuntimeException("expected IllegalArgumentException for non-volatile field");
        } catch (IllegalArgumentException expected) {
            // expected: field must be volatile long
        }
    }
}
