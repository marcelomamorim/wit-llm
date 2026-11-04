/*
 * @test
 * @summary Test java.util.Arrays.setAll(long[], IntToLongFunction) method
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntToLongFunction;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testSetAllBasic();
        testSetAllEmptyArray();
        testSetAllNullGenerator();
    }

    private static void testSetAllBasic() {
        long[] array = new long[5];
        IntToLongFunction generator = new IntToLongFunction() {
            public long applyAsLong(int i) {
                return i * 10L;
            }
        };
        Arrays.setAll(array, generator);
        for (int i = 0; i < array.length; i++) {
            if (array[i] != i * 10L) {
                throw new AssertionError("Element at index " + i + " expected " + (i * 10L) + " but was " + array[i]);
            }
        }
    }

    private static void testSetAllEmptyArray() {
        long[] array = new long[0];
        IntToLongFunction generator = new IntToLongFunction() {
            public long applyAsLong(int i) {
                throw new AssertionError("Generator should not be called for empty array");
            }
        };
        Arrays.setAll(array, generator);
    }

    private static void testSetAllNullGenerator() {
        long[] array = new long[1];
        try {
            Arrays.setAll(array, null);
            throw new AssertionError("Expected NullPointerException for null generator");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
