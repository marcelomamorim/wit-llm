/*
 * @test
 * @summary Tests observable behavior of EnumSet.range(from, to)
 * @run main EnumSetWitupTest
 */

import java.util.EnumSet;

public class EnumSetWitupTest {

    enum Color { RED, GREEN, BLUE, YELLOW, BLACK }

    public static void main(String[] args) {
        testInclusiveRange();
        testSingleElementRange();
        testFullRange();
        testIterationOrder();
        testReversedRangeThrows();
        testNullFromThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testInclusiveRange() {
        EnumSet<Color> s = EnumSet.range(Color.GREEN, Color.YELLOW);
        check(s.size() == 3, "range GREEN..YELLOW should have 3 elements");
        check(s.contains(Color.GREEN), "range must include lower bound");
        check(s.contains(Color.BLUE), "range must include middle element");
        check(s.contains(Color.YELLOW), "range must include upper bound");
        check(!s.contains(Color.RED), "range must not include RED");
        check(!s.contains(Color.BLACK), "range must not include BLACK");
    }

    static void testSingleElementRange() {
        EnumSet<Color> s = EnumSet.range(Color.BLUE, Color.BLUE);
        check(s.size() == 1, "range X..X should have 1 element");
        check(s.contains(Color.BLUE), "range X..X should contain X");
    }

    static void testFullRange() {
        EnumSet<Color> s = EnumSet.range(Color.RED, Color.BLACK);
        check(s.size() == Color.values().length, "full range should cover all constants");
        check(s.containsAll(EnumSet.allOf(Color.class)), "full range should equal allOf");
    }

    static void testIterationOrder() {
        EnumSet<Color> s = EnumSet.range(Color.GREEN, Color.BLACK);
        Color[] arr = s.toArray(new Color[0]);
        check(arr.length == 4, "range GREEN..BLACK should have 4 elements");
        check(arr[0] == Color.GREEN, "first element should be GREEN");
        check(arr[1] == Color.BLUE, "second element should be BLUE");
        check(arr[2] == Color.YELLOW, "third element should be YELLOW");
        check(arr[3] == Color.BLACK, "fourth element should be BLACK");
    }

    static void testReversedRangeThrows() {
        try {
            EnumSet.range(Color.BLUE, Color.GREEN);
            throw new RuntimeException("expected IllegalArgumentException for reversed range");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testNullFromThrows() {
        Color from = null;
        try {
            EnumSet.range(from, Color.BLUE);
            throw new RuntimeException("expected NullPointerException for null from");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
