/* @test    @summary Test for OffsetDateTime.isAfter method to verify behavior with valid and null inputs    @run main OffsetDateTimeWitupTest */

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testIsAfterWithValidDates();
        testIsAfterWithNull();
    }

    static void testIsAfterWithValidDates() {
        OffsetDateTime dt1 = OffsetDateTime.of(2023, 3, 15, 10, 0, 0, 0, ZoneOffset.ofHours(2));
        OffsetDateTime dt2 = OffsetDateTime.of(2023, 3, 15, 9, 59, 59, 999999999, ZoneOffset.ofHours(2));
        boolean result = dt1.isAfter(dt2);
        if (!result) {
            throw new AssertionError("Expected dt1 to be after dt2");
        }
    }

    static void testIsAfterWithNull() {
        OffsetDateTime dt = OffsetDateTime.of(2023, 3, 15, 10, 0, 0, 0, ZoneOffset.ofHours(2));
        try {
            dt.isAfter(null);
            throw new AssertionError("Expected NullPointerException when argument is null");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
