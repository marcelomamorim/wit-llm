/*
 * @test
 * @summary Test Duration.plusMillis method for correct addition of milliseconds.
 * @run main DurationWitupTest
 */

import java.time.Duration;

public class DurationWitupTest {

    public static void main(String[] args) {
        testPlusMillisPositive();
        testPlusMillisZero();
        testPlusMillisNegative();
        testPlusMillisLarge();
    }

    static void testPlusMillisPositive() {
        Duration original = Duration.ofSeconds(1);
        Duration result = original.plusMillis(1500);
        Duration expected = Duration.ofSeconds(1).plusMillis(1500);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: plusMillis positive test. Expected: " + expected + ", but got: " + result);
        }
    }

    static void testPlusMillisZero() {
        Duration original = Duration.ofSeconds(2);
        Duration result = original.plusMillis(0);
        if (!result.equals(original)) {
            throw new AssertionError("Failed: plusMillis zero test. Expected: " + original + ", but got: " + result);
        }
    }

    static void testPlusMillisNegative() {
        Duration original = Duration.ofSeconds(3);
        Duration result = original.plusMillis(-500);
        Duration expected = Duration.ofSeconds(3).minusMillis(500);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: plusMillis negative test. Expected: " + expected + ", but got: " + result);
        }
    }

    static void testPlusMillisLarge() {
        Duration original = Duration.ofSeconds(0);
        long largeMillis = Long.MAX_VALUE;
        Duration result = original.plusMillis(largeMillis);
        Duration expected = Duration.ofMillis(largeMillis);
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: plusMillis large value test. Expected: " + expected + ", but got: " + result);
        }
    }
}
