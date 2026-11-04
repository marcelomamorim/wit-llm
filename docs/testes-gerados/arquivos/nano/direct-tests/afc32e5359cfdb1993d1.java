/* @test    @summary Test Arrays.fill for double[] with various values    @run main ArraysWitupTest */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testFillWithZero();
        testFillWithPositive();
        testFillWithNegative();
        testFillWithLargeNumber();
        testFillEmptyArray();
    }

    static void testFillWithZero() {
        double[] array = {1.0, 2.0, 3.0};
        Arrays.fill(array, 0.0);
        assert array.length == 3 : "Array length mismatch";
        for (double v : array) {
            assert v == 0.0 : "Expected 0.0, but got " + v;
        }
    }

    static void testFillWithPositive() {
        double[] array = {1.0, 2.0, 3.0};
        Arrays.fill(array, 5.5);
        for (double v : array) {
            assert v == 5.5 : "Expected 5.5, but got " + v;
        }
    }

    static void testFillWithNegative() {
        double[] array = {1.0, 2.0, 3.0};
        Arrays.fill(array, -7.7);
        for (double v : array) {
            assert v == -7.7 : "Expected -7.7, but got " + v;
        }
    }

    static void testFillWithLargeNumber() {
        double[] array = {1.0, 2.0, 3.0};
        double largeNumber = 1e308;
        Arrays.fill(array, largeNumber);
        for (double v : array) {
            assert v == largeNumber : "Expected " + largeNumber + ", but got " + v;
        }
    }

    static void testFillEmptyArray() {
        double[] array = {};
        Arrays.fill(array, 9.9);
        assert array.length == 0 : "Array should be empty";
    }
}
