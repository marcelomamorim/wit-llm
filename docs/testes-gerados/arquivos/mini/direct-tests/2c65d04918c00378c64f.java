/*
 * @test
 * @summary Test java.time.OffsetDateTime.toZonedDateTime() method
 * @run main OffsetDateTimeWitupTest
 */

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class OffsetDateTimeWitupTest {
    public static void main(String[] args) {
        // Create an OffsetDateTime instance with known values
        LocalDateTime ldt = LocalDateTime.of(2018, 9, 25, 15, 30, 45, 123456789);
        ZoneOffset offset = ZoneOffset.ofHoursMinutes(2, 30);
        OffsetDateTime odt = OffsetDateTime.of(ldt, offset);

        // Call toZonedDateTime and verify the result
        ZonedDateTime zdt = odt.toZonedDateTime();

        // Check that the ZonedDateTime has the same LocalDateTime and ZoneOffset
        if (!zdt.toLocalDateTime().equals(ldt)) {
            throw new AssertionError("ZonedDateTime local date-time does not match OffsetDateTime local date-time");
        }
        if (!zdt.getOffset().equals(offset)) {
            throw new AssertionError("ZonedDateTime offset does not match OffsetDateTime offset");
        }

        // Check that the zone is a fixed offset zone with the same offset
        if (!zdt.getZone().getRules().getOffset(ldt).equals(offset)) {
            throw new AssertionError("ZonedDateTime zone offset rules do not match OffsetDateTime offset");
        }

        System.out.println("Test passed.");
    }
}
