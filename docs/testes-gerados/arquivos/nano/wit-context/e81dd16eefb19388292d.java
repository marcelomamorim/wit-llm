/* @test    @summary Test OffsetDateTime.of(LocalDateTime, ZoneOffset) for expected behavior and exceptions    @run main OffsetDateTimeWitupTest */

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.OffsetDateTime;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testValidOffsetDateTime();
        testNullLocalDateTime();
        testNullZoneOffset();
    }

    static void testValidOffsetDateTime() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 10, 30, 45, 123456789);
        ZoneOffset offset = ZoneOffset.ofHoursMinutes(2, 0);
        OffsetDateTime odt = OffsetDateTime.of(dateTime, offset);
        if (odt == null) {
            throw new AssertionError("OffsetDateTime.of() returned null for valid inputs");
        }
        // Additional observable checks can be added if needed
    }

    static void testNullLocalDateTime() {
        try {
            OffsetDateTime.of(null, ZoneOffset.UTC);
            throw new AssertionError("Expected NullPointerException for null LocalDateTime");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullZoneOffset() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 3, 15, 10, 30, 45, 123456789);
        try {
            OffsetDateTime.of(dateTime, null);
            throw new AssertionError("Expected NullPointerException for null ZoneOffset");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
