/*
 * @test
 * @summary Test java.util.concurrent.Phaser.register() for normal and boundary behavior
 * @run main PhaserWitupTest
 */

import java.util.concurrent.Phaser;

public class PhaserWitupTest {
    public static void main(String[] args) {
        testRegisterNormal();
        testRegisterOverflow();
    }

    static void testRegisterNormal() {
        Phaser phaser = new Phaser(0);
        int phase = phaser.register();
        if (phase != 0) {
            throw new Error("Expected phase 0 on first register, got " + phase);
        }
        // Register multiple parties and check phase remains consistent
        for (int i = 0; i < 5; i++) {
            int p = phaser.register();
            if (p != 0) {
                throw new Error("Expected phase 0 on register " + i + ", got " + p);
            }
        }
    }

    static void testRegisterOverflow() {
        // Create a Phaser with parties near the maximum allowed to trigger IllegalStateException
        // The internal counts field encodes parties in upper bits shifted by PARTIES_SHIFT (16)
        // We simulate this by creating a Phaser with a large number of parties close to 0xFFFF
        // Since no direct access to counts, we use bulkRegister to approach the limit
        Phaser phaser = new Phaser(0);
        int maxParties = 0xFFFF; // maximum parties allowed internally
        int initialParties = maxParties - 1; // one less than max
        phaser.bulkRegister(initialParties);
        // Now registering one more party should cause IllegalStateException
        boolean thrown = false;
        try {
            phaser.register();
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new Error("Expected IllegalStateException when registering beyond max parties");
        }
    }
}
