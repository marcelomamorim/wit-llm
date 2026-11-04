/*
 * @test
 * @summary Witup-derived checks for AtomicIntegerFieldUpdater.newUpdater and the reflective field lookup performed by run()
 * @run main AtomicIntegerFieldUpdaterWitupTest
 */

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

    static void testBasicOperations() {
        AtomicIntegerFieldUpdater<Node> updater = AtomicIntegerFieldUpdater.newUpdater(Node.class, "count");
        Node node = new Node();
        check(updater.get(node) == 0, "initial value must be 0");
        check(updater.compareAndSet(node, 0, 5), "CAS from expected value must succeed");
        check(node.count == 5, "field must reflect CAS result");
        check(!updater.compareAndSet(node, 0, 9), "CAS with stale expected value must fail");
        check(node.count == 5, "failed CAS must not change field");
        check(updater.incrementAndGet(node) == 6, "incrementAndGet must return new value");
        check(updater.getAndAdd(node, 4) == 6, "getAndAdd must return previous value");
        check(node.count == 10, "getAndAdd must update field");
        updater.set(node, -3);
        check(updater.get(node) == -3, "set must be observable through get");
    }

    static void testNonVolatileFieldRejected() {
        expectIllegalArgument(Node.class, "plain");
    }

    static void testNonIntVolatileFieldRejected() {
        expectIllegalArgument(Node.class, "wide");
    }

    static void expectIllegalArgument(Class<Node> c, String field) {
        boolean thrown = false;
        try {
            AtomicIntegerFieldUpdater.newUpdater(c, field);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalArgumentException for field " + field);
    }

    static void testMissingFieldWrapsReflectiveException() {
        boolean ok = false;
        try {
            AtomicIntegerFieldUpdater.newUpdater(Node.class, "missing");
        } catch (RuntimeException e) {
            ok = e.getCause() instanceof NoSuchFieldException;
        }
        check(ok, "missing field must raise RuntimeException caused by NoSuchFieldException");
    }

    static void testNullFieldNameFails() {
        boolean thrown = false;
        try {
            AtomicIntegerFieldUpdater.newUpdater(Node.class, null);
        } catch (RuntimeException e) {
            thrown = true;
        }
        check(thrown, "null fieldName must not be accepted");
    }

    public static void main(String[] args) throws Exception {
        testBasicOperations();
        testNonVolatileFieldRejected();
        testNonIntVolatileFieldRejected();
        testMissingFieldWrapsReflectiveException();
        testNullFieldNameFails();
    }
}
