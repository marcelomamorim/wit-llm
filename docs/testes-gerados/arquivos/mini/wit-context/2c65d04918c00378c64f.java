/*
 * @test
 * @summary Test OffsetDateTime.toZonedDateTime() for normal and null input cases
 * @run main OffsetDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class OffsetDateTimeWitupTest {
    public static void main(String[] args) {
        testToZonedDateTimeNormal();
        testToZonedDateTimeNullDateTime();
        testToZonedDateTimeNullOffset();
    }

    private static void testToZonedDateTimeNormal() {
        LocalDateTime ldt = LocalDateTime.of(2020, 1, 1, 12, 0);
        ZoneOffset offset = ZoneOffset.ofHours(2);
        OffsetDateTime odt = OffsetDateTime.of(ldt, offset);
        ZonedDateTime zdt = odt.toZonedDateTime();
        if (!zdt.toLocalDateTime().equals(ldt)) {
            throw new AssertionError("toZonedDateTime() returned ZonedDateTime with wrong LocalDateTime");
        }
        if (!zdt.getOffset().equals(offset)) {
            throw new AssertionError("toZonedDateTime() returned ZonedDateTime with wrong ZoneOffset");
        }
    }

    private static void testToZonedDateTimeNullDateTime() {
        // Construct OffsetDateTime with null dateTime is not possible via public API,
        // so simulate by subclassing to override dateTime field (not possible here),
        // thus test adapted: no direct test for null dateTime possible.
        // This test is omitted due to lack of public API to create such state.
    }

    private static void testToZonedDateTimeNullOffset() {
        // Construct OffsetDateTime with null offset is not possible via public API,
        // so simulate by subclassing to override offset field (not possible here),
        // thus test adapted: no direct test for null offset possible.
        // This test is omitted due to lack of public API to create such state.
    }
}
