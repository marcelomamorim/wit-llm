/* @test    @summary Test bulkRegister with positive, zero, and negative input values    @run main PhaserWitupTest */

import java.util.concurrent.Phaser;

public class PhaserWitupTest {

    public static void main(String[] args) {
        testBulkRegisterPositive();
        testBulkRegisterZero();
        testBulkRegisterNegative();
    }

    static void testBulkRegisterPositive() {
        Phaser phaser = new Phaser();
        int initialPhase = phaser.getPhase();
        int partiesToRegister = 3;
        int resultPhase = phaser.bulkRegister(partiesToRegister);
        assert resultPhase == initialPhase : "Expected phase to remain unchanged";
        int registeredParties = phaser.getRegisteredParties();
        assert registeredParties >= partiesToRegister : "Registered parties should be at least the number registered";
    }

    static void testBulkRegisterZero() {
        Phaser phaser = new Phaser();
        int initialPhase = phaser.getPhase();
        int resultPhase = phaser.bulkRegister(0);
        assert resultPhase == initialPhase : "Expected phase to remain unchanged when registering zero";
    }

    static void testBulkRegisterNegative() {
        Phaser phaser = new Phaser();
        boolean exceptionThrown = false;
        try {
            phaser.bulkRegister(-1);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IllegalArgumentException for negative input";
    }
}
