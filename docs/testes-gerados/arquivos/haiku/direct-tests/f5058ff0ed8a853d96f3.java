/*
 * @test
 * @summary Tests for Arrays.setAll(long[], IntToLongFunction) observable behavior
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.function.IntToLongFunction;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testFillsValuesByIndex();
        testGeneratorCalledInIndexOrder();
        testEmptyArrayDoesNotCallGenerator();
        testNullGeneratorThrowsNpe();
        testNullArrayThrowsNpe();
    }

    static void testFillsValuesByIndex() {
        long[] a = new long[5];
        Arrays.setAll(a, i -> i * 2L);
        long[] expected = {0L, 2L, 4L, 6L, 8L};
        if (!Arrays.equals(a, expected)) {
            throw new RuntimeException("unexpected values: " + Arrays.toString(a));
        }
    }

    static void testGeneratorCalledInIndexOrder() {
        final StringBuilder calls = new StringBuilder();
        long[] a = new long[4];
        Arrays.setAll(a, i -> {
            calls.append(i).append(',');
            return 10L + i;
        });
        if (!calls.toString().equals("0,1,2,3,")) {
            throw new RuntimeException("unexpected call order: " + calls);
        }
        long[] expected = {10L, 11L, 12L, 13L};
        if (!Arrays.equals(a, expected)) {
            throw new RuntimeException("unexpected values: " + Arrays.toString(a));
        }
    }

    static void testEmptyArrayDoesNotCallGenerator() {
        final int[] count = {0};
        IntToLongFunction g = i -> {
            count[0]++;
            return i;
        };
        Arrays.setAll(new long[0], g);
        if (count[0] != 0) {
            throw new RuntimeException("generator called " + count[0] + " times");
        }
    }

    static void testNullGeneratorThrowsNpe() {
        try {
            Arrays.setAll(new long[3], (IntToLongFunction) null);
            throw new RuntimeException("NullPointerException expected");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullArrayThrowsNpe() {
        try {
            Arrays.setAll((long[]) null, i -> 1L);
            throw new RuntimeException("NullPointerException expected");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
