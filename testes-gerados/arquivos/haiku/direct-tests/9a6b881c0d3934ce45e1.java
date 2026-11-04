/*
 * @test
 * @summary Verify observable state of Phaser(int parties) through public API
 * @run main PhaserWitupTest
 */

import java.util.concurrent.Phaser;

public class PhaserWitupTest {
    public static void main(String[] args) {
        Phaser p0 = new Phaser(0);
        check(p0.getRegisteredParties() == 0, "zero parties: registered");
        check(p0.getUnarrivedParties() == 0, "zero parties: unarrived");
        check(p0.getPhase() == 0, "zero parties: initial phase");

        Phaser p1 = new Phaser(1);
        check(p1.getRegisteredParties() == 1, "one party: registered");
        check(p1.getUnarrivedParties() == 1, "one party: unarrived before arrival");
        check(p1.getPhase() == 0, "one party: initial phase");
        int arrivalPhase = p1.arrive();
        check(arrivalPhase == 0, "one party: arrival phase should be 0");
        check(p1.getPhase() == 1, "one party: phase should advance after arrival");
        check(p1.getUnarrivedParties() == 1, "one party: unarrived resets for next phase");

        Phaser p3 = new Phaser(3);
        check(p3.getRegisteredParties() == 3, "three parties: registered");
        check(p3.getUnarrivedParties() == 3, "three parties: unarrived initially");
        check(p3.arrive() == 0, "three parties: first arrival phase");
        check(p3.getUnarrivedParties() == 2, "three parties: unarrived after one arrival");
        check(p3.getPhase() == 0, "three parties: phase not advanced yet");
        p3.arrive();
        p3.arrive();
        check(p3.getPhase() == 1, "three parties: phase advances after all arrive");
        check(p3.getUnarrivedParties() == 3, "three parties: unarrived reset after advance");
        check(p3.getRegisteredParties() == 3, "three parties: registered unchanged");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
