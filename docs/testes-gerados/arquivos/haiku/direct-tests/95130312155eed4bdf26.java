/*
 * @test
 * @summary Behavioral tests for Arrays.copyOfRange(float[], int, int)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAIL: " + msg);
        }
    }

    static void testBasicRange() {
        float[] a = {1.0f, 2.0f, 3.0f, 4.0f};
        float[] r = Arrays.copyOfRange(a, 1, 3);
        check(Arrays.equals(r, new float[]{2.0f, 3.0f}), "basic range");
    }

    static void testPaddingWhenToExceedsLength() {
        float[] a = {1.0f, 2.0f, 3.0f};
        float[] r = Arrays.copyOfRange(a, 1, 5);
        check(Arrays.equals(r, new float[]{2.0f, 3.0f, 0.0f, 0.0f}),
              "padding with zeros when to > length");
    }

    static void testEmptyRange() {
        float[] a = {1.0f, 2.0f, 3.0f};
        float[] r = Arrays.copyOfRange(a, 2, 2);
        check(r.length == 0, "from == to yields empty array");
    }

    static void testFromAtEndYieldsEmpty() {
        float[] a = {1.0f, 2.0f, 3.0f};
        float[] r = Arrays.copyOfRange(a, 3, 3);
        check(r.length == 0, "from == to == length yields empty array");
    }

    static void testReturnsNewArray() {
        float[] a = {1.0f, 2.0f, 3.0f};
        float[] r = Arrays.copyOfRange(a, 0, 3);
        check(r != a, "result must be a new array");
        r[0] = 99.0f;
        check(a[0] == 1.0f, "modifying result must not modify original");
    }

    static void testPreservesSpecialValues() {
        float[] a = {-0.0f, Float.NaN};
        float[] r = Arrays.copyOfRange(a, 0, 2);
        check(Float.floatToRawIntBits(r[0]) == Float.floatToRawIntBits(-0.0f),
              "negative zero preserved");
        check(Float.isNaN(r[1]), "NaN preserved");
    }

    static void testFromGreaterThanToThrows() {
        float[] a = {1.0f, 2.0f, 3.0f};
        boolean thrown = false;
        try {
            Arrays.copyOfRange(a, 2, 1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "from > to must throw IllegalArgumentException");
    }

    static void testNullArrayThrowsNpe() {
        boolean thrown = false;
        try {
            Arrays.copyOfRange((float[]) null, 0, 0);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null array must throw NullPointerException");
    }

    public static void main(String[] args) {
        testBasicRange();
        testPaddingWhenToExceedsLength();
        testEmptyRange();
        testFromAtEndYieldsEmpty();
        testReturnsNewArray();
        testPreservesSpecialValues();
        testFromGreaterThanToThrows();
        testNullArrayThrowsNpe();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
