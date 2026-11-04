/*
 * @test
 * @summary Witup-derived tests for MonthDay.format(DateTimeFormatter): null formatter must throw NullPointerException and valid formatting must be observable
 * @run main MonthDayWitupTest
 */

import java.time.Month;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;

public class MonthDayWitupTest {

    public static void main(String[] args) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("MM/dd");

        MonthDay md = MonthDay.of(12, 25);
        check("12/25".equals(md.format(f)), "format of 12/25 failed");

        MonthDay jan = MonthDay.of(Month.JANUARY, 1);
        check("01/01".equals(jan.format(f)), "format of 01/01 failed");

        MonthDay leap = MonthDay.of(Month.FEBRUARY, 29);
        check("02/29".equals(leap.format(f)), "format of 02/29 failed");

        boolean thrown = false;
        try {
            md.format(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "format(null) must throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
