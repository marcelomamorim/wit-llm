/* @test    @summary Test for Arrays.setAll method to ensure correct array initialization.    @run main ArraysWitupTest */

import java.util.Arrays;
import java.util.Objects;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testSetAllWithPositiveIndex();
        testSetAllWithZeroIndex();
        testSetAllWithNegativeIndex();
        testSetAllWithNullGenerator();
    }

    static void testSetAllWithPositiveIndex() {
        Integer[] array = new Integer[5];
        Arrays.setAll(array, i -> i * 10);
        for (int i = 0; i < array.length; i++) {
            assert array[i] != null : "Element at index " + i + " should not be null";
            assert array[i] == i * 10 : "Expected " + (i * 10) + " at index " + i + " but found " + array[i];
        }
    }

    static void testSetAllWithZeroIndex() {
        String[] array = new String[3];
        Arrays.setAll(array, i -> "zero");
        for (int i = 0; i < array.length; i++) {
            assert "zero".equals(array[i]) : "Element at index " + i + " should be 'zero'";
        }
    }

    static void testSetAllWithNegativeIndex() {
        Integer[] array = new Integer[2];
        Arrays.setAll(array, i -> -i);
        assert array[0] == 0 : "Expected 0 at index 0 but found " + array[0];
        assert array[1] == -1 : "Expected -1 at index 1 but found " + array[1];
    }

    static void testSetAllWithNullGenerator() {
        Integer[] array = new Integer[1];
        try {
            Arrays.setAll(array, null);
            assert false : "Expected NullPointerException when generator is null";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
