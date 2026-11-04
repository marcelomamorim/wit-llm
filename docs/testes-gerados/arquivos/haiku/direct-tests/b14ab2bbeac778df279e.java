/*
 * @test
 * @summary LocalTime.from(TemporalAccessor) extracts time, rejects null and rejects temporals without time
 * @run main LocalTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalTimeWitupTest {

    public static void main(String[] args) {
        testExtractFromLocalDateTime();
        testFromLocalTimeReturnsEqualTime();
        testNullThrowsNullPointerException();
        testTemporalWithoutTimeThrowsDateTimeException();
        System.out.println("All LocalTime.from tests passed");
    }

    static void testExtractFromLocalDateTime() {
        LocalTime expected = LocalTime.of(10, 20, 30, 400);
        LocalDateTime dt = LocalDateTime.of(LocalDate.of(2020, 1, 2), expected);
        LocalTime actual = LocalTime.from(dt);
        check(expected.equals(actual), "expected " + expected + " but got " + actual);
        check(actual.getHour() == 10, "hour mismatch");
        check(actual.getMinute() == 20, "minute mismatch");
        check(actual.getSecond() == 30, "second mismatch");
        check(actual.getNano() == 400, "nano mismatch");
    }

    static void testFromLocalTimeReturnsEqualTime() {
        LocalTime t = LocalTime.of(23, 59, 58);
        LocalTime actual = LocalTime.from(t);
        check(t.equals(actual), "from(LocalTime) should return equal time");
    }

    static void testNullThrowsNullPointerException() {
        boolean thrown = false;
        try {
            LocalTime.from(null);
        } catch (NullPointerException e) {
            thrown = true;
            check("temporal".equals(e.getMessage()), "unexpected NPE message: " + e.getMessage());
        }
        check(thrown, "expected NullPointerException for null temporal");
    }

    static void testTemporalWithoutTimeThrowsDateTimeException() {
        boolean thrown = false;
        try {
            LocalTime.from(LocalDate.of(2020, 1, 2));
        } catch (DateTimeException e) {
            thrown = true;
            check(e.getMessage() != null
                    && e.getMessage().startsWith("Unable to obtain LocalTime from TemporalAccessor"),
                    "unexpected DateTimeException message: " + e.getMessage());
        }
        check(thrown, "expected DateTimeException for LocalDate without time");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
