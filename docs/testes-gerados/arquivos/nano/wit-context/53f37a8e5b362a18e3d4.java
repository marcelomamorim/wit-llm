/*
 * @test
 * @summary Tests for java.time.Year.atMonthDay(MonthDay)
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.MonthDay;
import java.time.LocalDate;

public class YearWitupTest {

    public static void main(String[] args) {
        testAtMonthDayValid();
        testAtMonthDayInvalid();
    }

    static void testAtMonthDayValid() {
        Year year = Year.of(2020);
        MonthDay md = MonthDay.of(2, 29);
        LocalDate date = year.atMonthDay(md);
        if (!date.equals(LocalDate.of(2020, 2, 29))) {
            throw new AssertionError("Expected 2020-02-29, but got " + date);
        }
    }

    static void testAtMonthDayInvalid() {
        Year year = Year.of(2019);
        MonthDay md = MonthDay.of(2, 29);
        try {
            year.atMonthDay(md);
            throw new AssertionError("Expected DateTimeException for invalid date");
        } catch (java.time.DateTimeException e) {
            // expected
        }
    }
}
