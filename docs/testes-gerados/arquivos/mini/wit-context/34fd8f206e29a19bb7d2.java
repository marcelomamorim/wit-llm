/*
 * @test
 * @summary Test LocalDateTime.withMinute(int) for valid and invalid inputs
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.Month;
import java.time.DateTimeException;

public class LocalDateTimeWitupTest {
    public static void main(String[] args) {
        testWithMinuteValid();
        testWithMinuteInvalid();
    }

    private static void testWithMinuteValid() {
        LocalDateTime ldt = LocalDateTime.of(2018, Month.JANUARY, 1, 10, 30);
        // Change minute to a different valid value
        LocalDateTime changed = ldt.withMinute(45);
        if (changed.getMinute() != 45) {
            throw new AssertionError("Expected minute to be 45 but was " + changed.getMinute());
        }
        // Change minute to the same value returns equal instance (not necessarily same object but equal)
        LocalDateTime same = ldt.withMinute(30);
        if (!same.equals(ldt)) {
            throw new AssertionError("Expected withMinute with same minute to return equal LocalDateTime");
        }
    }

    private static void testWithMinuteInvalid() {
        LocalDateTime ldt = LocalDateTime.of(2018, Month.JANUARY, 1, 10, 30);
        int[] invalidMinutes = {-1, 60, 100};
        for (int invalidMinute : invalidMinutes) {
            boolean thrown = false;
            try {
                ldt.withMinute(invalidMinute);
            } catch (DateTimeException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("Expected DateTimeException for minute=" + invalidMinute);
            }
        }
    }
}
