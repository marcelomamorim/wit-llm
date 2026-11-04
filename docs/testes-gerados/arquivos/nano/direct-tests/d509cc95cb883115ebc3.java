/* @test    @summary Test ValueRange.of(long, long, long) method for various inputs    @run main ValueRangeWitupTest */

import java.time.temporal.ValueRange;

public class ValueRangeWitupTest {

    public static void main(String[] args) {
        testValidRange();
        testMinEqualsMaxSmallestEqualsMaxLargest();
        testInvalidRange();
        testEdgeCases();
    }

    static void testValidRange() {
        ValueRange vr = ValueRange.of(1L, 5L, 10L);
        assert vr != null : "ValueRange should not be null";
        assert vr.isValidValue(1L) : "1 should be valid";
        assert vr.isValidValue(5L) : "5 should be valid";
        assert vr.isValidValue(10L) : "10 should be valid";
        assert !vr.isValidValue(0L) : "0 should be invalid";
        assert !vr.isValidValue(11L) : "11 should be invalid";
    }

    static void testMinEqualsMaxSmallestEqualsMaxLargest() {
        ValueRange vr = ValueRange.of(3L, 3L, 3L);
        assert vr != null : "ValueRange should not be null";
        assert vr.isValidValue(3L) : "3 should be valid";
        assert !vr.isValidValue(2L) : "2 should be invalid";
        assert !vr.isValidValue(4L) : "4 should be invalid";
    }

    static void testInvalidRange() {
        ValueRange vr = ValueRange.of(10L, 5L, 8L);
        assert vr != null : "ValueRange should not be null";
        // Since the range is invalid, isValidValue should return false for all
        assert !vr.isValidValue(4L) : "4 should be invalid";
        assert !vr.isValidValue(9L) : "9 should be invalid";
        assert !vr.isValidValue(10L) : "10 should be invalid";
    }

    static void testEdgeCases() {
        ValueRange vr = ValueRange.of(Long.MIN_VALUE, Long.MAX_VALUE, Long.MAX_VALUE);
        assert vr != null : "ValueRange should not be null";
        assert vr.isValidValue(Long.MIN_VALUE) : "Long.MIN_VALUE should be valid";
        assert vr.isValidValue(Long.MAX_VALUE) : "Long.MAX_VALUE should be valid";
        assert vr.isValidValue(0L) : "0 should be valid";
    }
}
