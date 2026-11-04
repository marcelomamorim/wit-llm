/*
 * @test
 * @summary Test ChronoZonedDateTimeImpl.withZoneSameInstant(ZoneId) method
 * @run main ChronoZonedDateTimeImplWitupTest
 */

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.LocalDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.ChronoZonedDateTimeImpl;
import java.util.Objects;

public class ChronoZonedDateTimeImplWitupTest {

    public static void main(String[] args) {
        testWithZoneSameInstantReturnsSameInstanceForSameZone();
        testWithZoneSameInstantReturnsNewInstanceForDifferentZone();
        testWithZoneSameInstantRejectsNullZone();
    }

    private static void testWithZoneSameInstantReturnsSameInstanceForSameZone() {
        ZoneId zone = ZoneOffset.UTC;
        LocalDateTime ldt = LocalDateTime.of(2000, 1, 1, 0, 0);
        ChronoZonedDateTimeImpl<?> czdt = ChronoZonedDateTimeImpl.of(ldt, zone);
        ChronoZonedDateTime<?> result = czdt.withZoneSameInstant(zone);
        if (result != czdt) {
            throw new AssertionError("Expected same instance when zone is unchanged");
        }
    }

    private static void testWithZoneSameInstantReturnsNewInstanceForDifferentZone() {
        ZoneId zone1 = ZoneOffset.UTC;
        ZoneId zone2 = ZoneOffset.ofHours(1);
        LocalDateTime ldt = LocalDateTime.of(2000, 1, 1, 0, 0);
        ChronoZonedDateTimeImpl<?> czdt = ChronoZonedDateTimeImpl.of(ldt, zone1);
        ChronoZonedDateTime<?> result = czdt.withZoneSameInstant(zone2);
        if (result == czdt) {
            throw new AssertionError("Expected different instance when zone is changed");
        }
        // Check that the instant is the same
        if (!result.toInstant().equals(czdt.toInstant())) {
            throw new AssertionError("Expected same instant after zone change");
        }
        // Check that the zone is updated
        if (!result.getZone().equals(zone2)) {
            throw new AssertionError("Expected zone to be updated to new zone");
        }
    }

    private static void testWithZoneSameInstantRejectsNullZone() {
        ZoneId zone = ZoneOffset.UTC;
        LocalDateTime ldt = LocalDateTime.of(2000, 1, 1, 0, 0);
        ChronoZonedDateTimeImpl<?> czdt = ChronoZonedDateTimeImpl.of(ldt, zone);
        try {
            czdt.withZoneSameInstant(null);
            throw new AssertionError("Expected NullPointerException when zone is null");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
