/*
 * @test
 * @summary Test default constructor of Phaser for basic instantiation and no exceptions
 * @run main PhaserWitupTest
 */

import java.util.concurrent.Phaser;

public class PhaserWitupTest {
    public static void main(String[] args) {
        // Test default constructor creates a Phaser with zero parties and no parent
        Phaser phaser = new Phaser();
        // The initial phase should be zero
        int initialPhase = phaser.getPhase();
        if (initialPhase != 0) {
            throw new AssertionError("Initial phase should be 0 but was " + initialPhase);
        }
        // The number of registered parties should be zero
        int parties = phaser.getRegisteredParties();
        if (parties != 0) {
            throw new AssertionError("Initial registered parties should be 0 but was " + parties);
        }
        // Arrive should return current phase without error
        int arrivePhase = phaser.arrive();
        if (arrivePhase != 0) {
            throw new AssertionError("Arrive phase should be 0 but was " + arrivePhase);
        }
        // Arrive and deregister should return current phase without error
        int arriveDeregisterPhase = phaser.arriveAndDeregister();
        if (arriveDeregisterPhase != 0) {
            throw new AssertionError("ArriveAndDeregister phase should be 0 but was " + arriveDeregisterPhase);
        }
    }
}
