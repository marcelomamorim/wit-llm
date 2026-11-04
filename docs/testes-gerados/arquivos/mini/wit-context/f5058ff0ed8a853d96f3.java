/*
 * @test
 * @summary Test java.util.Arrays.setAll(long[], IntToLongFunction) for null generator and normal operation
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.function.IntToLongFunction;
import java.util.Objects;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testSetAllNullGenerator();
        testSetAllNormal();
    }

    private static void testSetAllNullGenerator() {
        long[] array = new long[3];
        IntToLongFunction generator = null;
        try {
            Arrays.setAll(array, generator);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testSetAllNormal() {
        long[] array = new long[5];
        IntToLongFunction generator = new IntToLongFunction() {
            public long applyAsLong(int index) {
                return index * 10L;
            }
        };
        Arrays.setAll(array, generator);
        for (int i = 0; i < array.length; i++) {
            if (array[i] != i * 10L) {
                throw new RuntimeException("Array element at index " + i + " expected " + (i * 10L) + " but found " + array[i]);
            }
        }
    }
}
