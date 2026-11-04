/*
 * @test
 * @summary Witup-derived checks for LocalDateTime.of(int, Month, int, int, int, int, int)
 * @run main LocalDateTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.Month;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testValidValues();
        testInvalidDayOfMonthThrows();
        testNullMonthThrowsNPE();
        testInvalidTimeFieldsThrow();
    }

    static void testValidValues() {
        LocalDateTime dt = LocalDateTime.of(2020, Month.FEBRUARY, 29, 23, 59, 58, 123);
        check(dt.getYear() == 2020, "year");
        check(dt.getMonth() == Month.FEBRUARY, "month");
        check(dt.getDayOfMonth() == 29, "day");
        check(dt.getHour() == 23, "hour");
        check(dt.getMinute() == 59, "minute");
        check(dt.getSecond() == 58, "second");
        check(dt.getNano() == 123, "nano");
    }

    static void testInvalidDayOfMonthThrows() {
        expectThrows(DateTimeException.class,
                () -> LocalDateTime.of(2019, Month.FEBRUARY, 29, 0, 0, 0, 0));
        expectThrows(DateTimeException.class,
                () -> LocalDateTime.of(2019, Month.APRIL, 31, 0, 0, 0, 0));
        expectThrows(DateTimeException.class,
                () -> LocalDateTime.of(2019, Month.JANUARY, 0, 0, 0, 0, 0));
        expectThrows(DateTimeException.class,
                () -> LocalDateTime.of(2019, Month.JANUARY, 32, 0, 0, 0, 0));
    }

    static void testNullMonthThrowsNPE() {
        expectThrows(NullPointerException.class,
                () -> LocalDateTime.of(2019, (Month) null, 1, 0, 0, 0, 0));
    }

    static void testInvalidTimeFieldsThrow() {
        expectThrows(DateTimeException.class,
                () -> LocalDateTime.of(2019, Month.JANUARY, 1, 24, 0, 0, 0));
        expectThrows(DateTimeException.class,
                () -> LocalDateTime.of(2019, Month.JANUARY, 1, 0, 60, 0, 0));
        expectThrows(DateTimeException.class,
                () -> LocalDateTime.of(2019, Month.JANUARY, 1, 0, 0, 60, 0));
        expectThrows(DateTimeException.class,
                () -> LocalDateTime.of(2019, Month.JANUARY, 1, 0, 0, 0, 1_000_000_000));
    }

    interface Action {
        void run();
    }

    static void expectThrows(Class<? extends Throwable> expected, Action action) {
        try {
            action.run();
        } catch (Throwable t) {
            if (expected.isInstance(t)) {
                return;
            }
            throw new RuntimeException("Unexpected exception: " + t, t);
        }
        throw new RuntimeException("Expected " + expected.getName() + " was not thrown");
    }

    static void check(boolean condition, String what) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + what);
        }
    }
}
