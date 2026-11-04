/*
 * @test
 * @summary Test java.util.Arrays.setAll method for normal and exceptional behavior
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntFunction;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testSetAllNormal();
        testSetAllNullGenerator();
    }

    private static void testSetAllNormal() {
        Integer[] array = new Integer[5];
        IntFunction<Integer> generator = new IntFunction<Integer>() {
            public Integer apply(int i) {
                return i * 2;
            }
        };
        Arrays.setAll(array, generator);
        for (int i = 0; i < array.length; i++) {
            if (!Objects.equals(array[i], i * 2)) {
                throw new AssertionError("Element at index " + i + " expected " + (i * 2) + " but was " + array[i]);
            }
        }
    }

    private static void testSetAllNullGenerator() {
        Integer[] array = new Integer[1];
        try {
            Arrays.setAll(array, null);
            throw new AssertionError("Expected NullPointerException for null generator");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
