/*
 * @test
 * @summary Verifies withZoneSameInstant rejects null zones and preserves the instant when changing zones
 * @run main ChronoZonedDateTimeImplWitupTest
 */
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.IsoChronology;

public class ChronoZonedDateTimeImplWitupTest {

    public static void main(String[] args) {
        ZonedDateTime base = ZonedDateTime.of(LocalDateTime.of(2020, 6, 15, 12, 30), ZoneId.of("Europe/Paris"));
        ChronoZonedDateTime<?> z = IsoChronology.INSTANCE.zonedDateTime(base);

        testNullZoneThrowsNpe(z);
        testSameZoneReturnsSameInstance(z);
        testDifferentZonePreservesInstant(z);
    }

    static void testNullZoneThrowsNpe(ChronoZonedDateTime<?> z) {
        try {
            z.withZoneSameInstant(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null zone");
    }

    static void testSameZoneReturnsSameInstance(ChronoZonedDateTime<?> z) {
        ChronoZonedDateTime<?> same = z.withZoneSameInstant(ZoneId.of("Europe/Paris"));
        if (same != z) {
            throw new RuntimeException("expected same instance when zone is unchanged");
        }
    }

    static void testDifferentZonePreservesInstant(ChronoZonedDateTime<?> z) {
        ZoneId utc = ZoneId.of("UTC");
        ChronoZonedDateTime<?> r = z.withZoneSameInstant(utc);
        if (!utc.equals(r.getZone())) {
            throw new RuntimeException("unexpected zone: " + r.getZone());
        }
        if (!z.toInstant().equals(r.toInstant())) {
            throw new RuntimeException("instant changed: " + z.toInstant() + " vs " + r.toInstant());
        }
        String local = r.toLocalDateTime().toString();
        if (!"2020-06-15T10:30".equals(local)) {
            throw new RuntimeException("unexpected local date-time: " + local);
        }
    }
}
