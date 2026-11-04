/*
 * @test
 * @summary Test ChronoZonedDateTimeImpl.withZoneSameInstant(ZoneId) for null argument and zone change
 * @run main ChronoZonedDateTimeImplWitupTest
 */

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.chrono.ChronoZonedDateTime;
import java.time.chrono.ChronoZonedDateTimeImpl;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.chrono.IsoChronology;

public class ChronoZonedDateTimeImplWitupTest {
    public static void main(String[] args) throws Exception {
        testWithZoneSameInstantNullZone();
        testWithZoneSameInstantSameZoneReturnsThis();
        testWithZoneSameInstantDifferentZoneReturnsNew();
    }

    private static void testWithZoneSameInstantNullZone() {
        ChronoZonedDateTime<?> czdt = createSampleChronoZonedDateTime();
        try {
            czdt.withZoneSameInstant(null);
            throw new AssertionError("Expected NullPointerException for null zone");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testWithZoneSameInstantSameZoneReturnsThis() {
        ChronoZonedDateTime<?> czdt = createSampleChronoZonedDateTime();
        ChronoZonedDateTime<?> result = czdt.withZoneSameInstant(czdt.getZone());
        if (result != czdt) {
            throw new AssertionError("Expected same instance when zone is unchanged");
        }
    }

    private static void testWithZoneSameInstantDifferentZoneReturnsNew() {
        ChronoZonedDateTime<?> czdt = createSampleChronoZonedDateTime();
        ZoneId differentZone = ZoneOffset.ofHours(2);
        if (differentZone.equals(czdt.getZone())) {
            differentZone = ZoneOffset.ofHours(3); // ensure different
        }
        ChronoZonedDateTime<?> result = czdt.withZoneSameInstant(differentZone);
        if (result == czdt) {
            throw new AssertionError("Expected new instance when zone is different");
        }
        if (!result.getZone().equals(differentZone)) {
            throw new AssertionError("Result zone should be the new zone");
        }
        if (result.toInstant().equals(czdt.toInstant()) == false) {
            throw new AssertionError("Result instant should be equal to original instant");
        }
    }

    private static ChronoZonedDateTime<?> createSampleChronoZonedDateTime() {
        // Use IsoChronology to create a ChronoZonedDateTimeImpl instance
        LocalDateTime ldt = LocalDateTime.of(2018, 1, 1, 12, 0);
        ZoneOffset offset = ZoneOffset.UTC;
        ZoneId zone = ZoneOffset.UTC;
        return ChronoZonedDateTimeImpl.of(IsoChronology.INSTANCE, ldt, offset, zone);
    }
}
