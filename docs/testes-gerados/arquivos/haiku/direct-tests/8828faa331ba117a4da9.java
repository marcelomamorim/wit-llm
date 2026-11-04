/*
 * @test
 * @summary Verifies MonthDay.getMonth() returns the Month matching the month-of-year used to create the MonthDay
 * @run main MonthDayWitupTest
 */

import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;

public class MonthDayWitupTest {

    public static void main(String[] args) {
        checkIntFactory();
        checkMonthFactory();
        checkAllMonths();
        checkFromTemporal();
        checkEndOfYear();
    }

    static void checkIntFactory() {
        check(MonthDay.of(1, 15).getMonth() == Month.JANUARY, "of(1,15) should be JANUARY");
        check(MonthDay.of(7, 4).getMonth() == Month.JULY, "of(7,4) should be JULY");
    }

    static void checkMonthFactory() {
        check(MonthDay.of(Month.MARCH, 10).getMonth() == Month.MARCH, "of(MARCH,10) should be MARCH");
        check(MonthDay.of(Month.DECEMBER, 31).getMonth() == Month.DECEMBER, "of(DECEMBER,31) should be DECEMBER");
    }

    static void checkAllMonths() {
        for (Month m : Month.values()) {
            MonthDay md = MonthDay.of(m, 1);
            check(md.getMonth() == m, "getMonth mismatch for " + m);
            check(md.getMonth().getValue() == m.getValue(), "month value mismatch for " + m);
        }
    }

    static void checkFromTemporal() {
        MonthDay md = MonthDay.from(LocalDate.of(2020, 2, 29));
        check(md.getMonth() == Month.FEBRUARY, "from(2020-02-29) should be FEBRUARY");
    }

    static void checkEndOfYear() {
        check(MonthDay.of(12, 1).getMonth() == Month.DECEMBER, "month 12 should be DECEMBER");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
