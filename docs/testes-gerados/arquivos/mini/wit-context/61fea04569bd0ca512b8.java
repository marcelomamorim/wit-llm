/*
 * @test
 * @summary Test java.time.Instant.plusSeconds(long) for normal and boundary cases
 * @run main InstantWitupTest
 */

import java.time.Instant;
import java.time.DateTimeException;
import java.lang.ArithmeticException;

public class InstantWitupTest {
    public static void main(String[] args) {
        testPlusSecondsZero();
        testPlusSecondsPositive();
        testPlusSecondsNegative();
        testPlusSecondsOverflowArithmeticException();
        testPlusSecondsOutOfRangeDateTimeException();
    }

    private static void testPlusSecondsZero() {
        Instant now = Instant.now();
        Instant result = now.plusSeconds(0L);
        if (!result.equals(now)) {
            throw new AssertionError("plusSeconds(0) should return the same Instant");
        }
    }

    private static void testPlusSecondsPositive() {
        Instant base = Instant.ofEpochSecond(1000L, 500);
        Instant plus = base.plusSeconds(10L);
        if (plus.getEpochSecond() != 1010L || plus.getNano() != 500) {
            throw new AssertionError("plusSeconds(10) did not add seconds correctly");
        }
    }

    private static void testPlusSecondsNegative() {
        Instant base = Instant.ofEpochSecond(1000L, 500);
        Instant minus = base.plusSeconds(-10L);
        if (minus.getEpochSecond() != 990L || minus.getNano() != 500) {
            throw new AssertionError("plusSeconds(-10) did not subtract seconds correctly");
        }
    }

    private static void testPlusSecondsOverflowArithmeticException() {
        // This test tries to trigger ArithmeticException by causing overflow in addExact
        // The overflow occurs if seconds and secondsToAdd have opposite signs and the sum overflows long
        // Use Long.MAX_VALUE and 1 to cause overflow
        Instant base = Instant.ofEpochSecond(Long.MAX_VALUE, 0);
        try {
            base.plusSeconds(1L);
            throw new AssertionError("Expected ArithmeticException not thrown for overflow");
        } catch (ArithmeticException expected) {
            // expected
        }

        base = Instant.ofEpochSecond(Long.MIN_VALUE, 0);
        try {
            base.plusSeconds(-1L);
            throw new AssertionError("Expected ArithmeticException not thrown for underflow");
        } catch (ArithmeticException expected) {
            // expected
        }
    }

    private static void testPlusSecondsOutOfRangeDateTimeException() {
        // This test tries to trigger DateTimeException by adding seconds that cause the result to be out of Instant range
        // The valid range is between -31557014167219200L and 31556889864403199L seconds
        // Use values just outside this range
        long min = -31557014167219200L;
        long max = 31556889864403199L;

        Instant minInstant = Instant.ofEpochSecond(min, 0);
        Instant maxInstant = Instant.ofEpochSecond(max, 0);

        try {
            minInstant.plusSeconds(-1L);
            throw new AssertionError("Expected DateTimeException not thrown for below minimum Instant");
        } catch (DateTimeException expected) {
            // expected
        }

        try {
            maxInstant.plusSeconds(1L);
            throw new AssertionError("Expected DateTimeException not thrown for above maximum Instant");
        } catch (DateTimeException expected) {
            // expected
        }
    }
}
