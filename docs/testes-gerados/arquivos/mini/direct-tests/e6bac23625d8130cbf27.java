/*
 * @test
 * @summary Test java.time.ZoneOffset.ofHours(int) factory method
 * @run main ZoneOffsetWitupTest
 */

import java.time.ZoneOffset;

public class ZoneOffsetWitupTest {
    public static void main(String[] args) {
        testValidHours();
        testBoundaryHours();
    }

    private static void testValidHours() {
        // Test typical valid hour offsets
        ZoneOffset z1 = ZoneOffset.ofHours(0);
        if (z1.getTotalSeconds() != 0) {
            throw new AssertionError("Expected offset 0 seconds for 0 hours");
        }

        ZoneOffset z2 = ZoneOffset.ofHours(5);
        if (z2.getTotalSeconds() != 5 * 3600) {
            throw new AssertionError("Expected offset 18000 seconds for 5 hours");
        }

        ZoneOffset z3 = ZoneOffset.ofHours(-3);
        if (z3.getTotalSeconds() != -3 * 3600) {
            throw new AssertionError("Expected offset -10800 seconds for -3 hours");
        }
    }

    private static void testBoundaryHours() {
        // Test boundary values allowed by ZoneOffset (max +18, min -18)
        ZoneOffset max = ZoneOffset.ofHours(18);
        if (max.getTotalSeconds() != 18 * 3600) {
            throw new AssertionError("Expected offset 64800 seconds for 18 hours");
        }

        ZoneOffset min = ZoneOffset.ofHours(-18);
        if (min.getTotalSeconds() != -18 * 3600) {
            throw new AssertionError("Expected offset -64800 seconds for -18 hours");
        }
    }
}
