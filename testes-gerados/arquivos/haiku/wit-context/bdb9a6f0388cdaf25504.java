/*
 * @test
 * @summary Witup-style tests for LocalDateTime.of(int, Month, int, int, int)
 * @run main LocalDateTimeWitupTest
 */
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        LocalDateTime dt = LocalDateTime.of(2018, Month.FEBRUARY, 3, 4, 5);
        check("2018-02-03T04:05".equals(dt.toString()), "valid date and time: " + dt);

        LocalDateTime leap = LocalDateTime.of(2016, Month.FEBRUARY, 29, 23, 59);
        check("2016-02-29T23:59".equals(leap.toString()), "valid leap day: " + leap);

        expectNullPointerException(() -> LocalDateTime.of(2018, null, 3, 4, 5), "null month");

        expectDateTimeException(() -> LocalDateTime.of(1000000000, Month.JANUARY, 1, 0, 0), "year above range");
        expectDateTimeException(() -> LocalDateTime.of(2018, Month.JANUARY, 0, 0, 0), "day of month zero");
        expectDateTimeException(() -> LocalDateTime.of(2018, Month.JANUARY, 32, 0, 0), "day of month above month length");
        expectDateTimeException(() -> LocalDateTime.of(2018, Month.FEBRUARY, 29, 0, 0), "non-leap February 29");
        expectDateTimeException(() -> LocalDateTime.of(2018, Month.JANUARY, 1, 24, 0), "hour above range");
        expectDateTimeException(() -> LocalDateTime.of(2018, Month.JANUARY, 1, -1, 0), "negative hour");
        expectDateTimeException(() -> LocalDateTime.of(2018, Month.JANUARY, 1, 0, 60), "minute above range");
        expectDateTimeException(() -> LocalDateTime.of(2018, Month.JANUARY, 1, 0, -1), "negative minute");
    }

    private interface Action {
        void run();
    }

    private static void expectNullPointerException(Action action, String label) {
        try {
            action.run();
        } catch (NullPointerException e) {
            return;
        } catch (RuntimeException e) {
            throw new RuntimeException("Unexpected exception for " + label + ": " + e, e);
        }
        throw new RuntimeException("Expected NullPointerException for " + label);
    }

    private static void expectDateTimeException(Action action, String label) {
        try {
            action.run();
        } catch (DateTimeException e) {
            return;
        } catch (RuntimeException e) {
            throw new RuntimeException("Unexpected exception for " + label + ": " + e, e);
        }
        throw new RuntimeException("Expected DateTimeException for " + label);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
