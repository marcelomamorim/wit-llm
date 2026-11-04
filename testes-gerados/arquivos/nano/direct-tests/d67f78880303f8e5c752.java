/* @test    @summary Test default constructor of Phaser    @run main PhaserWitupTest */

import java.util.concurrent.Phaser;

public class PhaserWitupTest {

    public static void main(String[] args) {
        testDefaultConstructor();
    }

    public static void testDefaultConstructor() {
        Phaser phaser = new Phaser();
        if (phaser == null) {
            throw new AssertionError("Phaser instance should not be null");
        }
        int parties = phaser.getRegisteredParties();
        if (parties != 0) {
            throw new AssertionError("Expected registered parties to be 0, but was " + parties);
        }
        int phase = phaser.getPhase();
        if (phase != 0) {
            throw new AssertionError("Expected initial phase to be 0, but was " + phase);
        }
        System.out.println("Default constructor test passed.");
    }
}
