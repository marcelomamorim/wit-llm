/*
 * @test
 * @summary Test java.time.OffsetDateTime.isBefore(OffsetDateTime) method
 * @run main OffsetDateTimeWitupTest
 */

import java.time.OffsetDateTime;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {
    public static void main(String[] args) {
        testIsBeforeDifferentEpochSeconds();
        testIsBeforeSameEpochSecondsDifferentNanos();
        testIsBeforeEqual();
    }

    private static void testIsBeforeDifferentEpochSeconds() {
        OffsetDateTime dt1 = OffsetDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime dt2 = OffsetDateTime.of(2000, 1, 1, 0, 0, 1, 0, ZoneOffset.UTC);
        if (!dt1.isBefore(dt2)) {
            throw new AssertionError("Expected dt1 to be before dt2 when epoch seconds differ");
        }
        if (dt2.isBefore(dt1)) {
            throw new AssertionError("Expected dt2 not to be before dt1 when epoch seconds differ");
        }
    }

    private static void testIsBeforeSameEpochSecondsDifferentNanos() {
        // Both have same epoch second but different nanos
        OffsetDateTime dt1 = OffsetDateTime.of(2000, 1, 1, 0, 0, 0, 100, ZoneOffset.UTC);
        OffsetDateTime dt2 = OffsetDateTime.of(2000, 1, 1, 0, 0, 0, 200, ZoneOffset.UTC);
        if (!dt1.isBefore(dt2)) {
            throw new AssertionError("Expected dt1 to be before dt2 when nanos differ and epoch seconds equal");
        }
        if (dt2.isBefore(dt1)) {
            throw new AssertionError("Expected dt2 not to be before dt1 when nanos differ and epoch seconds equal");
        }
    }

    private static void testIsBeforeEqual() {
        OffsetDateTime dt1 = OffsetDateTime.of(2000, 1, 1, 12, 0, 0, 123456789, ZoneOffset.UTC);
        OffsetDateTime dt2 = OffsetDateTime.of(2000, 1, 1, 12, 0, 0, 123456789, ZoneOffset.UTC);
        if (dt1.isBefore(dt2)) {
            throw new AssertionError("Expected dt1 not to be before dt2 when both are equal");
        }
        if (dt2.isBefore(dt1)) {
            throw new AssertionError("Expected dt2 not to be before dt1 when both are equal");
        }
    }
}
