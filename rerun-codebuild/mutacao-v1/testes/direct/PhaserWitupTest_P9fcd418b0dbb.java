/* @test    @summary Test bulkRegister with positive, zero, and negative input values    @run main PhaserWitupTest_P9fcd418b0dbb */

import java.util.concurrent.Phaser;

public class PhaserWitupTest_P9fcd418b0dbb {
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
        if (resultPhase != initialPhase) {
            throw new AssertionError("Expected phase " + initialPhase + " but got " + resultPhase);
        }
        int registeredParties = phaser.getRegisteredParties();
        if (registeredParties != partiesToRegister) {
            throw new AssertionError("Expected registered parties " + partiesToRegister + " but got " + registeredParties);
        }
    }

    static void testBulkRegisterZero() {
        Phaser phaser = new Phaser();
        int initialPhase = phaser.getPhase();
        int resultPhase = phaser.bulkRegister(0);
        if (resultPhase != initialPhase) {
            throw new AssertionError("Expected phase " + initialPhase + " but got " + resultPhase);
        }
        int registeredParties = phaser.getRegisteredParties();
        if (registeredParties != 0) {
            throw new AssertionError("Expected registered parties 0 but got " + registeredParties);
        }
    }

    static void testBulkRegisterNegative() {
        Phaser phaser = new Phaser();
        boolean exceptionThrown = false;
        try {
            phaser.bulkRegister(-1);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for negative input");
        }
    }
}
