/*
 * @test
 * @summary Test OffsetDateTime.atZoneSameInstant(ZoneId) for null argument handling and normal behavior
 * @run main OffsetDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Objects;

public class OffsetDateTimeWitupTest {
    public static void main(String[] args) {
        testAtZoneSameInstantWithValidZone();
        testAtZoneSameInstantWithNullZone();
    }

    private static void testAtZoneSameInstantWithValidZone() {
        // Create OffsetDateTime instance
        OffsetDateTime odt = OffsetDateTime.of(2020, 1, 1, 12, 0, 0, 0, ZoneOffset.UTC);
        ZoneId zone = ZoneId.of("Europe/Paris");
        ZonedDateTime zdt = odt.atZoneSameInstant(zone);
        // The instant represented by zdt should be equal to odt.toInstant() with zone applied
        if (!zdt.toInstant().equals(odt.toInstant())) {
            throw new AssertionError("ZonedDateTime instant does not match OffsetDateTime instant");
        }
        if (!zdt.getZone().equals(zone)) {
            throw new AssertionError("ZonedDateTime zone is not the expected zone");
        }
    }

    private static void testAtZoneSameInstantWithNullZone() {
        OffsetDateTime odt = OffsetDateTime.of(2020, 1, 1, 12, 0, 0, 0, ZoneOffset.UTC);
        try {
            odt.atZoneSameInstant(null);
            throw new AssertionError("Expected NullPointerException when zone is null");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
