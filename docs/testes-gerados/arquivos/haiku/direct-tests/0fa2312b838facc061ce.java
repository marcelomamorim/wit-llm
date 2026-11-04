/*
 * @test
 * @summary Behavioral tests for Arrays.copyOfRange(long[], int, int)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testInsideRange() {
        long[] src = {1L, 2L, 3L, 4L};
        long[] r = Arrays.copyOfRange(src, 1, 3);
        check(r.length == 2, "length expected 2");
        check(r[0] == 2L && r[1] == 3L, "values expected {2,3}");
    }

    static void testPaddingBeyondEnd() {
        long[] src = {1L, 2L, 3L, 4L};
        long[] r = Arrays.copyOfRange(src, 2, 6);
        check(r.length == 4, "length expected 4");
        check(r[0] == 3L && r[1] == 4L && r[2] == 0L && r[3] == 0L,
              "values expected {3,4,0,0}");
    }

    static void testEmptyRange() {
        long[] src = {1L, 2L};
        long[] r = Arrays.copyOfRange(src, 1, 1);
        check(r.length == 0, "length expected 0");
    }

    static void testEmptyArrayEmptyRange() {
        long[] r = Arrays.copyOfRange(new long[0], 0, 0);
        check(r.length == 0, "length expected 0");
    }

    static void testReturnsNewArray() {
        long[] src = {5L, 6L, 7L};
        long[] r = Arrays.copyOfRange(src, 0, 3);
        check(r != src, "must return a new array");
        r[0] = 99L;
        check(src[0] == 5L, "original must not be modified");
    }

    static void testFromGreaterThanToThrowsIAE() {
        try {
            Arrays.copyOfRange(new long[]{1L, 2L, 3L}, 3, 1);
            throw new RuntimeException("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            check("3 > 1".equals(e.getMessage()), "message expected '3 > 1'");
        }
    }

    static void testFromBeyondLengthThrows() {
        try {
            Arrays.copyOfRange(new long[]{1L, 2L}, 3, 3);
            throw new RuntimeException("IndexOutOfBoundsException expected");
        } catch (IndexOutOfBoundsException e) {
            // expected: negative copy length passed to System.arraycopy
        }
    }

    static void testNegativeFromThrows() {
        try {
            Arrays.copyOfRange(new long[]{1L, 2L, 3L}, -1, 2);
            throw new RuntimeException("IndexOutOfBoundsException expected");
        } catch (IndexOutOfBoundsException e) {
            // expected: negative source position in System.arraycopy
        }
    }

    public static void main(String[] args) {
        testInsideRange();
        testPaddingBeyondEnd();
        testEmptyRange();
        testEmptyArrayEmptyRange();
        testReturnsNewArray();
        testFromGreaterThanToThrowsIAE();
        testFromBeyondLengthThrows();
        testNegativeFromThrows();
    }
}
