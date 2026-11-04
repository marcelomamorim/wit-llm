/*
 * @test
 * @summary Verify Phaser.register() return value, registration effects, overflow behavior and behavior on a terminated phaser
 * @run main PhaserWitupTest
 */

import java.util.concurrent.Phaser;

public class PhaserWitupTest {
    public static void main(String[] args) {
        Phaser p = new Phaser();
        int phase = p.register();
        if (phase != 0) {
            throw new RuntimeException("expected arrival phase 0 but got " + phase);
        }
        if (p.getRegisteredParties() != 1) {
            throw new RuntimeException("expected 1 registered party but got " + p.getRegisteredParties());
        }
        if (p.getUnarrivedParties() != 1) {
            throw new RuntimeException("expected 1 unarrived party but got " + p.getUnarrivedParties());
        }
        p.register();
        if (p.getRegisteredParties() != 2) {
            throw new RuntimeException("expected 2 registered parties but got " + p.getRegisteredParties());
        }

        Phaser q = new Phaser();
        q.bulkRegister(0xffff);
        if (q.getRegisteredParties() != 0xffff) {
            throw new RuntimeException("expected 65535 registered parties but got " + q.getRegisteredParties());
        }
        boolean thrown = false;
        try {
            q.register();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalStateException when registering beyond 65535 parties");
        }
        if (q.getRegisteredParties() != 0xffff) {
            throw new RuntimeException("registered parties changed after failed register: " + q.getRegisteredParties());
        }

        Phaser t = new Phaser();
        t.forceTermination();
        if (!t.isTerminated()) {
            throw new RuntimeException("expected phaser to be terminated");
        }
        int tr = t.register();
        if (tr >= 0) {
            throw new RuntimeException("expected negative value from register on terminated phaser but got " + tr);
        }
    }
}
