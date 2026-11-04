/*
 * @test
 * @summary WITUP-derived checks for Arrays.parallelPrefix(double[], DoubleBinaryOperator)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testNullOperatorThrowsNpe();
        testNullArrayThrowsNpe();
        testPrefixSum();
        testEmptyArrayUnchanged();
    }

    static void testNullOperatorThrowsNpe() {
        double[] a = {1.0, 2.0, 3.0};
        try {
            Arrays.parallelPrefix(a, null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null operator");
    }

    static void testNullArrayThrowsNpe() {
        try {
            Arrays.parallelPrefix(null, (x, y) -> x + y);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null array");
    }

    static void testPrefixSum() {
        double[] a = {1.0, 2.0, 3.0, 4.0};
        Arrays.parallelPrefix(a, (x, y) -> x + y);
        double[] expected = {1.0, 3.0, 6.0, 10.0};
        if (!Arrays.equals(expected, a)) {
            throw new RuntimeException("unexpected prefix result: " + Arrays.toString(a));
        }
    }

    static void testEmptyArrayUnchanged() {
        double[] a = {};
        Arrays.parallelPrefix(a, (x, y) -> x + y);
        if (a.length != 0) {
            throw new RuntimeException("empty array length changed");
        }
    }
}
