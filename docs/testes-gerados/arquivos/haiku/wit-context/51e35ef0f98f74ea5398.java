/*
 * @test
 * @summary Witup-derived tests for Phaser(Phaser parent, int parties)
 * @run main PhaserWitupTest
 */

public class PhaserWitupTest {

    public static void main(String[] args) {
        testNegativePartiesRejected();
        testTooManyPartiesRejected();
        testChildWithZeroParties();
        testChildRegistersOneInParent();
        testNullParentCreatesRoot();
        testParentPartyOverflowRejected();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNegativePartiesRejected() {
        Phaser root = new Phaser();
        try {
            new Phaser(root, -1);
            throw new RuntimeException("Expected IllegalArgumentException for negative parties");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        check(root.getRegisteredParties() == 0, "Parent registration changed after rejected child");
    }

    static void testTooManyPartiesRejected() {
        Phaser root = new Phaser();
        try {
            new Phaser(root, 0x10000);
            throw new RuntimeException("Expected IllegalArgumentException for parties above 0xffff");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        check(root.getRegisteredParties() == 0, "Parent registration changed after rejected child");
    }

    static void testChildWithZeroParties() {
        Phaser root = new Phaser();
        Phaser child = new Phaser(root, 0);
        check(child.getParent() == root, "Parent not set");
        check(child.getRoot() == root.getRoot(), "Root not shared");
        check(child.getRegisteredParties() == 0, "Child should have no registered parties");
        check(root.getRegisteredParties() == 0, "Zero-party child must not register in parent");
    }

    static void testChildRegistersOneInParent() {
        Phaser root = new Phaser();
        Phaser child = new Phaser(root, 2);
        check(child.getRegisteredParties() == 2, "Child registered parties mismatch");
        check(root.getRegisteredParties() == 1, "Parent should register exactly one party for the child");
        check(child.getRoot() == root.getRoot(), "Root not shared");
    }

    static void testNullParentCreatesRoot() {
        Phaser p = new Phaser(null, 3);
        check(p.getParent() == null, "Parent should be null");
        check(p.getRoot() == p, "Root should be the phaser itself");
        check(p.getRegisteredParties() == 3, "Registered parties mismatch");
    }

    static void testParentPartyOverflowRejected() {
        Phaser root = new Phaser(0xffff);
        try {
            new Phaser(root, 1);
            throw new RuntimeException("Expected IllegalStateException when parent is full");
        } catch (IllegalStateException expected) {
            // expected
        }
        check(root.getRegisteredParties() == 0xffff, "Parent registration changed after failed child");
    }
}
