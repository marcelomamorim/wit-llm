/* @test    @summary Test StringBuilder.insert(int, float) method for correctness and regressions    @run main StringBuilderWitupTest */

import java.lang.StringBuilder;

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertWithPositiveOffset();
        testInsertWithNegativeOffset();
        testInsertWithOffsetBeyondLength();
        testInsertWithZeroOffset();
        testInsertWithLargeFloat();
    }

    private static void testInsertWithPositiveOffset() {
        StringBuilder sb = new StringBuilder("Hello");
        float value = 3.14f;
        int offset = 2;
        String expected = "He3.14llo";
        StringBuilder result = sb.insert(offset, value);
        if (!result.toString().equals(expected)) {
            throw new AssertionError("Failed: insert at positive offset. Expected: " + expected + ", but got: " + result);
        }
    }

    private static void testInsertWithNegativeOffset() {
        StringBuilder sb = new StringBuilder("World");
        float value = 2.718f;
        int offset = -1;
        try {
            sb.insert(offset, value);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative offset");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertWithOffsetBeyondLength() {
        StringBuilder sb = new StringBuilder("Test");
        float value = 1.618f;
        int offset = 10;
        try {
            sb.insert(offset, value);
            throw new AssertionError("Expected IndexOutOfBoundsException for offset beyond length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertWithZeroOffset() {
        StringBuilder sb = new StringBuilder("Start");
        float value = 0.0f;
        int offset = 0;
        String expected = "0.0Start";
        StringBuilder result = sb.insert(offset, value);
        if (!result.toString().equals(expected)) {
            throw new AssertionError("Failed: insert at zero offset. Expected: " + expected + ", but got: " + result);
        }
    }

    private static void testInsertWithLargeFloat() {
        StringBuilder sb = new StringBuilder("Number");
        float value = 123456.789f;
        int offset = 3;
        String expected = "Num123456.789fber";
        StringBuilder result = sb.insert(offset, value);
        if (!result.toString().equals(expected)) {
            throw new AssertionError("Failed: insert with large float. Expected: " + expected + ", but got: " + result);
        }
    }
}
