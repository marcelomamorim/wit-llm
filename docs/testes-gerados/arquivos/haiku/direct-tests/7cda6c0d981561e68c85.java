/*
 * @test
 * @summary Witup-style behavioral tests for StringBuffer.offsetByCodePoints
 * @run main StringBufferWitupTest
 */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static void checkOffset(StringBuffer sb, int index, int offset, int expected, String label) {
        int actual = sb.offsetByCodePoints(index, offset);
        check(actual == expected,
              label + ": expected " + expected + " but was " + actual);
    }

    static void checkThrowsIOOBE(StringBuffer sb, int index, int offset, String label) {
        boolean thrown = false;
        try {
            sb.offsetByCodePoints(index, offset);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, label + ": expected IndexOutOfBoundsException");
    }

    public static void main(String[] args) {
        StringBuffer ascii = new StringBuffer("abc");
        checkOffset(ascii, 0, 2, 2, "ascii forward from 0");
        checkOffset(ascii, 1, 0, 1, "zero offset");
        checkOffset(ascii, 3, 0, 3, "zero offset at end");
        checkOffset(ascii, 2, -2, 0, "negative offset to start");

        StringBuffer empty = new StringBuffer();
        checkOffset(empty, 0, 0, 0, "empty buffer zero offset");

        StringBuffer surrogate = new StringBuffer("\uD83D\uDE00a");
        checkOffset(surrogate, 0, 1, 2, "surrogate pair counts as one code point");
        checkOffset(surrogate, 0, 2, 3, "surrogate pair plus one char");
        checkOffset(surrogate, 2, 1, 3, "from after surrogate pair");

        checkThrowsIOOBE(ascii, -1, 0, "negative index");
        checkThrowsIOOBE(ascii, 4, 0, "index beyond length");
        checkThrowsIOOBE(ascii, 0, 4, "offset beyond length");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
