/*
 * @test
 * @summary Witup-derived tests for Arrays.setAll(int[], IntUnaryOperator)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.function.IntUnaryOperator;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testNullGeneratorThrowsNPE();
        testNullGeneratorWithEmptyArrayThrowsNPE();
        testNullArrayThrowsNPE();
        testFillsWithGeneratorValues();
        testEmptyArrayIsUnchanged();
    }

    static void testNullGeneratorThrowsNPE() {
        int[] a = new int[3];
        try {
            Arrays.setAll(a, null);
            throw new RuntimeException("expected NullPointerException for null generator");
        } catch (NullPointerException expected) {
        }
        for (int v : a) {
            if (v != 0) {
                throw new RuntimeException("array modified despite null generator");
            }
        }
    }

    static void testNullGeneratorWithEmptyArrayThrowsNPE() {
        int[] a = new int[0];
        try {
            Arrays.setAll(a, null);
            throw new RuntimeException("expected NullPointerException for null generator with empty array");
        } catch (NullPointerException expected) {
        }
    }

    static void testNullArrayThrowsNPE() {
        IntUnaryOperator gen = i -> i;
        try {
            Arrays.setAll((int[]) null, gen);
            throw new RuntimeException("expected NullPointerException for null array");
        } catch (NullPointerException expected) {
        }
    }

    static void testFillsWithGeneratorValues() {
        int[] a = new int[5];
        Arrays.setAll(a, i -> i * i);
        int[] expected = {0, 1, 4, 9, 16};
        if (!Arrays.equals(a, expected)) {
            throw new RuntimeException("unexpected contents: " + Arrays.toString(a));
        }
    }

    static void testEmptyArrayIsUnchanged() {
        int[] a = new int[0];
        Arrays.setAll(a, i -> 42);
        if (a.length != 0) {
            throw new RuntimeException("empty array length changed");
        }
    }
}
