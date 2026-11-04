/*
 * @test
 * @summary Test LocalDate.withDayOfYear(int)
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testWithDayOfYearSame();
        testWithDayOfYearDifferent();
    }

    static void testWithDayOfYearSame() {
        LocalDate date = LocalDate.of(2023, 3, 15);
        int dayOfYear = date.getDayOfYear();
        LocalDate result = date.withDayOfYear(dayOfYear);
        assert result == date : "Expected same instance when dayOfYear is unchanged";
    }

    static void testWithDayOfYearDifferent() {
        LocalDate date = LocalDate.of(2023, 1, 1);
        int newDayOfYear = 100;
        LocalDate result = date.withDayOfYear(newDayOfYear);
        LocalDate expected = LocalDate.ofYearDay(2023, newDayOfYear);
        assert result.equals(expected) : "Result should be the date with specified day of year";
    }
}
