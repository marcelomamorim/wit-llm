/*
 * @test
 * @summary Tests for java.util.concurrent.Phaser constructor with int parameter
 * @run main PhaserWitupTest
 */

import java.util.concurrent.Phaser;

public class PhaserWitupTest {

    public static void main(String[] args) {
        testValidParties();
        testZeroParties();
        testNegativeParties();
        testLargeParties();
    }

    private static void testValidParties() {
        try {
            Phaser p = new Phaser(5);
            if (p == null) {
                throw new AssertionError("Phaser instance should not be null for valid parties");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid parties: " + e);
        }
    }

    private static void testZeroParties() {
        try {
            new Phaser(0);
            throw new AssertionError("Expected IllegalArgumentException for zero parties");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    private static void testNegativeParties() {
        try {
            new Phaser(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative parties");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    private static void testLargeParties() {
        try {
            Phaser p = new Phaser(1000000);
            if (p == null) {
                throw new AssertionError("Phaser instance should not be null for large number of parties");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for large number of parties: " + e);
        }
    }
}
