/*
 * @test
 * @summary Witup-derived behavioral checks for Phaser.bulkRegister(int)
 * @run main PhaserWitupTest
 */

public class PhaserWitupTest {

    public static void main(String[] args) {
        testNegativePartiesThrowsIllegalArgumentException();
        testZeroPartiesReturnsCurrentPhase();
        testPositivePartiesReturnsPhaseAndRegisters();
        testRegistrationBeyondMaxPartiesThrowsIllegalStateException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNegativePartiesThrowsIllegalArgumentException() {
        Phaser phaser = new Phaser();
        try {
            phaser.bulkRegister(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative parties");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        check(phaser.getRegisteredParties() == 0,
              "Registered parties must not change after rejected negative registration");
    }

    static void testZeroPartiesReturnsCurrentPhase() {
        Phaser phaser = new Phaser(1);
        int arrivalPhase = phaser.arrive();
        check(arrivalPhase == 0, "Arrival phase of first arrive() should be 0");
        int currentPhase = phaser.getPhase();
        check(currentPhase == 1, "Phase should advance to 1 after the only party arrives");
        int result = phaser.bulkRegister(0);
        check(result == currentPhase,
              "bulkRegister(0) should return the current phase, expected " + currentPhase + " but was " + result);
    }

    static void testPositivePartiesReturnsPhaseAndRegisters() {
        Phaser phaser = new Phaser();
        int phase = phaser.bulkRegister(3);
        check(phase == 0, "bulkRegister on a new phaser should return phase 0, but was " + phase);
        check(phaser.getRegisteredParties() == 3,
              "Registered parties should be 3, but was " + phaser.getRegisteredParties());
        check(phaser.getUnarrivedParties() == 3,
              "Unarrived parties should be 3, but was " + phaser.getUnarrivedParties());
    }

    static void testRegistrationBeyondMaxPartiesThrowsIllegalStateException() {
        Phaser phaser = new Phaser();
        try {
            phaser.bulkRegister(65536);
            throw new RuntimeException("Expected IllegalStateException when exceeding 65535 parties");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}
