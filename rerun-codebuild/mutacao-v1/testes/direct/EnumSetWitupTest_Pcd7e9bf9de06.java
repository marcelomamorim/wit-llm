/*
 * @test
 * @summary Tests the EnumSet.range(E from, E to) method for correctness and exception handling.
 * @run main EnumSetWitupTest_Pcd7e9bf9de06
 */

import java.util.EnumSet;

public class EnumSetWitupTest_Pcd7e9bf9de06 {

    enum Color { RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO, VIOLET }

    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
        testRangeWithSameElement();
        testRangeWithFullSet();
    }

    static void testValidRange() {
        try {
            EnumSet<Color> set = EnumSet.range(Color.YELLOW, Color.GREEN);
            if (!set.contains(Color.YELLOW) || !set.contains(Color.GREEN)) {
                throw new AssertionError("Expected set to contain YELLOW and GREEN");
            }
            if (set.size() != 2) {
                throw new AssertionError("Expected set size 2, but got " + set.size());
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testValidRange: " + e);
        }
    }

    static void testInvalidRange() {
        try {
            EnumSet<Color> set = EnumSet.range(Color.GREEN, Color.YELLOW);
            throw new AssertionError("Expected IllegalArgumentException for invalid range");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    static void testRangeWithSameElement() {
        try {
            EnumSet<Color> set = EnumSet.range(Color.BLUE, Color.BLUE);
            if (!set.contains(Color.BLUE)) {
                throw new AssertionError("Set should contain BLUE");
            }
            if (set.size() != 1) {
                throw new AssertionError("Expected set size 1, but got " + set.size());
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testRangeWithSameElement: " + e);
        }
    }

    static void testRangeWithFullSet() {
        try {
            EnumSet<Color> set = EnumSet.range(Color.RED, Color.VIOLET);
            for (Color c : Color.values()) {
                if (!set.contains(c)) {
                    throw new AssertionError("Set should contain " + c);
                }
            }
            if (set.size() != Color.values().length) {
                throw new AssertionError("Set size mismatch: expected " + Color.values().length + " but got " + set.size());
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testRangeWithFullSet: " + e);
        }
    }
}
