/* @test    @summary Test OffsetDateTime.ofInstant with valid inputs    @run main OffsetDateTimeWitupTest */

import java.time.Instant;
import java.time.ZoneId;
import java.time.OffsetDateTime;
import java.time.LocalDateTime;
import java.util.Objects;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testOfInstantWithValidInputs();
        testOfInstantWithNullInstant();
        testOfInstantWithNullZone();
    }

    static void testOfInstantWithValidInputs() {
        Instant instant = Instant.ofEpochSecond(1609459200L, 123456789); // 2021-01-01T00:00:00.123456789Z
        ZoneId zone = ZoneId.of("UTC");
        OffsetDateTime odt = OffsetDateTime.ofInstant(instant, zone);
        assert odt != null : "OffsetDateTime should not be null";
        assert odt.getOffset().equals(zone.getRules().getOffset(instant)) : "Offset mismatch";
        LocalDateTime expectedLdt = LocalDateTime.ofEpochSecond(instant.getEpochSecond(), instant.getNano(), odt.getOffset());
        assert odt.toLocalDateTime().equals(expectedLdt) : "LocalDateTime mismatch";
    }

    static void testOfInstantWithNullInstant() {
        try {
            OffsetDateTime.ofInstant(null, ZoneId.of("UTC"));
            assert false : "Expected NullPointerException for null instant";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testOfInstantWithNullZone() {
        Instant instant = Instant.ofEpochSecond(0L);
        try {
            OffsetDateTime.ofInstant(instant, null);
            assert false : "Expected NullPointerException for null zone";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
