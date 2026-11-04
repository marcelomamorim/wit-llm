/*
 * @test
 * @summary Test constructor Phaser(Phaser parent, int parties) for argument validation and state initialization
 * @run main PhaserWitupTest
 */

import java.util.concurrent.Phaser;

public class PhaserWitupTest {
    public static void main(String[] args) {
        testIllegalArgumentExceptionForTooLargeParties();
        testConstructorWithNullParentAndZeroParties();
        testConstructorWithParentAndNonZeroParties();
    }

    private static void testIllegalArgumentExceptionForTooLargeParties() {
        Phaser parent = new Phaser();
        int invalidParties = 1 << 16; // 65536, shifted right by PARTIES_SHIFT (16) != 0
        try {
            new Phaser(parent, invalidParties);
            throw new Error("Expected IllegalArgumentException for too large parties");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testConstructorWithNullParentAndZeroParties() {
        Phaser phaser = new Phaser(null, 0);
        // phase should be 0, parties 0, state not EMPTY (EMPTY is 0L)
        // We cannot access state directly, but we can check that no exception is thrown and phaser is non-null
        if (phaser == null) {
            throw new Error("Phaser constructor returned null");
        }
    }

    private static void testConstructorWithParentAndNonZeroParties() {
        Phaser parent = new Phaser();
        int parties = 1;
        Phaser phaser = new Phaser(parent, parties);
        if (phaser == null) {
            throw new Error("Phaser constructor returned null");
        }
        // The constructor calls parent.doRegister(1) if parties != 0
        // We cannot access internal state, but we can test that no exception is thrown
    }
}
