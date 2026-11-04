/* @test    @summary Test for Arrays.copyOfRange(double[] original, int from, int to) method    @run main ArraysWitupTest */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testCopyOfRangeNormal();
        testCopyOfRangeFromEqualsTo();
        testCopyOfRangeFromNegative();
        testCopyOfRangeToExceedsLength();
        testCopyOfRangeToLessThanFrom();
    }

    static void testCopyOfRangeNormal() {
        double[] original = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] result = Arrays.copyOfRange(original, 1, 4);
        assert result.length == 3 : "Expected length 3";
        assert result[0] == 2.0 : "Expected 2.0";
        assert result[1] == 3.0 : "Expected 3.0";
        assert result[2] == 4.0 : "Expected 4.0";
    }

    static void testCopyOfRangeFromEqualsTo() {
        double[] original = {1.0, 2.0, 3.0};
        double[] result = Arrays.copyOfRange(original, 2, 2);
        assert result.length == 0 : "Expected empty array";
    }

    static void testCopyOfRangeFromNegative() {
        double[] original = {1.0, 2.0, 3.0};
        try {
            Arrays.copyOfRange(original, -1, 2);
            assert false : "Expected IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testCopyOfRangeToExceedsLength() {
        double[] original = {1.0, 2.0, 3.0};
        double[] result = Arrays.copyOfRange(original, 1, 5);
        assert result.length == 4 : "Expected length 4";
        assert result[0] == 2.0 : "Expected 2.0";
        assert result[1] == 3.0 : "Expected 3.0";
        assert result[2] == 0.0 : "Expected default 0.0";
        assert result[3] == 0.0 : "Expected default 0.0";
    }

    static void testCopyOfRangeToLessThanFrom() {
        double[] original = {1.0, 2.0, 3.0};
        try {
            Arrays.copyOfRange(original, 2, 1);
            assert false : "Expected IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
