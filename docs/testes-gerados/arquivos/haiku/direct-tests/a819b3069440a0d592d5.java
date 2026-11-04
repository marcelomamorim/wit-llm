/*
 * @test
 * @summary Year.of(int) accepts ISO years in the valid range and rejects values outside it
 * @run main YearWitupTest
 */

import java.time.DateTimeException;
import java.time.Year;

public class YearWitupTest {
    public static void main(String[] args) {
        check(Year.of(2007).getValue() == 2007, "2007");
        check(Year.of(0).getValue() == 0, "0");
        check(Year.of(-1).getValue() == -1, "-1");
        check(Year.of(999999999).getValue() == 999999999, "max");
        check(Year.of(-999999999).getValue() == -999999999, "min");
        expectDateTimeException(1000000000);
        expectDateTimeException(-1000000000);
        expectDateTimeException(Integer.MAX_VALUE);
        expectDateTimeException(Integer.MIN_VALUE);
        check(Year.of(2007).equals(Year.of(2007)), "equals");
        check(Year.of(2007).toString().equals("2007"), "toString");
    }

    static void expectDateTimeException(int year) {
        try {
            Year.of(year);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("no DateTimeException for " + year);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
