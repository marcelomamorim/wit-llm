/*
 * @test
 * @summary Test the toOffsetDateTime() method of ZonedDateTime.
 * @run main ZonedDateTimeWitupTest
 */

import java.time.ZonedDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.LocalDateTime;

public class ZonedDateTimeWitupTest {

    public static void main(String[] args) {
        testToOffsetDateTime();
    }

    public static void testToOffsetDateTime() {
        LocalDateTime ldt = LocalDateTime.of(2023, 3, 15, 12, 30, 45);
        ZoneOffset offset = ZoneOffset.ofHours(2);
        ZonedDateTime zdt = ZonedDateTime.of(ldt, offset);
        OffsetDateTime odt = zdt.toOffsetDateTime();
        assert odt != null : "OffsetDateTime should not be null";
        assert odt.getOffset().equals(offset) : "Offset should match";
        assert odt.toLocalDateTime().equals(ldt) : "LocalDateTime should match";
    }
}
