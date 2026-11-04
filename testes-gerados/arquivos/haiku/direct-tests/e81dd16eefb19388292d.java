/*
 * @test
 * @summary Verifies OffsetDateTime.of(LocalDateTime, ZoneOffset) preserves its inputs and handles null arguments
 * @run main OffsetDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {
    public static void main(String[] args) {
        LocalDateTime ldt = LocalDateTime.of(2018, 9, 15, 10, 30, 45, 123456789);
        ZoneOffset off = ZoneOffset.ofHours(2);
        OffsetDateTime odt = OffsetDateTime.of(ldt, off);

        check(odt != null, "result must not be null");
        check(odt.toLocalDateTime().equals(ldt), "local date-time must be preserved");
        check(odt.getOffset().equals(off), "offset must be preserved");
        check(odt.getYear() == 2018, "year mismatch");
        check(odt.getMonthValue() == 9, "month mismatch");
        check(odt.getDayOfMonth() == 15, "day mismatch");
        check(odt.getHour() == 10, "hour mismatch");
        check(odt.getMinute() == 30, "minute mismatch");
        check(odt.getSecond() == 45, "second mismatch");
        check(odt.getNano() == 123456789, "nano mismatch");

        check(odt.equals(OffsetDateTime.of(2018, 9, 15, 10, 30, 45, 123456789, off)),
              "must equal the component-based factory result");

        OffsetDateTime utc = OffsetDateTime.of(ldt, ZoneOffset.UTC);
        check(!odt.equals(utc), "different offsets must produce different values");
        check(utc.toEpochSecond() - odt.toEpochSecond() == 7200L,
              "instant must differ by the offset difference");

        expectNpe(() -> OffsetDateTime.of(null, off), "null dateTime must throw NullPointerException");
        expectNpe(() -> OffsetDateTime.of(ldt, null), "null offset must throw NullPointerException");
    }

    private interface Action {
        void run();
    }

    private static void expectNpe(Action action, String msg) {
        try {
            action.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError(msg);
    }

    private static void check(boolean condition, String msg) {
        if (!condition) {
            throw new AssertionError(msg);
        }
    }
}
