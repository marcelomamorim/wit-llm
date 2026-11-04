/* @test    @summary Test StringBuilder.append(char[] str, int offset, int len) method for correctness and regressions    @run main StringBuilderWitupTest */

import java.lang.StringBuilder;

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testAppendCharArrayWithValidRange();
        testAppendCharArrayWithZeroLength();
        testAppendCharArrayWithFullArray();
        testAppendCharArrayWithNegativeOffset();
        testAppendCharArrayWithInvalidLength();
        testAppendCharArrayWithOffsetBeyondArray();
        testAppendCharArrayWithNegativeLength();
    }

    private static void testAppendCharArrayWithValidRange() {
        char[] chars = {'a', 'b', 'c', 'd', 'e'};
        StringBuilder sb = new StringBuilder();
        sb.append(chars, 1, 3); // should append 'b', 'c', 'd'
        String result = sb.toString();
        if (!"bcd".equals(result)) {
            throw new AssertionError("Expected 'bcd', but got: " + result);
        }
    }

    private static void testAppendCharArrayWithZeroLength() {
        char[] chars = {'x', 'y', 'z'};
        StringBuilder sb = new StringBuilder("start");
        sb.append(chars, 0, 0); // should append nothing
        String result = sb.toString();
        if (!"start".equals(result)) {
            throw new AssertionError("Expected 'start', but got: " + result);
        }
    }

    private static void testAppendCharArrayWithFullArray() {
        char[] chars = {'1', '2', '3'};
        StringBuilder sb = new StringBuilder();
        sb.append(chars, 0, chars.length); // append entire array
        String result = sb.toString();
        if (!"123".equals(result)) {
            throw new AssertionError("Expected '123', but got: " + result);
        }
    }

    private static void testAppendCharArrayWithNegativeOffset() {
        char[] chars = {'a', 'b'};
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(chars, -1, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative offset");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testAppendCharArrayWithInvalidLength() {
        char[] chars = {'a', 'b'};
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(chars, 0, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testAppendCharArrayWithOffsetBeyondArray() {
        char[] chars = {'a', 'b'};
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(chars, 3, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for offset beyond array length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testAppendCharArrayWithNegativeLength() {
        char[] chars = {'a', 'b'};
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(chars, 1, -2);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
