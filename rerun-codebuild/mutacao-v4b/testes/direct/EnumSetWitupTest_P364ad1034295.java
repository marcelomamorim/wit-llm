/* @test
   @summary Test EnumSet.range() with valid enum constants.
   @run main EnumSetWitupTest_P364ad1034295
 */
import java.util.EnumSet;
import java.util.Arrays;

public class EnumSetWitupTest_P364ad1034295 {
    enum Color { RED, GREEN, BLUE, YELLOW }

    public static void main(String[] args) {
        testRangeValid();
        testRangeInvalid();
        testRangeSame();
        testRangeReverse();
    }

    static void testRangeValid() {
        EnumSet<Color> set = EnumSet.range(Color.RED, Color.YELLOW);
        assert set.contains(Color.RED) : "Should contain RED";
        assert set.contains(Color.GREEN) : "Should contain GREEN";
        assert set.contains(Color.BLUE) : "Should contain BLUE";
        assert set.contains(Color.YELLOW) : "Should contain YELLOW";
        assert set.size() == 4 : "Size should be 4";
    }

    static void testRangeInvalid() {
        try {
            EnumSet.range(Color.YELLOW, Color.RED);
            throw new AssertionError("Expected IllegalArgumentException for reverse range");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testRangeSame() {
        EnumSet<Color> set = EnumSet.range(Color.GREEN, Color.GREEN);
        assert set.contains(Color.GREEN) : "Should contain GREEN";
        assert set.size() == 1 : "Size should be 1";
    }

    static void testRangeReverse() {
        try {
            EnumSet.range(Color.BLUE, Color.RED);
            throw new AssertionError("Expected IllegalArgumentException for reverse range");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
