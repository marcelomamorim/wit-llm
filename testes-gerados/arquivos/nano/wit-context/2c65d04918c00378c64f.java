/* @test    @summary Tests for OffsetDateTime.toZonedDateTime() method.    @run main OffsetDateTimeWitupTest */

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.LocalDateTime;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testToZonedDateTime_NullOffset();
        testToZonedDateTime_NullDateTime();
    }

    private static void testToZonedDateTime_NullOffset() {
        try {
            OffsetDateTime odt = OffsetDateTime.of(LocalDateTime.of(2023, 1, 1, 12, 0), null);
            odt.toZonedDateTime();
            System.out.println("FAIL: Expected NullPointerException for null offset");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null offset");
        }
    }

    private static void testToZonedDateTime_NullDateTime() {
        try {
            OffsetDateTime odt = OffsetDateTime.of(null, ZoneOffset.UTC);
            odt.toZonedDateTime();
            System.out.println("FAIL: Expected NullPointerException for null dateTime");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null dateTime");
        }
    }
}
