/*
 * @test
 * @summary Witup-generated tests for Arrays.setAll(long[], IntToLongFunction)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.function.IntToLongFunction;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testNullGeneratorThrowsNPE();
        testNullArrayThrowsNPE();
        testFillsValuesFromGenerator();
        testEmptyArrayDoesNotInvokeGenerator();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullGeneratorThrowsNPE() {
        long[] array = new long[3];
        IntToLongFunction generator = null;
        boolean thrown = false;
        try {
            Arrays.setAll(array, generator);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null generator");
        check(array[0] == 0L && array[1] == 0L && array[2] == 0L,
              "array must not be modified when generator is null");
    }

    static void testNullArrayThrowsNPE() {
        IntToLongFunction generator = i -> (long) i;
        boolean thrown = false;
        try {
            Arrays.setAll((long[]) null, generator);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null array");
    }

    static void testFillsValuesFromGenerator() {
        long[] array = new long[5];
        Arrays.setAll(array, i -> (long) i * 2L);
        for (int i = 0; i < array.length; i++) {
            check(array[i] == i * 2L, "unexpected value at index " + i + ": " + array[i]);
        }
    }

    static void testEmptyArrayDoesNotInvokeGenerator() {
        long[] array = new long[0];
        int[] calls = {0};
        Arrays.setAll(array, i -> {
            calls[0]++;
            return 1L;
        });
        check(calls[0] == 0, "generator must not be invoked for empty array");
    }
}
