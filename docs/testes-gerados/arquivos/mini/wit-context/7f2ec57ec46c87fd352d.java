/*
 * @test
 * @summary Test OffsetDateTime.ofInstant(Instant, ZoneId) for null checks and normal behavior
 * @run main OffsetDateTimeWitupTest
 */

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.zone.ZoneRules;
import java.util.Objects;

public class OffsetDateTimeWitupTest {
    public static void main(String[] args) {
        testNullInstant();
        testNullZone();
        testValidInstantAndZone();
    }

    private static void testNullInstant() {
        try {
            OffsetDateTime.ofInstant(null, ZoneOffset.UTC);
            throw new RuntimeException("Expected NullPointerException for null instant");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testNullZone() {
        Instant now = Instant.now();
        try {
            OffsetDateTime.ofInstant(now, null);
            throw new RuntimeException("Expected NullPointerException for null zone");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testValidInstantAndZone() {
        Instant instant = Instant.ofEpochSecond(1000L, 123456789);
        ZoneId zone = ZoneOffset.ofHoursMinutes(2, 30);
        OffsetDateTime odt = OffsetDateTime.ofInstant(instant, zone);

        // Check that the returned OffsetDateTime has the expected offset
        ZoneRules rules = zone.getRules();
        ZoneOffset expectedOffset = rules.getOffset(instant);

        if (!odt.getOffset().equals(expectedOffset)) {
            throw new RuntimeException("Offset mismatch: expected " + expectedOffset + " but got " + odt.getOffset());
        }

        // Check that the LocalDateTime corresponds to the instant and offset
        LocalDateTime expectedLdt = LocalDateTime.ofEpochSecond(instant.getEpochSecond(), instant.getNano(), expectedOffset);
        if (!odt.toLocalDateTime().equals(expectedLdt)) {
            throw new RuntimeException("LocalDateTime mismatch: expected " + expectedLdt + " but got " + odt.toLocalDateTime());
        }
    }
}
