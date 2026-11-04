/*
 * @test
 * @summary Tests observable behavior of Phaser.bulkRegister
 * @run main PhaserWitupTest
 */
import java.util.concurrent.Phaser;

public class PhaserWitupTest {
    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Phaser p = new Phaser();
        check(p.bulkRegister(0) == 0, "bulkRegister(0) should return current phase 0");
        check(p.getRegisteredParties() == 0, "bulkRegister(0) should not register parties");
        check(p.bulkRegister(3) == 0, "bulkRegister(3) should return phase 0");
        check(p.getRegisteredParties() == 3, "expected 3 registered parties");
        check(p.getUnarrivedParties() == 3, "expected 3 unarrived parties");

        Phaser q = new Phaser(1);
        check(q.arrive() == 0, "arrive should return arrival phase 0");
        check(q.getPhase() == 1, "single-party phaser should advance to phase 1");
        check(q.bulkRegister(0) == 1, "bulkRegister(0) should return current phase 1");
        check(q.getRegisteredParties() == 1, "bulkRegister(0) should keep 1 party");
        check(q.bulkRegister(2) == 1, "bulkRegister(2) should return phase 1");
        check(q.getRegisteredParties() == 3, "expected 3 registered parties after bulkRegister(2)");

        boolean thrown = false;
        try {
            p.bulkRegister(-1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "negative parties should throw IllegalArgumentException");
        check(p.getRegisteredParties() == 3, "failed bulkRegister should not change registration");
    }
}
