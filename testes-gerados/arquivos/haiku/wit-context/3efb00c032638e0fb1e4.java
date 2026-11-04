/*
 * @test
 * @summary WITUP-derived tests for OffsetDateTime.of(LocalDate, LocalTime, ZoneOffset)
 * @run main OffsetDateTimeWitupTest
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testValidArguments();
        testNullDateThrowsNpe();
        testNullTimeThrowsNpe();
        testNullOffsetThrowsNpe();
    }

    static void testValidArguments() {
        LocalDate date = LocalDate.of(2020, 1, 15);
        LocalTime time = LocalTime.of(10, 30);
        ZoneOffset offset = ZoneOffset.ofHours(2);
        OffsetDateTime odt = OffsetDateTime.of(date, time, offset);
        check(odt.getYear() == 2020, "year");
        check(odt.getMonthValue() == 1, "month");
        check(odt.getDayOfMonth() == 15, "day");
        check(odt.getHour() == 10, "hour");
        check(odt.getMinute() == 30, "minute");
        check(odt.getOffset().equals(offset), "offset");
        check("2020-01-15T10:30+02:00".equals(odt.toString()), "toString: " + odt);
    }

    static void testNullDateThrowsNpe() {
        expectNpe(() -> OffsetDateTime.of(null, LocalTime.NOON, ZoneOffset.UTC));
    }

    static void testNullTimeThrowsNpe() {
        expectNpe(() -> OffsetDateTime.of(LocalDate.of(2020, 1, 15), null, ZoneOffset.UTC));
    }

    static void testNullOffsetThrowsNpe() {
        expectNpe(() -> OffsetDateTime.of(LocalDate.of(2020, 1, 15), LocalTime.NOON, null));
    }

    interface Action {
        void run();
    }

    static void expectNpe(Action action) {
        try {
            action.run();
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException was not thrown");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
