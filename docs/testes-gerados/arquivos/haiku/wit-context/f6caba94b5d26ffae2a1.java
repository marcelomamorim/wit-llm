/*
 * @test
 * @summary Witup-generated tests for YearMonth.of(int, int) argument validation and valid boundaries
 * @run main YearMonthWitupTest
 */

import java.time.DateTimeException;
import java.time.YearMonth;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        checkValid(2020, 1);
        checkValid(2020, 12);
        checkValid(999999999, 12);
        checkValid(-999999999, 1);

        expectInvalid(1000000000, 1);
        expectInvalid(-1000000000, 1);
        expectInvalid(Integer.MAX_VALUE, 1);
        expectInvalid(Integer.MIN_VALUE, 1);

        expectInvalid(2020, 0);
        expectInvalid(2020, 13);
        expectInvalid(2020, -1);
        expectInvalid(2020, Integer.MAX_VALUE);
        expectInvalid(999999999, 0);
        expectInvalid(-999999999, 13);
    }

    static void checkValid(int year, int month) {
        YearMonth ym = YearMonth.of(year, month);
        if (ym.getYear() != year) {
            throw new RuntimeException("unexpected year for " + year + "/" + month + ": " + ym.getYear());
        }
        if (ym.getMonthValue() != month) {
            throw new RuntimeException("unexpected month for " + year + "/" + month + ": " + ym.getMonthValue());
        }
    }

    static void expectInvalid(int year, int month) {
        try {
            YearMonth.of(year, month);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("no DateTimeException for " + year + "/" + month);
    }
}
