/*
 * @test
 * @summary Test constructor Phaser(int parties) for basic behavior
 * @run main PhaserWitupTest
 */

public class PhaserWitupTest {
    public static void main(String[] args) {
        // Test with zero parties
        java.util.concurrent.Phaser ph0 = new java.util.concurrent.Phaser(0);
        if (ph0.getRegisteredParties() != 0) {
            throw new Error("Expected 0 registered parties, got " + ph0.getRegisteredParties());
        }
        if (ph0.getPhase() != 0) {
            throw new Error("Expected initial phase 0, got " + ph0.getPhase());
        }

        // Test with positive parties
        java.util.concurrent.Phaser ph3 = new java.util.concurrent.Phaser(3);
        if (ph3.getRegisteredParties() != 3) {
            throw new Error("Expected 3 registered parties, got " + ph3.getRegisteredParties());
        }
        if (ph3.getPhase() != 0) {
            throw new Error("Expected initial phase 0, got " + ph3.getPhase());
        }

        // Test that arriveAndAwaitAdvance works as expected
        int phaseBefore = ph3.getPhase();
        int arrivalPhase = ph3.arrive();
        if (arrivalPhase != phaseBefore) {
            throw new Error("arrive() should return current phase " + phaseBefore + ", got " + arrivalPhase);
        }

        // Arrive remaining parties to advance phase
        ph3.arrive();
        int phaseAfter = ph3.arriveAndAwaitAdvance();
        if (phaseAfter != phaseBefore + 1) {
            throw new Error("Phase should advance to " + (phaseBefore + 1) + ", got " + phaseAfter);
        }

        // Test with negative parties - expect IllegalArgumentException
        boolean threw = false;
        try {
            new java.util.concurrent.Phaser(-1);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new Error("Expected IllegalArgumentException for negative parties");
        }
    }
}
