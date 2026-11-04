/* @test    @summary Test constructor Phaser(int) with valid input    @run main PhaserWitupTest */

import java.util.concurrent.Phaser;

public class PhaserWitupTest {
    public static void main(String[] args) {
        testConstructorWithValidParties();
        testConstructorWithZeroParties();
        testConstructorWithNegativeParties();
    }

    static void testConstructorWithValidParties() {
        int parties = 5;
        Phaser phaser = new Phaser(parties);
        int registeredParties = phaser.getRegisteredParties();
        if (registeredParties != parties) {
            throw new AssertionError("Expected registered parties: " + parties + ", but got: " + registeredParties);
        }
    }

    static void testConstructorWithZeroParties() {
        int parties = 0;
        Phaser phaser = new Phaser(parties);
        int registeredParties = phaser.getRegisteredParties();
        if (registeredParties != parties) {
            throw new AssertionError("Expected registered parties: 0, but got: " + registeredParties);
        }
    }

    static void testConstructorWithNegativeParties() {
        int parties = -1;
        try {
            new Phaser(parties);
            throw new AssertionError("Expected IllegalArgumentException for negative parties");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
