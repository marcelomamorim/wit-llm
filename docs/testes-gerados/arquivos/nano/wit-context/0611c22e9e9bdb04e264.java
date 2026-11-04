/*
 * @test
 * @summary Tests for StringBuilder.insert(int, CharSequence)
 * @run main StringBuilderWitupTest
 */

import java.lang.StringBuilder;

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertWithValidInputs();
        testInsertWithNullCharSequence();
        testInsertWithNegativeDstOffset();
        testInsertWithDstOffsetGreaterThanLength();
        testInsertWithDstOffsetEqualToLength();
        testInsertWithDstOffsetZero();
        testInsertWithEmptyCharSequence();
    }

    private static void testInsertWithValidInputs() {
        StringBuilder sb = new StringBuilder("Hello");
        CharSequence seq = "World";
        sb.insert(5, seq);
        assert sb.toString().equals("HelloWorld") : "Failed: insert at valid position";
    }

    private static void testInsertWithNullCharSequence() {
        StringBuilder sb = new StringBuilder("Test");
        CharSequence seq = null;
        try {
            sb.insert(2, seq);
            assert sb.toString().equals("Tenullst") : "Failed: insert null CharSequence";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testInsertWithNegativeDstOffset() {
        StringBuilder sb = new StringBuilder("Sample");
        CharSequence seq = "Test";
        try {
            sb.insert(-1, seq);
            assert false : "Expected IndexOutOfBoundsException for negative dstOffset";
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testInsertWithDstOffsetGreaterThanLength() {
        StringBuilder sb = new StringBuilder("Example");
        CharSequence seq = "Test";
        try {
            sb.insert(100, seq);
            assert false : "Expected IndexOutOfBoundsException for dstOffset > length";
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testInsertWithDstOffsetEqualToLength() {
        StringBuilder sb = new StringBuilder("End");
        CharSequence seq = "ing";
        sb.insert(sb.length(), seq);
        assert sb.toString().equals("Ending") : "Failed: insert at dstOffset == length";
    }

    private static void testInsertWithDstOffsetZero() {
        StringBuilder sb = new StringBuilder("Start");
        CharSequence seq = "Begin";
        sb.insert(0, seq);
        assert sb.toString().equals("BeginStart") : "Failed: insert at dstOffset zero";
    }

    private static void testInsertWithEmptyCharSequence() {
        StringBuilder sb = new StringBuilder("Empty");
        CharSequence seq = "";
        sb.insert(3, seq);
        assert sb.toString().equals("Empy") : "Failed: insert empty CharSequence";
    }
}
