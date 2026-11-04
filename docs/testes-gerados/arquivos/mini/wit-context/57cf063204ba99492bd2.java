/*
 * @test
 * @summary Deterministic tests for java.time.OffsetDateTime.isAfter(OffsetDateTime)
 * @run main java.time.OffsetDateTimeWitupTest
 */

import java.time.OffsetDateTime;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {
    public static void main(String[] args) {
        testIsAfterTrue();
        testIsAfterFalse();
        testIsAfterEqualButNanos();
        testIsAfterNullArgument();
    }

    private static void testIsAfterTrue() {
        OffsetDateTime dt1 = OffsetDateTime.of(2020, 1, 1, 12, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime dt2 = OffsetDateTime.of(2019, 12, 31, 23, 59, 59, 999_999_999, ZoneOffset.UTC);
        if (!dt1.isAfter(dt2)) {
            throw new AssertionError("Expected dt1 to be after dt2");
        }
    }

    private static void testIsAfterFalse() {
        OffsetDateTime dt1 = OffsetDateTime.of(2020, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime dt2 = OffsetDateTime.of(2020, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);
        if (dt1.isAfter(dt2)) {
            throw new AssertionError("Expected dt1 not to be after dt2 when equal");
        }
    }

    private static void testIsAfterEqualButNanos() {
        OffsetDateTime dt1 = OffsetDateTime.of(2020, 1, 1, 0, 0, 0, 1, ZoneOffset.UTC);
        OffsetDateTime dt2 = OffsetDateTime.of(2020, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);
        if (!dt1.isAfter(dt2)) {
            throw new AssertionError("Expected dt1 to be after dt2 due to nanos");
        }
    }

    private static void testIsAfterNullArgument() {
        OffsetDateTime dt1 = OffsetDateTime.of(2020, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);
        try {
            dt1.isAfter(null);
            throw new AssertionError("Expected NullPointerException when argument is null");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
