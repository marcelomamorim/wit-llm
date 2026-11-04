/*
 * @test
 * @summary Deterministic tests for java.util.concurrent.Phaser.bulkRegister(int)
 * @run main PhaserWitupTest
 */

import java.util.concurrent.Phaser;

public class PhaserWitupTest {
    public static void main(String[] args) {
        testBulkRegisterNegativeParties();
        testBulkRegisterZeroParties();
        testBulkRegisterNormal();
    }

    private static void testBulkRegisterNegativeParties() {
        Phaser phaser = new Phaser();
        try {
            phaser.bulkRegister(-1);
            throw new Error("Expected IllegalArgumentException for negative parties");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }

    private static void testBulkRegisterZeroParties() {
        Phaser phaser = new Phaser();
        int phase = phaser.bulkRegister(0);
        if (phase != 0) {
            throw new Error("Expected phase 0 when registering zero parties, got " + phase);
        }
    }

    private static void testBulkRegisterNormal() {
        Phaser phaser = new Phaser();
        int initialPhase = phaser.getPhase();
        int partiesToRegister = 3;
        int phaseAfterRegister = phaser.bulkRegister(partiesToRegister);
        if (phaseAfterRegister != initialPhase) {
            throw new Error("Expected phase unchanged after bulkRegister, got " + phaseAfterRegister);
        }
        // Verify that arriveAndAwaitAdvance works with registered parties
        for (int i = 0; i < partiesToRegister; i++) {
            new Thread(new Arriver(phaser)).start();
        }
        // Wait for phase to advance
        int waitedPhase = initialPhase;
        int maxWait = 10000; // 10 seconds max
        long start = System.currentTimeMillis();
        while (phaser.getPhase() == waitedPhase) {
            if (System.currentTimeMillis() - start > maxWait) {
                throw new Error("Phase did not advance after arrivals");
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new Error("Interrupted while waiting for phase advance");
            }
        }
    }

    static class Arriver implements Runnable {
        private final Phaser phaser;

        Arriver(Phaser phaser) {
            this.phaser = phaser;
        }

        public void run() {
            phaser.arriveAndAwaitAdvance();
        }
    }
}
