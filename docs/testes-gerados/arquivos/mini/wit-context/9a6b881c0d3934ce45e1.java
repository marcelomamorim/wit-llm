/*
 * @test
 * @summary Test java.util.concurrent.Phaser constructor with int parties
 * @run main PhaserWitupTest
 */

import java.util.concurrent.Phaser;

public class PhaserWitupTest {
    public static void main(String[] args) {
        testValidParties();
        testIllegalArgumentException();
    }

    private static void testValidParties() {
        // Test with valid parties (0 and positive values within limit)
        Phaser p0 = new Phaser(0);
        if (p0 == null) throw new AssertionError("Phaser instance is null for 0 parties");

        Phaser p1 = new Phaser(1);
        if (p1 == null) throw new AssertionError("Phaser instance is null for 1 party");

        Phaser p100 = new Phaser(100);
        if (p100 == null) throw new AssertionError("Phaser instance is null for 100 parties");
    }

    private static void testIllegalArgumentException() {
        // According to WIT, if (parties >>> 16 != 0) then IllegalArgumentException is thrown
        // So test with parties > 0xFFFF (65535) to trigger exception
        int invalidParties = 0x10000; // 65536
        try {
            new Phaser(invalidParties);
            throw new AssertionError("Expected IllegalArgumentException for parties=" + invalidParties);
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }
}
