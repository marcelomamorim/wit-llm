/* @test    @summary Test for Arrays.replaceAll method to ensure it applies the operator correctly.    @run main ArraysWitupTest */

import java.util.Arrays;
import java.util.function.UnaryOperator;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testReplaceAllWithIncrement();
        testReplaceAllWithSquare();
        testReplaceAllWithNullOperator();
    }

    static void testReplaceAllWithIncrement() {
        Integer[] array = {1, 2, 3};
        Arrays.replaceAll(array, new UnaryOperator<Integer>() {
            public Integer apply(Integer x) {
                return x + 1;
            }
        });
        assert array[0] == 2 : "Expected 2, got " + array[0];
        assert array[1] == 3 : "Expected 3, got " + array[1];
        assert array[2] == 4 : "Expected 4, got " + array[2];
    }

    static void testReplaceAllWithSquare() {
        Double[] array = {1.0, 2.0, 3.0};
        Arrays.replaceAll(array, new UnaryOperator<Double>() {
            public Double apply(Double x) {
                return x * x;
            }
        });
        assert array[0] == 1.0 : "Expected 1.0, got " + array[0];
        assert array[1] == 4.0 : "Expected 4.0, got " + array[1];
        assert array[2] == 9.0 : "Expected 9.0, got " + array[2];
    }

    static void testReplaceAllWithNullOperator() {
        Integer[] array = {1, 2, 3};
        try {
            Arrays.replaceAll(array, null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
