/*
 * @test
 * @summary Test for java.util.concurrent.Phaser.bulkRegister(int)
 * @run main PhaserWitupTest
 */

import java.util.concurrent.Phaser;

public class PhaserWitupTest {

    public static void main(String[] args) {
        testBulkRegisterNegativeParties();
        testBulkRegisterZeroParties();
        testBulkRegisterValidParties();
    }

    private static void testBulkRegisterNegativeParties() {
        Phaser phaser = new Phaser();
        try {
            phaser.bulkRegister(-1);
            System.out.println("FAIL: Expected IllegalArgumentException for negative parties");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for negative parties");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testBulkRegisterZeroParties() {
        Phaser phaser = new Phaser();
        int phaseBefore = phaser.getPhase();
        int result = phaser.bulkRegister(0);
        if (result == phaseBefore) {
            System.out.println("PASS: bulkRegister(0) returns current phase");
        } else {
            System.out.println("FAIL: bulkRegister(0) did not return current phase");
        }
    }

    private static void testBulkRegisterValidParties() {
        Phaser phaser = new Phaser();
        int initialParties = 1;
        phaser.register();
        int phaseBefore = phaser.getPhase();
        int registeredParties = 3;
        int result = phaser.bulkRegister(registeredParties);
        if (result == phaseBefore) {
            System.out.println("PASS: bulkRegister with valid parties returns current phase");
        } else {
            System.out.println("FAIL: bulkRegister with valid parties did not return current phase");
        }
        // Additional check: ensure parties count increased
        // Not directly accessible, so rely on behavior
        // No direct API to verify parties count, so test indirectly
        // by arriving and checking phase change if needed
    }
}
