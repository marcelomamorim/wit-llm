/*
 * @test
 * @summary Verifies OffsetDateTime.of(LocalDate, LocalTime, ZoneOffset) components, equivalence and null handling
 * @run main OffsetDateTimeWitupTest
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testComponents();
        testEquivalentToLocalDateTimeFactory();
        testNanosPreserved();
        testNullDate();
        testNullTime();
        testNullOffset();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectNpe(Runnable action, String message) {
        try {
            action.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException(message);
    }

    static void testComponents() {
        OffsetDateTime odt = OffsetDateTime.of(
                LocalDate.of(2018, 9, 15),
                LocalTime.of(10, 30, 5),
                ZoneOffset.ofHours(2));
        check(odt.getYear() == 2018, "year");
        check(odt.getMonthValue() == 9, "month");
        check(odt.getDayOfMonth() == 15, "day");
        check(odt.getHour() == 10, "hour");
        check(odt.getMinute() == 30, "minute");
        check(odt.getSecond() == 5, "second");
        check(odt.getOffset().equals(ZoneOffset.ofHours(2)), "offset");
    }

    static void testEquivalentToLocalDateTimeFactory() {
        LocalDate date = LocalDate.of(2000, 2, 29);
        LocalTime time = LocalTime.of(23, 59, 59, 999_999_999);
        ZoneOffset offset = ZoneOffset.ofHoursMinutes(-3, -30);
        OffsetDateTime a = OffsetDateTime.of(date, time, offset);
        OffsetDateTime b = OffsetDateTime.of(LocalDateTime.of(date, time), offset);
        check(a.equals(b), "of(date,time,offset) must equal of(dateTime,offset)");
        check(a.toLocalDateTime().equals(LocalDateTime.of(date, time)), "local date-time");
        check(a.getOffset().equals(offset), "offset preserved");
    }

    static void testNanosPreserved() {
        OffsetDateTime odt = OffsetDateTime.of(
                LocalDate.of(2018, 1, 1),
                LocalTime.of(1, 2, 3, 456),
                ZoneOffset.UTC);
        check(odt.getNano() == 456, "nano preserved");
        check(odt.getOffset().equals(ZoneOffset.UTC), "UTC offset preserved");
    }

    static void testNullDate() {
        expectNpe(() -> OffsetDateTime.of(null, LocalTime.NOON, ZoneOffset.UTC),
                "null date must throw NullPointerException");
    }

    static void testNullTime() {
        expectNpe(() -> OffsetDateTime.of(LocalDate.of(2018, 1, 1), null, ZoneOffset.UTC),
                "null time must throw NullPointerException");
    }

    static void testNullOffset() {
        expectNpe(() -> OffsetDateTime.of(LocalDate.of(2018, 1, 1), LocalTime.NOON, null),
                "null offset must throw NullPointerException");
    }
}
