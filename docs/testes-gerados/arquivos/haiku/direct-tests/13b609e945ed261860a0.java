/*
 * @test
 * @summary Behavioral tests for Phaser.register() using only public API
 * @run main PhaserWitupTest
 */

import java.util.concurrent.Phaser;

public class PhaserWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static void testRegisterOnEmptyPhaserReturnsPhaseZero() {
        Phaser p = new Phaser();
        int phase = p.register();
        check(phase == 0, "register() on new Phaser() should return phase 0, got " + phase);
        check(p.getRegisteredParties() == 1, "registered parties should be 1 after register(), got " + p.getRegisteredParties());
    }

    static void testRegisterIncrementsExistingParties() {
        Phaser p = new Phaser(2);
        int phase = p.register();
        check(phase == 0, "register() on Phaser(2) in phase 0 should return 0, got " + phase);
        check(p.getRegisteredParties() == 3, "registered parties should be 3, got " + p.getRegisteredParties());
    }

    static void testRegisterAfterPhaseAdvanceReturnsCurrentPhase() {
        Phaser p = new Phaser(1);
        int arrival = p.arrive();
        check(arrival == 0, "arrive() should return arrival phase 0, got " + arrival);
        check(p.getPhase() == 1, "phase should advance to 1 after sole party arrives, got " + p.getPhase());
        int phase = p.register();
        check(phase == 1, "register() should return current phase 1, got " + phase);
        check(p.getRegisteredParties() == 2, "registered parties should be 2, got " + p.getRegisteredParties());
    }

    static void testRegisterOnTerminatedPhaserReturnsNegative() {
        Phaser p = new Phaser(1);
        p.arriveAndDeregister();
        check(p.isTerminated(), "phaser should be terminated after last party deregisters");
        int phase = p.register();
        check(phase < 0, "register() on terminated phaser should return negative value, got " + phase);
    }

    public static void main(String[] args) {
        testRegisterOnEmptyPhaserReturnsPhaseZero();
        testRegisterIncrementsExistingParties();
        testRegisterAfterPhaseAdvanceReturnsCurrentPhase();
        testRegisterOnTerminatedPhaserReturnsNegative();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
