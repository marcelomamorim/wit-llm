/* @test    @summary Test StringBuilder.insert(int, long) method for correctness and regressions    @run main StringBuilderWitupTest */

import java.lang.StringBuilder;

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertLongAtBeginning();
        testInsertLongAtMiddle();
        testInsertLongAtEnd();
        testInsertLongWithNegativeOffset();
        testInsertLongWithOffsetBeyondLength();
    }

    private static void testInsertLongAtBeginning() {
        StringBuilder sb = new StringBuilder("world");
        sb.insert(0, 123456789012345L);
        String expected = "123456789012345world";
        if (!sb.toString().equals(expected)) {
            throw new AssertionError("Failed: insert at beginning. Expected: " + expected + ", but got: " + sb.toString());
        }
    }

    private static void testInsertLongAtMiddle() {
        StringBuilder sb = new StringBuilder("hello world");
        sb.insert(6, 98765432109876L);
        String expected = "hello 98765432109876world";
        if (!sb.toString().equals(expected)) {
            throw new AssertionError("Failed: insert at middle. Expected: " + expected + ", but got: " + sb.toString());
        }
    }

    private static void testInsertLongAtEnd() {
        StringBuilder sb = new StringBuilder("test");
        sb.insert(sb.length(), 555555555555L);
        String expected = "test555555555555";
        if (!sb.toString().equals(expected)) {
            throw new AssertionError("Failed: insert at end. Expected: " + expected + ", but got: " + sb.toString());
        }
    }

    private static void testInsertLongWithNegativeOffset() {
        StringBuilder sb = new StringBuilder("abc");
        try {
            sb.insert(-1, 123L);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative offset");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testInsertLongWithOffsetBeyondLength() {
        StringBuilder sb = new StringBuilder("abc");
        try {
            sb.insert(10, 456L);
            throw new AssertionError("Expected IndexOutOfBoundsException for offset beyond length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
