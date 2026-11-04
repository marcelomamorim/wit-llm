/*
 * @test
 * @summary Witup-derived tests for Year.of(int) range validation
 * @run main YearWitupTest
 */

import java.time.DateTimeException;
import java.time.Year;

public class YearWitupTest {
    public static void main(String[] args) {
        if (Year.of(2020).getValue() != 2020) {
            throw new RuntimeException("valid year not preserved");
        }
        if (Year.of(Year.MAX_VALUE).getValue() != Year.MAX_VALUE) {
            throw new RuntimeException("MAX_VALUE not accepted");
        }
        if (Year.of(Year.MIN_VALUE).getValue() != Year.MIN_VALUE) {
            throw new RuntimeException("MIN_VALUE not accepted");
        }
        expectDateTimeException(Year.MAX_VALUE + 1);
        expectDateTimeException(Year.MIN_VALUE - 1);
        expectDateTimeException(Integer.MIN_VALUE);
        expectDateTimeException(Integer.MAX_VALUE);
    }

    static void expectDateTimeException(int isoYear) {
        try {
            Year.of(isoYear);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeException for " + isoYear);
    }
}
