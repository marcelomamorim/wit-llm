/*
 * @test
 * @summary Verify observable state produced by the no-arg Phaser() constructor
 * @run main PhaserWitupTest
 */

import java.util.concurrent.Phaser;

public class PhaserWitupTest {
    public static void main(String[] args) {
        Phaser p = new Phaser();
        check(p.getRegisteredParties() == 0, "initial registered parties must be 0");
        check(p.getArrivedParties() == 0, "initial arrived parties must be 0");
        check(p.getUnarrivedParties() == 0, "initial unarrived parties must be 0");
        check(p.getPhase() == 0, "initial phase must be 0");
        check(!p.isTerminated(), "new phaser must not be terminated");

        int phase = p.register();
        check(phase == 0, "register() on initial phaser must return phase 0");
        check(p.getRegisteredParties() == 1, "registered parties must be 1 after register()");
        check(p.getUnarrivedParties() == 1, "unarrived parties must be 1 after register()");

        int arrivalPhase = p.arrive();
        check(arrivalPhase == 0, "arrive() must return the arrival phase 0");
        check(p.getPhase() == 1, "phase must advance to 1 when the only party arrives");
        check(p.getRegisteredParties() == 1, "registered parties must remain 1 after advance");
        check(p.getUnarrivedParties() == 1, "unarrived parties must be reset to 1 after advance");
        check(!p.isTerminated(), "phaser must not terminate while a party is registered");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
