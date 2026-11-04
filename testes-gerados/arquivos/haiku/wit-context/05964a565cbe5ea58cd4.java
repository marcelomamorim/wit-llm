/*
 * @test
 * @summary Witup-generated checks for YearMonth.atDay(int)
 * @run main YearMonthWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        checkValid(YearMonth.of(2020, 2), 1, LocalDate.of(2020, 2, 1));
        checkValid(YearMonth.of(2020, 2), 29, LocalDate.of(2020, 2, 29));
        checkValid(YearMonth.of(2019, 12), 31, LocalDate.of(2019, 12, 31));
        checkValid(YearMonth.of(2020, 4), 30, LocalDate.of(2020, 4, 30));

        checkInvalid(YearMonth.of(2019, 2), 29);
        checkInvalid(YearMonth.of(2020, 2), 30);
        checkInvalid(YearMonth.of(2020, 4), 31);
        checkInvalid(YearMonth.of(2020, 1), 0);
        checkInvalid(YearMonth.of(2020, 1), 32);
        checkInvalid(YearMonth.of(2020, 1), -1);
    }

    private static void checkValid(YearMonth ym, int day, LocalDate expected) {
        LocalDate actual = ym.atDay(day);
        if (!expected.equals(actual)) {
            throw new RuntimeException("atDay(" + day + ") on " + ym
                    + " returned " + actual + ", expected " + expected);
        }
    }

    private static void checkInvalid(YearMonth ym, int day) {
        try {
            LocalDate result = ym.atDay(day);
            throw new RuntimeException("atDay(" + day + ") on " + ym
                    + " should throw DateTimeException but returned " + result);
        } catch (DateTimeException expected) {
            // expected
        }
    }
}
