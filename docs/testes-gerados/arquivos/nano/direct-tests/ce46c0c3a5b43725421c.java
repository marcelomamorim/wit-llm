/*
 * @test
 * @summary Test LocalDateTime.of(int, int, int, int, int) method for correct behavior.
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testOfMethod();
    }

    public static void testOfMethod() {
        // Test case 1: Typical date and time
        LocalDateTime result1 = LocalDateTime.of(2023, 3, 15, 10, 30);
        assert result1.getYear() == 2023 : "Year mismatch";
        assert result1.getMonthValue() == 3 : "Month mismatch";
        assert result1.getDayOfMonth() == 15 : "Day mismatch";
        assert result1.getHour() == 10 : "Hour mismatch";
        assert result1.getMinute() == 30 : "Minute mismatch";

        // Test case 2: Edge case - earliest date
        LocalDateTime result2 = LocalDateTime.of(1, 1, 1, 0, 0);
        assert result2.getYear() == 1 : "Year mismatch";
        assert result2.getMonthValue() == 1 : "Month mismatch";
        assert result2.getDayOfMonth() == 1 : "Day mismatch";
        assert result2.getHour() == 0 : "Hour mismatch";
        assert result2.getMinute() == 0 : "Minute mismatch";

        // Test case 3: Edge case - latest date in typical range
        LocalDateTime result3 = LocalDateTime.of(9999, 12, 31, 23, 59);
        assert result3.getYear() == 9999 : "Year mismatch";
        assert result3.getMonthValue() == 12 : "Month mismatch";
        assert result3.getDayOfMonth() == 31 : "Day mismatch";
        assert result3.getHour() == 23 : "Hour mismatch";
        assert result3.getMinute() == 59 : "Minute mismatch";

        // Test case 4: Invalid month (should throw exception)
        try {
            LocalDateTime.of(2023, 13, 10, 10, 10);
            assert false : "Expected DateTimeException for invalid month";
        } catch (Exception e) {
            // Expected exception
        }

        // Test case 5: Invalid day (should throw exception)
        try {
            LocalDateTime.of(2023, 2, 30, 10, 10);
            assert false : "Expected DateTimeException for invalid day";
        } catch (Exception e) {
            // Expected exception
        }
    }
}
