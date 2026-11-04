/*
 * @test
 * @summary Observable state of a Phaser created with the public no-arg constructor
 * @run main PhaserWitupTest
 */

import java.util.concurrent.Phaser;

public class PhaserWitupTest {
    public static void main(String[] args) {
        Phaser p = new Phaser();
        check(p.getRegisteredParties() == 0, "initial registered parties should be 0");
        check(p.getUnarrivedParties() == 0, "initial unarrived parties should be 0");
        check(p.getArrivedParties() == 0, "initial arrived parties should be 0");
        check(p.getPhase() == 0, "initial phase should be 0");
        check(!p.isTerminated(), "new phaser should not be terminated");

        check(p.register() == 0, "register should return phase 0");
        check(p.getRegisteredParties() == 1, "registered parties should be 1 after register");
        check(p.getUnarrivedParties() == 1, "unarrived parties should be 1 after register");

        check(p.arrive() == 0, "arrive should return arrival phase 0");
        check(p.getPhase() == 1, "phase should advance to 1 when the only party arrives");
        check(p.getRegisteredParties() == 1, "registered parties should remain 1 after advance");
        check(p.getUnarrivedParties() == 1, "unarrived parties should reset to 1 after advance");
        check(!p.isTerminated(), "phaser should not be terminated after normal advance");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
