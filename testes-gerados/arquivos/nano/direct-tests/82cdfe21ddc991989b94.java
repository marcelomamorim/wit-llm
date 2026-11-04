/* @test    @summary Test OffsetDateTime.isEqual method for various scenarios    @run main OffsetDateTimeWitupTest */

import java.time.OffsetDateTime;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class OffsetDateTimeWitupTest {

    public static void main(String[] args) {
        testIsEqualSameEpochSecondAndNano();
        testIsEqualDifferentEpochSecond();
        testIsEqualDifferentNano();
        testIsEqualDifferentOffset();
        testIsEqualNull();
    }

    static void testIsEqualSameEpochSecondAndNano() {
        OffsetDateTime dt1 = OffsetDateTime.of(LocalDateTime.of(2023, 3, 25, 10, 30, 15, 123456789), ZoneOffset.ofHours(2));
        OffsetDateTime dt2 = OffsetDateTime.of(LocalDateTime.of(2023, 3, 25, 8, 30, 15, 123456789), ZoneOffset.ofHours(0));
        boolean result = dt1.isEqual(dt2);
        if (!result) {
            throw new AssertionError("Expected dt1 to be equal to dt2");
        }
    }

    static void testIsEqualDifferentEpochSecond() {
        OffsetDateTime dt1 = OffsetDateTime.of(LocalDateTime.of(2023, 3, 25, 10, 30, 15, 0), ZoneOffset.ofHours(2));
        OffsetDateTime dt2 = OffsetDateTime.of(LocalDateTime.of(2023, 3, 25, 10, 30, 16, 0), ZoneOffset.ofHours(2));
        boolean result = dt1.isEqual(dt2);
        if (result) {
            throw new AssertionError("Expected dt1 not to be equal to dt2");
        }
    }

    static void testIsEqualDifferentNano() {
        OffsetDateTime dt1 = OffsetDateTime.of(LocalDateTime.of(2023, 3, 25, 10, 30, 15, 123456789), ZoneOffset.ofHours(2));
        OffsetDateTime dt2 = OffsetDateTime.of(LocalDateTime.of(2023, 3, 25, 10, 30, 15, 987654321), ZoneOffset.ofHours(2));
        boolean result = dt1.isEqual(dt2);
        if (result) {
            throw new AssertionError("Expected dt1 not to be equal to dt2");
        }
    }

    static void testIsEqualDifferentOffset() {
        OffsetDateTime dt1 = OffsetDateTime.of(LocalDateTime.of(2023, 3, 25, 10, 30, 15, 0), ZoneOffset.ofHours(2));
        OffsetDateTime dt2 = OffsetDateTime.of(LocalDateTime.of(2023, 3, 25, 8, 30, 15, 0), ZoneOffset.ofHours(0));
        boolean result = dt1.isEqual(dt2);
        if (result) {
            throw new AssertionError("Expected dt1 not to be equal to dt2");
        }
    }

    static void testIsEqualNull() {
        OffsetDateTime dt1 = OffsetDateTime.of(LocalDateTime.of(2023, 3, 25, 10, 30, 15, 0), ZoneOffset.ofHours(2));
        try {
            dt1.isEqual(null);
            throw new AssertionError("Expected NullPointerException when comparing with null");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
