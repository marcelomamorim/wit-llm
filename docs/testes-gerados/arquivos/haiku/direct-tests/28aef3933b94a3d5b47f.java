/*
 * @test
 * @summary IsoChronology.dateNow(Clock) returns the date of the clock and rejects null
 * @run main IsoChronologyWitupTest
 */

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.chrono.IsoChronology;

public class IsoChronologyWitupTest {

    public static void main(String[] args) {
        testFixedClockUtc();
        testFixedClockWithOffsetCrossesDate();
        testNullClockThrowsNpe();
    }

    static void testFixedClockUtc() {
        Clock clock = Clock.fixed(Instant.parse("2020-02-29T10:15:30Z"), ZoneOffset.UTC);
        LocalDate actual = IsoChronology.INSTANCE.dateNow(clock);
        LocalDate expected = LocalDate.of(2020, 2, 29);
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }

    static void testFixedClockWithOffsetCrossesDate() {
        Clock clock = Clock.fixed(Instant.parse("2020-01-01T23:30:00Z"), ZoneOffset.ofHours(2));
        LocalDate actual = IsoChronology.INSTANCE.dateNow(clock);
        LocalDate expected = LocalDate.of(2020, 1, 2);
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }

    static void testNullClockThrowsNpe() {
        try {
            IsoChronology.INSTANCE.dateNow(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null clock");
    }
}
