/*
 * @test
 * @summary Test java.time.MonthDay.atYear(int) method behavior
 * @run main MonthDayWitupTest
 */

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Month;

public class MonthDayWitupTest {
    public static void main(String[] args) {
        testAtYearNormal();
        testAtYearInvalidDayFallsBackTo28();
    }

    private static void testAtYearNormal() {
        MonthDay md = MonthDay.of(Month.MARCH, 15);
        LocalDate ld = md.atYear(2019); // 2019 is not leap year but day 15 is valid
        if (ld.getYear() != 2019 || ld.getMonthValue() != 3 || ld.getDayOfMonth() != 15) {
            throw new AssertionError("Expected 2019-03-15 but got " + ld);
        }
    }

    private static void testAtYearInvalidDayFallsBackTo28() {
        // February 29 on a non-leap year should fallback to 28
        MonthDay md = MonthDay.of(Month.FEBRUARY, 29);
        LocalDate ld = md.atYear(2019); // 2019 is not leap year
        if (ld.getYear() != 2019 || ld.getMonthValue() != 2 || ld.getDayOfMonth() != 28) {
            throw new AssertionError("Expected 2019-02-28 but got " + ld);
        }
        // On leap year, day should be 29
        LocalDate ldLeap = md.atYear(2020);
        if (ldLeap.getYear() != 2020 || ldLeap.getMonthValue() != 2 || ldLeap.getDayOfMonth() != 29) {
            throw new AssertionError("Expected 2020-02-29 but got " + ldLeap);
        }
    }
}
