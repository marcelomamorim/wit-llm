/*
 * @test
 * @summary Test default constructor of Phaser
 * @run main PhaserWitupTest
 */

public class PhaserWitupTest {
    public static void main(String[] args) {
        java.util.concurrent.Phaser phaser = new java.util.concurrent.Phaser();
        // The initial phase should be 0
        int phase = phaser.getPhase();
        if (phase != 0) {
            throw new Error("Initial phase is not zero: " + phase);
        }
        // The number of registered parties should be zero
        int parties = phaser.getRegisteredParties();
        if (parties != 0) {
            throw new Error("Initial registered parties is not zero: " + parties);
        }
        // Arrive should return current phase (0) and not block
        int arrivePhase = phaser.arrive();
        if (arrivePhase != 0) {
            throw new Error("arrive() returned unexpected phase: " + arrivePhase);
        }
        // Arrive and deregister should return current phase (0) and reduce parties
        int arriveDeregisterPhase = phaser.arriveAndDeregister();
        if (arriveDeregisterPhase != 0) {
            throw new Error("arriveAndDeregister() returned unexpected phase: " + arriveDeregisterPhase);
        }
        // Register a party and check parties count
        int partiesAfterRegister = phaser.register();
        if (phaser.getRegisteredParties() != 1) {
            throw new Error("Registered parties after register() is not 1");
        }
        // Bulk register 2 parties
        int partiesAfterBulk = phaser.bulkRegister(2);
        if (phaser.getRegisteredParties() != 3) {
            throw new Error("Registered parties after bulkRegister(2) is not 3");
        }
        // Arrive and await advance should return phase 0 and advance phase to 1
        int phaseBefore = phaser.getPhase();
        int arrivedPhase = phaser.arriveAndAwaitAdvance();
        int phaseAfter = phaser.getPhase();
        if (arrivedPhase != phaseBefore) {
            throw new Error("arriveAndAwaitAdvance() returned unexpected phase");
        }
        if (phaseAfter != phaseBefore + 1) {
            throw new Error("Phase did not advance after arriveAndAwaitAdvance");
        }
    }
}
