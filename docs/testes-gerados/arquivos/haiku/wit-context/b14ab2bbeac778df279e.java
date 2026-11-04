/*
 * @test
 * @summary Witup-generated checks for LocalTime.from(TemporalAccessor)
 * @run main LocalTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testNullThrowsNullPointerException();
        testExtractsTimeFromLocalDateTime();
        testReturnsTimeFromLocalTime();
        testDateOnlyThrowsDateTimeException();
    }

    static void testNullThrowsNullPointerException() {
        boolean thrown = false;
        try {
            LocalTime.from(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "from(null) must throw NullPointerException");
    }

    static void testExtractsTimeFromLocalDateTime() {
        LocalDateTime dateTime = LocalDateTime.of(2020, 1, 1, 10, 15, 30);
        LocalTime result = LocalTime.from(dateTime);
        check(LocalTime.of(10, 15, 30).equals(result),
                "from(LocalDateTime) must return its time part, got " + result);
    }

    static void testReturnsTimeFromLocalTime() {
        LocalTime time = LocalTime.of(23, 59, 58, 123456789);
        LocalTime result = LocalTime.from(time);
        check(time.equals(result),
                "from(LocalTime) must return an equal time, got " + result);
    }

    static void testDateOnlyThrowsDateTimeException() {
        boolean thrown = false;
        try {
            LocalTime.from(LocalDate.of(2020, 1, 1));
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "from(LocalDate) must throw DateTimeException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
