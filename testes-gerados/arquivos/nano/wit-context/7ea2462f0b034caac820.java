/*
 * @test
 * @summary Test for LocalDateTime.plusHours(long)
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testPlusHoursNormal();
        testPlusHoursOverflow();
        testPlusHoursNegative();
        testPlusHoursLarge();
    }

    static void testPlusHoursNormal() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.MARCH, 15, 10, 30);
        LocalDateTime result = dt.plusHours(5);
        if (!result.equals(LocalDateTime.of(2023, Month.MARCH, 15, 15, 30))) {
            throw new AssertionError("Failed: normal plusHours");
        }
    }

    static void testPlusHoursOverflow() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.MARCH, 15, 23, 0);
        LocalDateTime result = dt.plusHours(2);
        if (!result.equals(LocalDateTime.of(2023, Month.MARCH, 16, 1, 0))) {
            throw new AssertionError("Failed: overflow plusHours");
        }
    }

    static void testPlusHoursNegative() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.MARCH, 15, 1, 0);
        LocalDateTime result = dt.plusHours(-3);
        if (!result.equals(LocalDateTime.of(2023, Month.MARCH, 14, 22, 0))) {
            throw new AssertionError("Failed: negative plusHours");
        }
    }

    static void testPlusHoursLarge() {
        LocalDateTime dt = LocalDateTime.of(2023, Month.MARCH, 15, 12, 0);
        LocalDateTime result = dt.plusHours(1000);
        LocalDateTime expected = dt.plusHours(1000); // expected result
        if (!result.equals(expected)) {
            throw new AssertionError("Failed: large plusHours");
        }
    }
}
