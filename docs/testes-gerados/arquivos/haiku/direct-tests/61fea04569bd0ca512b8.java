/*
 * @test
 * @summary Verifies observable behavior of Instant.plusSeconds, including nanosecond preservation, overflow and range errors
 * @run main InstantWitupTest
 */
import java.time.DateTimeException;
import java.time.Instant;

public class InstantWitupTest {

    public static void main(String[] args) {
        testZeroSecondsReturnsEquivalentInstant();
        testPositiveSecondsAddsAndPreservesNanos();
        testNegativeSecondsSubtractsAndPreservesNanos();
        testFromEpoch();
        testLongOverflowThrowsArithmeticException();
        testMaxInstantThrowsDateTimeException();
        testMinInstantThrowsDateTimeException();
    }

    static void testZeroSecondsReturnsEquivalentInstant() {
        Instant base = Instant.ofEpochSecond(100L, 500L);
        Instant result = base.plusSeconds(0L);
        check(result.equals(base), "plusSeconds(0) should be equal to the original instant");
    }

    static void testPositiveSecondsAddsAndPreservesNanos() {
        Instant base = Instant.ofEpochSecond(100L, 500L);
        Instant result = base.plusSeconds(10L);
        check(result.getEpochSecond() == 110L, "expected epoch second 110 but was " + result.getEpochSecond());
        check(result.getNano() == 500, "expected nano 500 but was " + result.getNano());
    }

    static void testNegativeSecondsSubtractsAndPreservesNanos() {
        Instant base = Instant.ofEpochSecond(100L, 500L);
        Instant result = base.plusSeconds(-200L);
        check(result.getEpochSecond() == -100L, "expected epoch second -100 but was " + result.getEpochSecond());
        check(result.getNano() == 500, "expected nano 500 but was " + result.getNano());
    }

    static void testFromEpoch() {
        Instant result = Instant.EPOCH.plusSeconds(1L);
        check(result.getEpochSecond() == 1L, "expected epoch second 1 but was " + result.getEpochSecond());
        check(result.getNano() == 0, "expected nano 0 but was " + result.getNano());
    }

    static void testLongOverflowThrowsArithmeticException() {
        Instant base = Instant.ofEpochSecond(1L);
        boolean thrown = false;
        try {
            base.plusSeconds(Long.MAX_VALUE);
        } catch (ArithmeticException e) {
            thrown = true;
        }
        check(thrown, "expected ArithmeticException on long overflow");
    }

    static void testMaxInstantThrowsDateTimeException() {
        boolean thrown = false;
        try {
            Instant.MAX.plusSeconds(1L);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "expected DateTimeException when exceeding Instant.MAX");
    }

    static void testMinInstantThrowsDateTimeException() {
        boolean thrown = false;
        try {
            Instant.MIN.plusSeconds(-1L);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "expected DateTimeException when going below Instant.MIN");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
