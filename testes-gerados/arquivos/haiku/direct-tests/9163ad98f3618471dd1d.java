/*
 * @test
 * @summary Witup-style tests for Year.plusYears covering zero, ordinary, boundary and overflow cases
 * @run main YearWitupTest
 */

import java.time.DateTimeException;
import java.time.Year;

public class YearWitupTest {

    public static void main(String[] args) {
        Year base = Year.of(2000);

        check(base.plusYears(0) == base, "plusYears(0) must return the same instance");
        check(base.plusYears(5).getValue() == 2005, "2000 plus 5 must be 2005");
        check(base.plusYears(-2000).getValue() == 0, "2000 plus -2000 must be 0");
        check(base.plusYears(-2001).getValue() == -1, "2000 plus -2001 must be -1");

        check(Year.of(999999999).plusYears(0).getValue() == 999999999, "max year plus 0 must be unchanged");
        check(Year.of(-999999999).plusYears(0).getValue() == -999999999, "min year plus 0 must be unchanged");

        expectDateTimeException(() -> Year.of(999999999).plusYears(1), "max year plus 1 must fail");
        expectDateTimeException(() -> Year.of(-999999999).plusYears(-1), "min year minus 1 must fail");
        expectDateTimeException(() -> Year.of(1).plusYears(Long.MAX_VALUE), "overflow from positive year must fail");
        expectDateTimeException(() -> Year.of(-1).plusYears(Long.MAX_VALUE), "large positive delta must fail");
        expectDateTimeException(() -> Year.of(2000).plusYears(Long.MIN_VALUE), "large negative delta must fail");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void expectDateTimeException(Runnable action, String message) {
        try {
            action.run();
        } catch (DateTimeException expected) {
            return;
        }
        throw new RuntimeException(message);
    }
}
