/*
 * @test
 * @summary Tests for StringBuilder.append(char[] str, int offset, int len) method.
 * @run main StringBuilderWitupTest
 */

import java.lang.StringBuilder;

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testAppendWithValidParameters();
        testAppendWithNegativeOffset();
        testAppendWithNegativeLength();
        testAppendWithOffsetExceedingArray();
        testAppendWithLengthExceedingArray();
    }

    private static void testAppendWithValidParameters() {
        StringBuilder sb = new StringBuilder();
        char[] chars = {'a', 'b', 'c', 'd'};
        sb.append(chars, 1, 2); // should append 'b', 'c'
        assert sb.toString().equals("bc") : "Expected 'bc', got '" + sb.toString() + "'";
    }

    private static void testAppendWithNegativeOffset() {
        StringBuilder sb = new StringBuilder();
        char[] chars = {'x', 'y', 'z'};
        try {
            sb.append(chars, -1, 2);
            assert false : "Expected IndexOutOfBoundsException for negative offset";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testAppendWithNegativeLength() {
        StringBuilder sb = new StringBuilder();
        char[] chars = {'x', 'y', 'z'};
        try {
            sb.append(chars, 1, -2);
            assert false : "Expected IndexOutOfBoundsException for negative length";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testAppendWithOffsetExceedingArray() {
        StringBuilder sb = new StringBuilder();
        char[] chars = {'a', 'b'};
        try {
            sb.append(chars, 3, 1);
            assert false : "Expected IndexOutOfBoundsException for offset exceeding array length";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testAppendWithLengthExceedingArray() {
        StringBuilder sb = new StringBuilder();
        char[] chars = {'a', 'b'};
        try {
            sb.append(chars, 1, 5);
            assert false : "Expected IndexOutOfBoundsException for length exceeding array bounds";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
