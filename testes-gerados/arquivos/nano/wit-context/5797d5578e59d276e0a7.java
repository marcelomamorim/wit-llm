/*
 * @test
 * @summary Test for Duration.withNanos(int)
 * @run main DurationWitupTest
 */

import java.time.Duration;
import java.time.temporal.ChronoField;

public class DurationWitupTest {

    public static void main(String[] args) {
        testWithNanosValid();
        testWithNanosNegative();
        testWithNanosExceedingMax();
        testWithNanosExceedingMin();
    }

    static void testWithNanosValid() {
        int validNano = 500_000_000;
        Duration d = Duration.ofSeconds(10);
        Duration result = d.withNanos(validNano);
        assert result != null : "Result should not be null";
        assert result.getSeconds() == 10 : "Seconds should remain unchanged";
        assert result.getNano() == validNano : "Nano part should be updated";
    }

    static void testWithNanosNegative() {
        int negativeNano = -1;
        try {
            Duration.ofSeconds(5).withNanos(negativeNano);
            assert false : "Expected DateTimeException for negative nanos";
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    static void testWithNanosExceedingMax() {
        int exceedingNano = 1_000_000_000; // 1 billion, exceeds max valid nano (less than 1,000,000,000)
        try {
            Duration.ofSeconds(0).withNanos(exceedingNano);
            assert false : "Expected DateTimeException for nanos exceeding max";
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    static void testWithNanosExceedingMin() {
        int exceedingNano = -1; // negative
        try {
            Duration.ofSeconds(0).withNanos(exceedingNano);
            assert false : "Expected DateTimeException for negative nanos";
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }
}
