/* @test    @summary Test OffsetDateTime.isBefore method for correct behavior    @run main OffsetDateTimeWitupTest */

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testIsBeforeDifferentEpochSeconds();
        testIsBeforeSameEpochSecondsDifferentNano();
        testIsBeforeSameEpochSecondsSameNano();
        testIsBeforeOtherAfter();
        testIsBeforeOtherBefore();
    }

    static void testIsBeforeDifferentEpochSeconds() {
        OffsetDateTime dt1 = OffsetDateTime.of(2023, 3, 25, 10, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime dt2 = OffsetDateTime.of(2023, 3, 25, 11, 0, 0, 0, ZoneOffset.UTC);
        assert dt1.isBefore(dt2) : "dt1 should be before dt2";
        assert !dt2.isBefore(dt1) : "dt2 should not be before dt1";
    }

    static void testIsBeforeSameEpochSecondsDifferentNano() {
        OffsetDateTime dt1 = OffsetDateTime.of(2023, 3, 25, 10, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime dt2 = OffsetDateTime.of(2023, 3, 25, 10, 0, 0, 1_000_000_000 - 1, ZoneOffset.UTC);
        assert dt1.isBefore(dt2) : "dt1 should be before dt2 due to nano difference";
        assert !dt2.isBefore(dt1) : "dt2 should not be before dt1";
    }

    static void testIsBeforeSameEpochSecondsSameNano() {
        OffsetDateTime dt1 = OffsetDateTime.of(2023, 3, 25, 10, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime dt2 = OffsetDateTime.of(2023, 3, 25, 10, 0, 0, 0, ZoneOffset.ofHours(2));
        assert !dt1.isBefore(dt2) : "dt1 should not be before dt2";
        assert dt2.isBefore(dt1) : "dt2 should be before dt1";
    }

    static void testIsBeforeOtherAfter() {
        OffsetDateTime dt1 = OffsetDateTime.of(2023, 3, 25, 9, 0, 0, 0, ZoneOffset.ofHours(-1));
        OffsetDateTime dt2 = OffsetDateTime.of(2023, 3, 25, 10, 0, 0, 0, ZoneOffset.UTC);
        assert dt1.isBefore(dt2) : "dt1 should be before dt2";
    }

    static void testIsBeforeOtherBefore() {
        OffsetDateTime dt1 = OffsetDateTime.of(2023, 3, 25, 11, 0, 0, 0, ZoneOffset.UTC);
        OffsetDateTime dt2 = OffsetDateTime.of(2023, 3, 25, 10, 0, 0, 0, ZoneOffset.UTC);
        assert !dt1.isBefore(dt2) : "dt1 should not be before dt2";
    }
}
