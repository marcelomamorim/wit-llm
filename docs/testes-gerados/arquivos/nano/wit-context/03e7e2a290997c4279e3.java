/*
 * @test
 * @summary Test for LocalTime.withNano(int)
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.temporal.ChronoField;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testWithNanoValidValues();
        testWithNanoEqualCurrent();
        testWithNanoBelowMinimum();
        testWithNanoAboveMaximum();
    }

    private static void testWithNanoValidValues() {
        LocalTime time = LocalTime.of(12, 30, 45, 500_000_000); // 12:30:45.500000000
        int[] validNanos = {0, 999_999_999, 123_456_789};
        for (int nano : validNanos) {
            LocalTime result = time.withNano(nano);
            assert result.getNano() == nano : "Expected nano: " + nano + ", but got: " + result.getNano();
            assert result.getHour() == time.getHour() : "Hour changed unexpectedly";
            assert result.getMinute() == time.getMinute() : "Minute changed unexpectedly";
            assert result.getSecond() == time.getSecond() : "Second changed unexpectedly";
        }
    }

    private static void testWithNanoEqualCurrent() {
        LocalTime time = LocalTime.of(8, 15, 30, 250_000_000);
        LocalTime result = time.withNano(250_000_000);
        assert result == time : "withNano should return the same instance if nano is unchanged";
    }

    private static void testWithNanoBelowMinimum() {
        int[] invalidNanos = {-1, -100, -999999999};
        for (int nano : invalidNanos) {
            try {
                LocalTime.of(10, 0).withNano(nano);
                assert false : "Expected DateTimeException for nano: " + nano;
            } catch (java.time.DateTimeException e) {
                // Expected exception
            }
        }
    }

    private static void testWithNanoAboveMaximum() {
        int[] invalidNanos = {1_000_000_000, 1_500_000_000};
        for (int nano : invalidNanos) {
            try {
                LocalTime.of(10, 0).withNano(nano);
                assert false : "Expected DateTimeException for nano: " + nano;
            } catch (java.time.DateTimeException e) {
                // Expected exception
            }
        }
    }
}
