/*
 * @test
 * @summary Test java.util.concurrent.Phaser.register() method behavior
 * @run main PhaserWitupTest
 */

import java.util.concurrent.Phaser;

public class PhaserWitupTest {
    public static void main(String[] args) {
        Phaser phaser = new Phaser();

        // Initial phase should be 0
        int initialPhase = phaser.getPhase();
        if (initialPhase != 0) {
            throw new Error("Initial phase expected to be 0 but was " + initialPhase);
        }

        // Register one party and check returned phase
        int phaseAfterRegister = phaser.register();
        if (phaseAfterRegister != 0) {
            throw new Error("Phase returned by register() expected to be 0 but was " + phaseAfterRegister);
        }

        // After register, getRegisteredParties should be 1
        int parties = phaser.getRegisteredParties();
        if (parties != 1) {
            throw new Error("Registered parties expected to be 1 but was " + parties);
        }

        // Register another party and verify parties count increments
        int phaseAfterSecondRegister = phaser.register();
        if (phaseAfterSecondRegister != 0) {
            throw new Error("Phase returned by second register() expected to be 0 but was " + phaseAfterSecondRegister);
        }

        int partiesAfterSecond = phaser.getRegisteredParties();
        if (partiesAfterSecond != 2) {
            throw new Error("Registered parties expected to be 2 but was " + partiesAfterSecond);
        }

        // Arrive and deregister all parties to test phaser termination
        phaser.arriveAndDeregister();
        phaser.arriveAndDeregister();

        // After deregistration, registered parties should be 0
        int partiesAfterDeregister = phaser.getRegisteredParties();
        if (partiesAfterDeregister != 0) {
            throw new Error("Registered parties expected to be 0 after deregister but was " + partiesAfterDeregister);
        }

        // Phase should have advanced after all parties arrived
        int phaseAfterAdvance = phaser.getPhase();
        if (phaseAfterAdvance != 1) {
            throw new Error("Phase expected to advance to 1 but was " + phaseAfterAdvance);
        }
    }
}
