/*
 * @test
 * @summary Test for Instant.plusMillis method to ensure correct addition of milliseconds.
 * @run main InstantWitupTest
 */

import java.time.Instant;

public class InstantWitupTest {

    public static void main(String[] args) {
        testPlusMillisPositive();
        testPlusMillisZero();
        testPlusMillisNegative();
        testPlusMillisLarge();
    }

    static void testPlusMillisPositive() {
        Instant base = Instant.ofEpochSecond(1000L);
        long millisToAdd = 1500L;
        Instant result = base.plusMillis(millisToAdd);
        Instant expected = base.plusSeconds(millisToAdd / 1000).plusNanos((millisToAdd % 1000) * 1_000_000);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: plusMillis positive test. Expected: " + expected + ", but got: " + result);
        }
    }

    static void testPlusMillisZero() {
        Instant base = Instant.ofEpochSecond(2000L);
        long millisToAdd = 0L;
        Instant result = base.plusMillis(millisToAdd);
        if (!result.equals(base)) {
            throw new AssertionError("Failed: plusMillis zero test. Expected: " + base + ", but got: " + result);
        }
    }

    static void testPlusMillisNegative() {
        Instant base = Instant.ofEpochSecond(3000L);
        long millisToAdd = -2000L;
        Instant result = base.plusMillis(millisToAdd);
        Instant expected = base.plusSeconds(millisToAdd / 1000).plusNanos((millisToAdd % 1000) * 1_000_000);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: plusMillis negative test. Expected: " + expected + ", but got: " + result);
        }
    }

    static void testPlusMillisLarge() {
        Instant base = Instant.ofEpochSecond(0L);
        long millisToAdd = Long.MAX_VALUE;
        Instant result = base.plusMillis(millisToAdd);
        Instant expected = base.plusSeconds(millisToAdd / 1000).plusNanos((millisToAdd % 1000) * 1_000_000);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: plusMillis large value test. Expected: " + expected + ", but got: " + result);
        }
    }
}
