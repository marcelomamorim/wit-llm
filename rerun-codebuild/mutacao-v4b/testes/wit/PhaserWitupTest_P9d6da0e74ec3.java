/* @test
   @summary Testa o método bulkRegister de java.util.concurrent.Phaser.
   @run main PhaserWitupTest_P9d6da0e74ec3
 */
import java.util.concurrent.Phaser;
public class PhaserWitupTest_P9d6da0e74ec3 {
    public static void main(String[] args) {
        testBulkRegisterNegativeParties();
        testBulkRegisterZeroParties();
        testBulkRegisterPositiveParties();
    }

    private static void testBulkRegisterNegativeParties() {
        Phaser phaser = new Phaser();
        try {
            phaser.bulkRegister(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative parties");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testBulkRegisterZeroParties() {
        Phaser phaser = new Phaser();
        int phaseBefore = phaser.getPhase();
        int phaseAfter = phaser.bulkRegister(0);
        if (phaseBefore != phaseAfter) {
            throw new AssertionError("Phase should not change when registering zero parties");
        }
    }

    private static void testBulkRegisterPositiveParties() {
        Phaser phaser = new Phaser();
        int initialPhase = phaser.getPhase();
        int registeredParties = 3;
        int resultPhase = phaser.bulkRegister(registeredParties);
        if (resultPhase != initialPhase) {
            throw new AssertionError("Phase should not change after registration");
        }
        // Additional check: register more parties and verify phase remains
        int newPhase = phaser.getPhase();
        if (newPhase != initialPhase) {
            throw new AssertionError("Phase should remain unchanged after bulkRegister");
        }
    }
}
