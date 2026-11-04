/*
 * @test
 * @summary Test java.time.LocalDateTime.withHour(int) method for valid and invalid hour values
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;
import java.time.DateTimeException;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testWithHourValid();
        testWithHourInvalid();
    }

    private static void testWithHourValid() {
        // Create a LocalDateTime with a known date and time
        LocalDateTime ldt = LocalDateTime.of(2018, Month.JANUARY, 1, 10, 30);
        // Change hour to a valid value different from current
        LocalDateTime changed = ldt.withHour(15);
        if (changed.getHour() != 15) {
            throw new AssertionError("withHour did not set the hour correctly");
        }
        // Changing hour to the same value should return an equal object
        LocalDateTime same = ldt.withHour(10);
        if (!same.equals(ldt)) {
            throw new AssertionError("withHour with same hour should return equal LocalDateTime");
        }
    }

    private static void testWithHourInvalid() {
        LocalDateTime ldt = LocalDateTime.of(2018, Month.JANUARY, 1, 10, 30);
        // Test hour less than 0
        try {
            ldt.withHour(-1);
            throw new AssertionError("withHour should throw DateTimeException for hour < 0");
        } catch (DateTimeException expected) {
            // expected
        }
        // Test hour greater than 23
        try {
            ldt.withHour(24);
            throw new AssertionError("withHour should throw DateTimeException for hour > 23");
        } catch (DateTimeException expected) {
            // expected
        }
    }
}
