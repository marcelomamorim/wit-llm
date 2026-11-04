/*
 * @test
 * @summary Tests for java.time.LocalTime.ofNanoOfDay(long)
 * @run main LocalTimeWitupTest
 */

import java.time.LocalTime;
import java.time.temporal.ChronoField;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testValidNanoOfDay();
        testNegativeNanoOfDay();
        testExceedingNanoOfDay();
    }

    static void testValidNanoOfDay() {
        long nano = 123456789L;
        LocalTime time = LocalTime.ofNanoOfDay(nano);
        long computedNano = time.toNanoOfDay();
        if (computedNano != nano) {
            throw new AssertionError("Expected nano: " + nano + ", but got: " + computedNano);
        }
    }

    static void testNegativeNanoOfDay() {
        long nano = -1L;
        try {
            LocalTime.ofNanoOfDay(nano);
            throw new AssertionError("Expected DateTimeException for negative nanoOfDay");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }

    static void testExceedingNanoOfDay() {
        long nano = 1000000001L; // NANOS_PER_DAY + 1
        try {
            LocalTime.ofNanoOfDay(nano);
            throw new AssertionError("Expected DateTimeException for nanoOfDay exceeding max");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }
}
