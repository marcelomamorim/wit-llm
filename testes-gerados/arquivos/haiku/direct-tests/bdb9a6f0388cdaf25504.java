/*
 * @test
 * @summary Checks LocalDateTime.of(int, Month, int, int, int) returns the expected fields and rejects invalid inputs
 * @run main LocalDateTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

public class LocalDateTimeWitupTest {

    public static void main(String[] args) {
        testValidFieldsWithMonthEnum();
        testEquivalenceWithLocalDateAndLocalTime();
        testLeapDayIsAccepted();
        testInvalidDayOfMonthIsRejected();
        testInvalidHourIsRejected();
        testInvalidMinuteIsRejected();
        testNullMonthIsRejected();
    }

    static void testValidFieldsWithMonthEnum() {
        LocalDateTime dt = LocalDateTime.of(2020, Month.JANUARY, 15, 10, 30);
        check(dt.getYear() == 2020, "year");
        check(dt.getMonth() == Month.JANUARY, "month");
        check(dt.getDayOfMonth() == 15, "day");
        check(dt.getHour() == 10, "hour");
        check(dt.getMinute() == 30, "minute");
        check(dt.getSecond() == 0, "second must be zero");
        check(dt.getNano() == 0, "nano must be zero");
    }

    static void testEquivalenceWithLocalDateAndLocalTime() {
        LocalDateTime a = LocalDateTime.of(2020, Month.JANUARY, 15, 10, 30);
        LocalDateTime b = LocalDateTime.of(LocalDate.of(2020, 1, 15), LocalTime.of(10, 30));
        check(a.equals(b), "of(int...) must equal of(LocalDate, LocalTime)");

        LocalDateTime c = LocalDateTime.of(2020, 1, 15, 10, 30);
        check(a.equals(c), "int month overload must equal Month overload");
    }

    static void testLeapDayIsAccepted() {
        LocalDateTime dt = LocalDateTime.of(2020, Month.FEBRUARY, 29, 0, 0);
        check(dt.getMonth() == Month.FEBRUARY, "leap month");
        check(dt.getDayOfMonth() == 29, "leap day");
    }

    static void testInvalidDayOfMonthIsRejected() {
        expectDateTimeException(() -> LocalDateTime.of(2019, Month.FEBRUARY, 29, 0, 0));
    }

    static void testInvalidHourIsRejected() {
        expectDateTimeException(() -> LocalDateTime.of(2020, Month.JANUARY, 1, 24, 0));
    }

    static void testInvalidMinuteIsRejected() {
        expectDateTimeException(() -> LocalDateTime.of(2020, Month.JANUARY, 1, 0, 60));
    }

    static void testNullMonthIsRejected() {
        try {
            LocalDateTime.of(2020, (Month) null, 1, 0, 0);
            throw new RuntimeException("null month must be rejected");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void expectDateTimeException(Runnable r) {
        try {
            r.run();
        } catch (DateTimeException expected) {
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
