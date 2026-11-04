/*
 * @test
 * @summary Test java.time.OffsetDateTime.isAfter method behavior
 * @run main OffsetDateTimeWitupTest
 */

import java.time.OffsetDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {
    public static void main(String[] args) {
        testIsAfterWithDifferentEpochSeconds();
        testIsAfterWithSameEpochSecondsDifferentNanos();
        testIsAfterWithSameInstantAndNanos();
    }

    private static void testIsAfterWithDifferentEpochSeconds() {
        OffsetDateTime odt1 = OffsetDateTime.of(2020, 1, 1, 0, 0, 1, 0, ZoneOffset.UTC);
        OffsetDateTime odt2 = OffsetDateTime.of(2020, 1, 1, 0, 0, 0, 999_999_999, ZoneOffset.UTC);
        // odt1 is after odt2 because epoch seconds 1 > 0
        if (!odt1.isAfter(odt2)) {
            throw new AssertionError("Expected odt1 to be after odt2 when epoch seconds differ");
        }
        // odt2 is not after odt1
        if (odt2.isAfter(odt1)) {
            throw new AssertionError("Expected odt2 not to be after odt1 when epoch seconds differ");
        }
    }

    private static void testIsAfterWithSameEpochSecondsDifferentNanos() {
        OffsetDateTime odt1 = OffsetDateTime.of(2020, 1, 1, 0, 0, 0, 500, ZoneOffset.UTC);
        OffsetDateTime odt2 = OffsetDateTime.of(2020, 1, 1, 0, 0, 0, 400, ZoneOffset.UTC);
        // same epoch seconds, odt1 nanos > odt2 nanos
        if (!odt1.isAfter(odt2)) {
            throw new AssertionError("Expected odt1 to be after odt2 when nanos differ and epoch seconds equal");
        }
        // odt2 is not after odt1
        if (odt2.isAfter(odt1)) {
            throw new AssertionError("Expected odt2 not to be after odt1 when nanos differ and epoch seconds equal");
        }
    }

    private static void testIsAfterWithSameInstantAndNanos() {
        OffsetDateTime odt1 = OffsetDateTime.of(2020, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime odt2 = OffsetDateTime.of(2020, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);
        // same instant and nanos, neither is after the other
        if (odt1.isAfter(odt2)) {
            throw new AssertionError("Expected odt1 not to be after odt2 when equal");
        }
        if (odt2.isAfter(odt1)) {
            throw new AssertionError("Expected odt2 not to be after odt1 when equal");
        }
    }
}
