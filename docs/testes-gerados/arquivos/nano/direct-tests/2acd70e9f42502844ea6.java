/*
 * @test
 * @summary Test LocalDate.withDayOfMonth(int)
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testWithDayOfMonthSameDay();
        testWithDayOfMonthDifferentDay();
        testWithDayOfMonthInvalidDay();
    }

    static void testWithDayOfMonthSameDay() {
        LocalDate date = LocalDate.of(2023, 3, 15);
        LocalDate result = date.withDayOfMonth(15);
        assert result == date : "Expected same instance when day is unchanged";
    }

    static void testWithDayOfMonthDifferentDay() {
        LocalDate date = LocalDate.of(2023, 3, 15);
        LocalDate result = date.withDayOfMonth(10);
        assert result.getYear() == 2023 : "Year should remain unchanged";
        assert result.getMonthValue() == 3 : "Month should remain unchanged";
        assert result.getDayOfMonth() == 10 : "Day should be updated";
    }

    static void testWithDayOfMonthInvalidDay() {
        LocalDate date = LocalDate.of(2023, 1, 31);
        try {
            date.withDayOfMonth(31);
            assert false : "Expected DateTimeException for invalid day";
        } catch (Exception e) {
            // Expected exception
        }
    }
}
