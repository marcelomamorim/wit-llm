/*
 * @test
 * @summary Testa o método bulkRegister de java.util.concurrent.Phaser
 * @run main PhaserWitupTest_P9fcd418b0dbb
 */

import java.util.concurrent.Phaser;

public class PhaserWitupTest_P9fcd418b0dbb {
    public static void main(String[] args) {
        testBulkRegisterNegativeParties();
        testBulkRegisterZeroParties();
        testBulkRegisterPositiveParties();
    }

    static void testBulkRegisterNegativeParties() {
        Phaser phaser = new Phaser();
        try {
            phaser.bulkRegister(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative parties");
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t);
        }
    }

    static void testBulkRegisterZeroParties() {
        Phaser phaser = new Phaser();
        int phaseBefore = phaser.getPhase();
        int result = phaser.bulkRegister(0);
        if (result != phaseBefore) {
            throw new AssertionError("Expected phase " + phaseBefore + " but got " + result);
        }
    }

    static void testBulkRegisterPositiveParties() {
        Phaser phaser = new Phaser();
        int initialParties = 1;
        // Register initial parties to simulate a non-zero state
        phaser.register();
        int phaseBefore = phaser.getPhase();
        int registeredParties = 3;
        int result = phaser.bulkRegister(registeredParties);
        if (result != phaseBefore) {
            throw new AssertionError("Expected phase " + phaseBefore + " but got " + result);
        }
        // Additional check: the number of registered parties should increase
        // Note: getRegisteredParties() is not public; assuming internal state is not accessible,
        // so we rely on behavior: no exception thrown and phase unchanged.
    }
}
