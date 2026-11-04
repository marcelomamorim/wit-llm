/* @test    @summary Test OffsetDateTime.toZonedDateTime() method for correct behavior    @run main OffsetDateTimeWitupTest */

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testToZonedDateTime();
    }

    public static void testToZonedDateTime() {
        OffsetDateTime odt = OffsetDateTime.of(2023, 3, 15, 12, 30, 45, 0, ZoneOffset.ofHours(-5));
        ZonedDateTime zdt = odt.toZonedDateTime();
        if (zdt == null) {
            throw new AssertionError("toZonedDateTime() returned null");
        }
        if (!zdt.getOffset().equals(ZoneOffset.ofHours(-5))) {
            throw new AssertionError("Offset mismatch: expected -05:00, got " + zdt.getOffset());
        }
        if (!zdt.toLocalDateTime().equals(odt.toLocalDateTime())) {
            throw new AssertionError("LocalDateTime mismatch: expected " + odt.toLocalDateTime() + ", got " + zdt.toLocalDateTime());
        }
        System.out.println("testToZonedDateTime passed.");
    }
}
