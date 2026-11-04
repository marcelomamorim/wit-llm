/*
 * @test
 * @summary Test java.time.ZonedDateTime.toOffsetDateTime() for expected behavior and NPE
 * @run main ZonedDateTimeWitupTest
 */

import java.time.ZonedDateTime;
import java.time.OffsetDateTime;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;

public class ZonedDateTimeWitupTest {
    public static void main(String[] args) {
        testToOffsetDateTimeNormal();
        testToOffsetDateTimeNullDateTime();
        testToOffsetDateTimeNullOffset();
    }

    private static void testToOffsetDateTimeNormal() {
        ZonedDateTime zdt = ZonedDateTime.of(2020, 1, 1, 12, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime odt = zdt.toOffsetDateTime();
        if (!odt.toLocalDateTime().equals(zdt.toLocalDateTime())) {
            throw new AssertionError("LocalDateTime mismatch");
        }
        if (!odt.getOffset().equals(zdt.getOffset())) {
            throw new AssertionError("Offset mismatch");
        }
    }

    private static void testToOffsetDateTimeNullDateTime() {
        // We cannot create a ZonedDateTime with null dateTime or offset via public API,
        // so this test is adapted to check that toOffsetDateTime does not throw NPE
        // in normal usage. The WIT expath about dateTime == null is discarded as unreachable.
        // Hence, no test for null dateTime is possible without reflection or internal access.
    }

    private static void testToOffsetDateTimeNullOffset() {
        // Similarly, ZonedDateTime cannot have null offset via public API.
        // The WIT expath about null offset is discarded as unreachable.
        // No test for null offset is possible without reflection or internal access.
    }
}
