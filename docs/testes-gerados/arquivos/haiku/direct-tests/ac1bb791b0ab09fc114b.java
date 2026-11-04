/*
 * @test
 * @summary Behavioral tests for YearMonth.of(int year, Month month)
 * @run main YearMonthWitupTest
 */
import java.time.DateTimeException;
import java.time.Month;
import java.time.YearMonth;

public class YearMonthWitupTest {
    public static void main(String[] args) {
        for (Month m : Month.values()) {
            YearMonth ym = YearMonth.of(2000, m);
            check(ym.getYear() == 2000, "year must be preserved for " + m);
            check(ym.getMonthValue() == m.getValue(), "month value must match Month for " + m);
        }
        check(YearMonth.of(2012, Month.FEBRUARY).equals(YearMonth.of(2012, 2)),
              "Month overload must equal int overload");
        check(YearMonth.of(-5, Month.JANUARY).getYear() == -5,
              "negative year must be preserved");
        expectNullPointerException();
        expectDateTimeException();
    }

    static void expectNullPointerException() {
        try {
            YearMonth.of(2012, (Month) null);
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null month");
    }

    static void expectDateTimeException() {
        try {
            YearMonth.of(1000000000, Month.MAY);
        } catch (DateTimeException e) {
            return;
        }
        throw new AssertionError("expected DateTimeException for out-of-range year");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
