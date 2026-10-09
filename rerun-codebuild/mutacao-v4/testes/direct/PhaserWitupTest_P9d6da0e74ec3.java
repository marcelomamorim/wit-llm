/* @test
   @summary Tests bulkRegister method of Phaser for various input scenarios.
   @run main PhaserWitupTest_P9d6da0e74ec3
 */
import java.util.concurrent.Phaser;
public class PhaserWitupTest_P9d6da0e74ec3 {
    public static void main(String[] args) {
        testBulkRegisterNegative();
        testBulkRegisterZero();
        testBulkRegisterPositive();
    }
    static void testBulkRegisterNegative() {
        Phaser phaser = new Phaser();
        try {
            phaser.bulkRegister(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative input");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
    static void testBulkRegisterZero() {
        Phaser phaser = new Phaser();
        int phaseBefore = phaser.getPhase();
        int phaseAfter = phaser.bulkRegister(0);
        if (phaseBefore != phaseAfter) {
            throw new AssertionError("Phase should not change when registering zero parties");
        }
    }
    static void testBulkRegisterPositive() {
        Phaser phaser = new Phaser();
        int initialPhase = phaser.getPhase();
        int partiesToRegister = 3;
        int returnedPhase = phaser.bulkRegister(partiesToRegister);
        if (returnedPhase != initialPhase) {
            throw new AssertionError("Returned phase should match initial phase");
        }
        // Verify that the number of registered parties increased
        int registeredParties = phaser.getRegisteredParties();
        if (registeredParties != partiesToRegister) {
            throw new AssertionError("Registered parties should equal the number registered");
        }
    }
}
