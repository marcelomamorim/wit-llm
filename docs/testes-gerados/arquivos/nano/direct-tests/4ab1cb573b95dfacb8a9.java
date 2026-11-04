/* @test    @summary Test Arrays.parallelPrefix with various DoubleBinaryOperator implementations    @run main ArraysWitupTest */

import java.util.Arrays;
import java.util.function.DoubleBinaryOperator;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testParallelPrefixSum();
        testParallelPrefixProduct();
        testParallelPrefixMax();
        testParallelPrefixMin();
        testParallelPrefixWithNullOperator();
        testParallelPrefixEmptyArray();
    }

    static void testParallelPrefixSum() {
        double[] array = {1.0, 2.0, 3.0, 4.0};
        DoubleBinaryOperator sumOp = (a, b) -> a + b;
        double[] expected = {1.0, 3.0, 6.0, 10.0};
        Arrays.parallelPrefix(array, sumOp);
        assert java.util.Arrays.equals(array, expected) : "Sum prefix failed";
    }

    static void testParallelPrefixProduct() {
        double[] array = {1.0, 2.0, 3.0, 4.0};
        DoubleBinaryOperator prodOp = (a, b) -> a * b;
        double[] expected = {1.0, 2.0, 6.0, 24.0};
        Arrays.parallelPrefix(array, prodOp);
        assert java.util.Arrays.equals(array, expected) : "Product prefix failed";
    }

    static void testParallelPrefixMax() {
        double[] array = {1.0, 3.0, 2.0, 5.0};
        DoubleBinaryOperator maxOp = (a, b) -> a > b ? a : b;
        double[] expected = {1.0, 3.0, 3.0, 5.0};
        Arrays.parallelPrefix(array, maxOp);
        assert java.util.Arrays.equals(array, expected) : "Max prefix failed";
    }

    static void testParallelPrefixMin() {
        double[] array = {5.0, 3.0, 4.0, 2.0};
        DoubleBinaryOperator minOp = (a, b) -> a < b ? a : b;
        double[] expected = {5.0, 3.0, 3.0, 2.0};
        Arrays.parallelPrefix(array, minOp);
        assert java.util.Arrays.equals(array, expected) : "Min prefix failed";
    }

    static void testParallelPrefixWithNullOperator() {
        double[] array = {1.0, 2.0};
        try {
            Arrays.parallelPrefix(array, null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testParallelPrefixEmptyArray() {
        double[] array = {};
        DoubleBinaryOperator sumOp = (a, b) -> a + b;
        Arrays.parallelPrefix(array, sumOp);
        assert java.util.Arrays.equals(array, new double[0]) : "Empty array test failed";
    }
}
