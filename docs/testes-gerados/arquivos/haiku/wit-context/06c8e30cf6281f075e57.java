/*
 * @test
 * @summary Witup-derived checks for YearMonth.withYear(int) range validation
 * @run main YearMonthWitupTest
 */
import java.time.DateTimeException;
import java.time.YearMonth;

public class YearMonthWitupTest {
    interface Action {
        void run();
    }

    public static void main(String[] args) {
        YearMonth ym = YearMonth.of(2000, 5);

        YearMonth r = ym.withYear(2020);
        check(r.getYear() == 2020, "withYear(2020) must set the year");
        check(r.getMonthValue() == 5, "withYear must preserve the month");

        check(ym.withYear(999999999).getYear() == 999999999, "maximum valid year must be accepted");
        check(ym.withYear(-999999999).getYear() == -999999999, "minimum valid year must be accepted");

        expectDateTimeException(new Action() {
            public void run() {
                ym.withYear(1000000000);
            }
        });
        expectDateTimeException(new Action() {
            public void run() {
                ym.withYear(-1000000000);
            }
        });
    }

    static void expectDateTimeException(Action a) {
        try {
            a.run();
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
