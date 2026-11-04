/*
 * @test
 * @summary Test OffsetDateTime.isBefore(OffsetDateTime) method behavior and null handling
 * @run main OffsetDateTimeWitupTest
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {
    public static void main(String[] args) {
        testIsBeforeBasic();
        testIsBeforeWithSameInstantDifferentNanos();
        testIsBeforeNullArgument();
    }

    private static void testIsBeforeBasic() {
        OffsetDateTime dt1 = OffsetDateTime.of(2020, 1, 1, 10, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime dt2 = OffsetDateTime.of(2020, 1, 1, 11, 0, 0, 0, ZoneOffset.UTC);
        if (!dt1.isBefore(dt2)) {
            throw new AssertionError("Expected dt1 to be before dt2");
        }
        if (dt2.isBefore(dt1)) {
            throw new AssertionError("Expected dt2 not to be before dt1");
        }
    }

    private static void testIsBeforeWithSameInstantDifferentNanos() {
        // Same epoch second but different nanos
        OffsetDateTime dt1 = OffsetDateTime.of(2020, 1, 1, 10, 0, 0, 100, ZoneOffset.UTC);
        OffsetDateTime dt2 = OffsetDateTime.of(2020, 1, 1, 10, 0, 0, 200, ZoneOffset.UTC);
        if (!dt1.isBefore(dt2)) {
            throw new AssertionError("Expected dt1 to be before dt2 based on nanos");
        }
        if (dt2.isBefore(dt1)) {
            throw new AssertionError("Expected dt2 not to be before dt1 based on nanos");
        }
    }

    private static void testIsBeforeNullArgument() {
        OffsetDateTime dt = OffsetDateTime.of(LocalDate.of(2020, 1, 1), LocalTime.NOON, ZoneOffset.UTC);
        try {
            dt.isBefore(null);
            throw new AssertionError("Expected NullPointerException when passing null to isBefore");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }
}
