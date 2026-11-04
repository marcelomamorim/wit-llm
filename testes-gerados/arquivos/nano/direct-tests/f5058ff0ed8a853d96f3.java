/* @test    @summary Test Arrays.setAll with a simple index-based generator    @run main ArraysWitupTest */

import java.util.Arrays;
import java.util.function.IntToLongFunction;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testSetAllWithIndexGenerator();
        testSetAllWithNullGenerator();
        testSetAllEmptyArray();
    }

    private static void testSetAllWithIndexGenerator() {
        long[] array = new long[5];
        IntToLongFunction generator = i -> i * 10L;
        Arrays.setAll(array, generator);
        boolean success = true;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != i * 10L) {
                success = false;
                break;
            }
        }
        if (!success) {
            throw new AssertionError("setAll did not produce expected values") ;
        }
    }

    private static void testSetAllWithNullGenerator() {
        long[] array = {1L, 2L};
        try {
            Arrays.setAll(array, null);
            throw new AssertionError("Expected NullPointerException not thrown") ;
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testSetAllEmptyArray() {
        long[] array = new long[0];
        IntToLongFunction generator = i -> 42L;
        Arrays.setAll(array, generator); // should not throw
        if (array.length != 0) {
            throw new AssertionError("Array length changed") ;
        }
    }
}
