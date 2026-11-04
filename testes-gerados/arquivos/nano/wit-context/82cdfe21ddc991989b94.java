/*
 * @test
 * @summary Test for OffsetDateTime.isEqual method, covering normal and null cases.
 * @run main OffsetDateTimeWitupTest
 */

import java.time.OffsetDateTime;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testIsEqualWithSameEpochSeconds();
        testIsEqualWithDifferentEpochSeconds();
        testIsEqualWithNull();
    }

    static void testIsEqualWithSameEpochSeconds() {
        OffsetDateTime dt1 = OffsetDateTime.of(2023, 3, 15, 12, 0, 0, 0, OffsetDateTime.now().getOffset());
        OffsetDateTime dt2 = OffsetDateTime.of(2023, 3, 15, 12, 0, 0, 0, dt1.getOffset());
        boolean result = dt1.isEqual(dt2);
        if (!result) {
            throw new AssertionError("Expected dt1.isEqual(dt2) to be true");
        }
    }

    static void testIsEqualWithDifferentEpochSeconds() {
        OffsetDateTime dt1 = OffsetDateTime.of(2023, 3, 15, 12, 0, 0, 0, OffsetDateTime.now().getOffset());
        OffsetDateTime dt2 = OffsetDateTime.of(2023, 3, 15, 12, 0, 1, 0, dt1.getOffset());
        boolean result = dt1.isEqual(dt2);
        if (result) {
            throw new AssertionError("Expected dt1.isEqual(dt2) to be false");
        }
    }

    static void testIsEqualWithNull() {
        OffsetDateTime dt1 = OffsetDateTime.now();
        try {
            dt1.isEqual(null);
            throw new AssertionError("Expected NullPointerException when passing null");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
