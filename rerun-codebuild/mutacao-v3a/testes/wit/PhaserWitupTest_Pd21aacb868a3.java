/*
 * @test    @summary Test for java.util.concurrent.Phaser.bulkRegister(int)
 * @run main PhaserWitupTest_Pd21aacb868a3
 */

import java.util.concurrent.Phaser;

public class PhaserWitupTest_Pd21aacb868a3 {

    public static void main(String[] args) {
        testBulkRegisterWithNegativeParties();
        testBulkRegisterWithZeroParties();
        testBulkRegisterWithPositiveParties();
    }

    static void testBulkRegisterWithNegativeParties() {
        Phaser phaser = new Phaser();
        try {
            phaser.bulkRegister(-1);
            System.out.println("FAIL: Expected IllegalArgumentException for negative parties");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for negative parties");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }

    static void testBulkRegisterWithZeroParties() {
        Phaser phaser = new Phaser();
        int phaseBefore = phaser.getPhase();
        int result = phaser.bulkRegister(0);
        if (result == phaseBefore) {
            System.out.println("PASS: bulkRegister(0) returns current phase: " + result);
        } else {
            System.out.println("FAIL: bulkRegister(0) returned " + result + ", expected " + phaseBefore);
        }
    }

    static void testBulkRegisterWithPositiveParties() {
        Phaser phaser = new Phaser();
        int initialParties = 2;
        // Register initial parties to avoid errors (though not strictly necessary for this test)
        phaser.bulkRegister(initialParties);
        int currentPhase = phaser.getPhase();
        int result = phaser.bulkRegister(3);
        if (result >= currentPhase) {
            System.out.println("PASS: bulkRegister(3) returned phase: " + result);
        } else {
            System.out.println("FAIL: bulkRegister(3) returned phase: " + result + ", expected >= " + currentPhase);
        }
    }
}
