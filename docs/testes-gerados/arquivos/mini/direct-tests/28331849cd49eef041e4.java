/*
 * @test
 * @summary Test java.time.ZonedDateTime.toOffsetDateTime() method
 * @run main ZonedDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class ZonedDateTimeWitupTest {
    public static void main(String[] args) {
        testToOffsetDateTimeBasic();
        testToOffsetDateTimeWithDifferentOffset();
    }

    private static void testToOffsetDateTimeBasic() {
        ZonedDateTime zdt = ZonedDateTime.of(2018, 9, 25, 10, 15, 30, 123456789, ZoneId.of("UTC"));
        OffsetDateTime odt = zdt.toOffsetDateTime();
        // The offset should be UTC
        if (!odt.getOffset().equals(ZoneOffset.UTC)) {
            throw new AssertionError("Expected offset UTC but got " + odt.getOffset());
        }
        // The local date-time should be equal
        if (!odt.toLocalDateTime().equals(zdt.toLocalDateTime())) {
            throw new AssertionError("Expected local date-time to match");
        }
    }

    private static void testToOffsetDateTimeWithDifferentOffset() {
        ZoneOffset offset = ZoneOffset.ofHoursMinutes(5, 30); // +05:30
        ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.of(2020, 1, 1, 0, 0), offset);
        OffsetDateTime odt = zdt.toOffsetDateTime();
        if (!odt.getOffset().equals(offset)) {
            throw new AssertionError("Expected offset +05:30 but got " + odt.getOffset());
        }
        if (!odt.toLocalDateTime().equals(zdt.toLocalDateTime())) {
            throw new AssertionError("Expected local date-time to match");
        }
    }
}
