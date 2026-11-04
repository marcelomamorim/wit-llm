/*
 * @test
 * @summary Test OffsetDateTime.isEqual method for correctness and null handling
 * @run main OffsetDateTimeWitupTest
 */

import java.time.OffsetDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {
    public static void main(String[] args) {
        testIsEqualTrue();
        testIsEqualFalse();
        testIsEqualNullPointer();
        System.out.println("All tests passed.");
    }

    private static void testIsEqualTrue() {
        OffsetDateTime dt1 = OffsetDateTime.of(LocalDate.of(2020, 1, 1), LocalTime.of(12, 0, 0, 123456789), ZoneOffset.UTC);
        OffsetDateTime dt2 = OffsetDateTime.of(LocalDate.of(2020, 1, 1), LocalTime.of(12, 0, 0, 123456789), ZoneOffset.UTC);
        if (!dt1.isEqual(dt2)) {
            throw new AssertionError("Expected isEqual to return true for identical date-times");
        }
    }

    private static void testIsEqualFalse() {
        OffsetDateTime dt1 = OffsetDateTime.of(LocalDate.of(2020, 1, 1), LocalTime.of(12, 0, 0, 0), ZoneOffset.UTC);
        OffsetDateTime dt2 = OffsetDateTime.of(LocalDate.of(2020, 1, 1), LocalTime.of(12, 0, 0, 1), ZoneOffset.UTC);
        if (dt1.isEqual(dt2)) {
            throw new AssertionError("Expected isEqual to return false for different nanoseconds");
        }
        OffsetDateTime dt3 = OffsetDateTime.of(LocalDate.of(2020, 1, 1), LocalTime.of(12, 0, 1, 0), ZoneOffset.UTC);
        if (dt1.isEqual(dt3)) {
            throw new AssertionError("Expected isEqual to return false for different seconds");
        }
    }

    private static void testIsEqualNullPointer() {
        OffsetDateTime dt = OffsetDateTime.of(LocalDate.of(2020, 1, 1), LocalTime.NOON, ZoneOffset.UTC);
        try {
            dt.isEqual(null);
            throw new AssertionError("Expected NullPointerException when passing null to isEqual");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
