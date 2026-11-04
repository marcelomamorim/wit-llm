/*
 * @test
 * @summary WITUP-derived behavioral tests for Year.minusYears(long)
 * @run main YearWitupTest
 */

import java.time.DateTimeException;
import java.time.Year;

public class YearWitupTest {

    public static void main(String[] args) {
        check(Year.of(2000).minusYears(5).getValue() == 1995, "minusYears(5) from 2000 should be 1995");
        check(Year.of(2000).minusYears(0).getValue() == 2000, "minusYears(0) should keep the year");
        check(Year.of(2000).minusYears(-3).getValue() == 2003, "minusYears(-3) should add three years");
        check(Year.of(2000).minusYears(2001).getValue() == -1, "minusYears(2001) from 2000 should cross year zero");

        expectDateTimeException(() -> Year.of(2000).minusYears(Long.MIN_VALUE),
                "minusYears(Long.MIN_VALUE) should throw DateTimeException");

        expectDateTimeException(() -> Year.of(999999999).minusYears(-1),
                "minusYears(-1) at upper bound should throw DateTimeException");
        expectDateTimeException(() -> Year.of(-999999999).minusYears(1),
                "minusYears(1) at lower bound should throw DateTimeException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void expectDateTimeException(Runnable action, String message) {
        try {
            action.run();
        } catch (DateTimeException e) {
            return;
        }
        throw new AssertionError(message);
    }
}
