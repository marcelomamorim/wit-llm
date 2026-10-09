/*
 * @test
 * @summary Test java.util.concurrent.Phaser.bulkRegister method behavior
 * @run main PhaserWitupTest_P3208f29c1620
 */

import java.util.concurrent.Phaser;

public class PhaserWitupTest_P3208f29c1620 {
    public static void main(String[] args) {
        Phaser phaser = new Phaser();

        // Test bulkRegister with zero parties returns current phase (should be 0 initially)
        int phaseBefore = phaser.getPhase();
        int resultZero = phaser.bulkRegister(0);
        if (resultZero != phaseBefore) {
            throw new AssertionError("bulkRegister(0) should return current phase " + phaseBefore + ", but returned " + resultZero);
        }

        // Test bulkRegister with positive parties increases registered parties and returns phase
        int initialPhase = phaser.getPhase();
        int partiesToRegister = 3;
        int resultPositive = phaser.bulkRegister(partiesToRegister);
        if (resultPositive != initialPhase) {
            throw new AssertionError("bulkRegister(positive) should return current phase " + initialPhase + ", but returned " + resultPositive);
        }

        // After registration, arrive that many times to advance phase
        for (int i = 0; i < partiesToRegister; i++) {
            phaser.arrive();
        }

        int phaseAfterArrivals = phaser.getPhase();
        if (phaseAfterArrivals != initialPhase + 1) {
            throw new AssertionError("Phase should have advanced by 1 after all parties arrived. Expected " + (initialPhase + 1) + ", got " + phaseAfterArrivals);
        }

        // Test bulkRegister with negative parties throws IllegalArgumentException
        boolean threw = false;
        try {
            phaser.bulkRegister(-1);
        } catch (IllegalArgumentException iae) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("bulkRegister with negative parties should throw IllegalArgumentException");
        }
    }
}
