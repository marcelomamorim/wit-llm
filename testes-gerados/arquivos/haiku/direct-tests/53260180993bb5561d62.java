/*
 * @test
 * @summary ZonedDateTime.now(Clock) observable behavior with fixed clocks
 * @run main ZonedDateTimeWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        testNullClockThrowsNpeWithClockMessage();
        testUtcFixedClock();
        testParisFixedClockOffset();
        testNanosecondsPreserved();
    }

    static void testNullClockThrowsNpeWithClockMessage() {
        try {
            ZonedDateTime.now((Clock) null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException e) {
            if (!"clock".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
    }

    static void testUtcFixedClock() {
        Instant instant = Instant.ofEpochSecond(0L);
        Clock clock = Clock.fixed(instant, ZoneOffset.UTC);
        ZonedDateTime now = ZonedDateTime.now(clock);
        if (!now.toInstant().equals(instant)) {
            throw new RuntimeException("instant mismatch: " + now);
        }
        if (!now.getZone().equals(ZoneOffset.UTC)) {
            throw new RuntimeException("zone mismatch: " + now.getZone());
        }
        if (now.getYear() != 1970 || now.getMonthValue() != 1 || now.getDayOfMonth() != 1
                || now.getHour() != 0 || now.getMinute() != 0 || now.getSecond() != 0) {
            throw new RuntimeException("local date-time mismatch: " + now);
        }
    }

    static void testParisFixedClockOffset() {
        Instant instant = Instant.parse("2018-09-01T12:00:00Z");
        ZoneId paris = ZoneId.of("Europe/Paris");
        Clock clock = Clock.fixed(instant, paris);
        ZonedDateTime now = ZonedDateTime.now(clock);
        if (now.getHour() != 14) {
            throw new RuntimeException("expected hour 14 but was " + now.getHour());
        }
        if (now.getOffset().getTotalSeconds() != 7200) {
            throw new RuntimeException("expected offset +02:00 but was " + now.getOffset());
        }
        if (!now.toInstant().equals(instant)) {
            throw new RuntimeException("instant mismatch: " + now);
        }
    }

    static void testNanosecondsPreserved() {
        Instant instant = Instant.ofEpochSecond(1L, 500L);
        Clock clock = Clock.fixed(instant, ZoneOffset.UTC);
        ZonedDateTime now = ZonedDateTime.now(clock);
        if (now.getNano() != 500) {
            throw new RuntimeException("expected nano 500 but was " + now.getNano());
        }
    }
}
