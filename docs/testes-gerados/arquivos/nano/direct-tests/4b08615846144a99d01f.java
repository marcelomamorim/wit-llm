/* @test    @summary Test Arrays.setAll for int[] with simple generator    @run main ArraysWitupTest */

import java.util.Arrays;
import java.util.function.IntUnaryOperator;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testSetAllWithValidGenerator();
        testSetAllWithNullGenerator();
        testSetAllEmptyArray();
    }

    static void testSetAllWithValidGenerator() {
        int[] array = {0, 0, 0};
        IntUnaryOperator generator = i -> i * 10;
        Arrays.setAll(array, generator);
        assert array.length == 3 : "Array length mismatch";
        assert array[0] == 0 : "First element should be 0";
        assert array[1] == 10 : "Second element should be 10";
        assert array[2] == 20 : "Third element should be 20";
    }

    static void testSetAllWithNullGenerator() {
        int[] array = {1, 2, 3};
        try {
            Arrays.setAll(array, null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testSetAllEmptyArray() {
        int[] array = {};
        IntUnaryOperator generator = i -> 42;
        Arrays.setAll(array, generator);
        assert array.length == 0 : "Empty array should remain empty";
    }
}
