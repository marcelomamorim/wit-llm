/* @test    @summary Test OffsetDateTime.isAfter method for correctness and regressions    @run main OffsetDateTimeWitupTest */

import java.time.OffsetDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testIsAfterWithDifferentEpochSeconds();
        testIsAfterWithSameEpochSecondsDifferentNano();
        testIsAfterWithSameEpochSecondsAndNano();
        testIsAfterWithEqualOffsetTimes();
        System.out.println("All tests passed.");
    }

    static void testIsAfterWithDifferentEpochSeconds() {
        OffsetDateTime dt1 = OffsetDateTime.of(2023, 3, 25, 10, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime dt2 = OffsetDateTime.of(2023, 3, 25, 9, 59, 59, 999999999, ZoneOffset.UTC);
        if (!dt1.isAfter(dt2)) {
            throw new AssertionError("dt1 should be after dt2");
        }
    }

    static void testIsAfterWithSameEpochSecondsDifferentNano() {
        OffsetDateTime dt1 = OffsetDateTime.of(2023, 3, 25, 10, 0, 0, 500000000, ZoneOffset.UTC);
        OffsetDateTime dt2 = OffsetDateTime.of(2023, 3, 25, 10, 0, 0, 499999999, ZoneOffset.UTC);
        if (!dt1.isAfter(dt2)) {
            throw new AssertionError("dt1 should be after dt2 based on nano");
        }
    }

    static void testIsAfterWithSameEpochSecondsAndNano() {
        OffsetDateTime dt1 = OffsetDateTime.of(2023, 3, 25, 10, 0, 0, 0, ZoneOffset.ofHours(2));
        OffsetDateTime dt2 = OffsetDateTime.of(2023, 3, 25, 8, 0, 0, 0, ZoneOffset.ofHours(0));
        if (dt1.isAfter(dt2)) {
            throw new AssertionError("dt1 should not be after dt2");
        }
    }

    static void testIsAfterWithEqualOffsetTimes() {
        OffsetDateTime dt1 = OffsetDateTime.of(2023, 3, 25, 12, 0, 0, 0, ZoneOffset.ofHours(3));
        OffsetDateTime dt2 = OffsetDateTime.of(2023, 3, 25, 9, 0, 0, 0, ZoneOffset.ofHours(0));
        if (dt1.isAfter(dt2)) {
            throw new AssertionError("dt1 should not be after dt2 with same instant");
        }
    }
}
