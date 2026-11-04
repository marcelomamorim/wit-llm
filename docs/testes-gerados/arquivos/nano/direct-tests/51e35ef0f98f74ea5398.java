/*
 * @test
 * @summary Tests the constructor Phaser(Phaser, int) for correct behavior and exception handling.
 * @run main PhaserWitupTest
 */

import java.util.concurrent.Phaser;

public class PhaserWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidArguments();
        testConstructorWithInvalidParties();
        testConstructorWithNullParent();
    }

    static void testConstructorWithValidArguments() {
        Phaser parent = new Phaser();
        int parties = 3;
        Phaser phaser = new Phaser(parent, parties);
        if (phaser == null) {
            throw new AssertionError("Phaser instance should not be null");
        }
    }

    static void testConstructorWithInvalidParties() {
        Phaser parent = new Phaser();
        int invalidParties = 1 << 30; // large value to simulate invalid input
        try {
            new Phaser(parent, invalidParties);
            throw new AssertionError("Expected IllegalArgumentException for invalid parties");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testConstructorWithNullParent() {
        int parties = 2;
        Phaser phaser = new Phaser(null, parties);
        if (phaser == null) {
            throw new AssertionError("Phaser instance should not be null when parent is null");
        }
    }
}
