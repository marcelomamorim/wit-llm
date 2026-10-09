/* @test    @summary Test EnumSet.range() method for various enum constants    @run main EnumSetWitupTest_Pe73e0f469b31 */

import java.util.EnumSet;

public class EnumSetWitupTest_Pe73e0f469b31 {

    enum Color { RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO, VIOLET }

    public static void main(String[] args) {
        testRangeNormal();
        testRangeReversed();
        testRangeInvalid();
    }

    static void testRangeNormal() {
        EnumSet<Color> set = EnumSet.range(Color.RED, Color.GREEN);
        assert set.contains(Color.RED) : "Should contain RED";
        assert set.contains(Color.ORANGE) : "Should contain ORANGE";
        assert set.contains(Color.YELLOW) : "Should contain YELLOW";
        assert set.contains(Color.GREEN) : "Should contain GREEN";
        assert !set.contains(Color.BLUE) : "Should not contain BLUE";
    }

    static void testRangeReversed() {
        try {
            EnumSet.range(Color.GREEN, Color.RED);
            assert false : "Expected IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testRangeInvalid() {
        // No invalid enum constants, but test nulls if needed
        // Since method does not accept nulls, skipping null tests
        // Alternatively, test with nulls to ensure NullPointerException
        try {
            EnumSet.range(null, Color.RED);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
        try {
            EnumSet.range(Color.RED, null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
