/*
 * @test
 * @summary Test LocalDateTime.withSecond(int) for valid and invalid seconds
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;
import java.time.DateTimeException;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testWithSecondValid();
        testWithSecondInvalid();
    }

    private static void testWithSecondValid() {
        LocalDateTime ldt = LocalDateTime.of(2018, Month.JANUARY, 1, 12, 30, 15);
        // Change second to a valid value
        LocalDateTime changed = ldt.withSecond(45);
        if (changed.getSecond() != 45) {
            throw new AssertionError("Expected second to be 45 but was " + changed.getSecond());
        }
        // Changing to the same second returns equal instance
        LocalDateTime same = ldt.withSecond(15);
        if (!same.equals(ldt)) {
            throw new AssertionError("Expected same instance or equal for same second");
        }
    }

    private static void testWithSecondInvalid() {
        LocalDateTime ldt = LocalDateTime.of(2018, Month.JANUARY, 1, 12, 30, 15);
        // Test invalid second: negative
        try {
            ldt.withSecond(-1);
            throw new AssertionError("Expected DateTimeException for second -1");
        } catch (DateTimeException e) {
            // expected
        }
        // Test invalid second: greater than 59
        try {
            ldt.withSecond(60);
            throw new AssertionError("Expected DateTimeException for second 60");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
