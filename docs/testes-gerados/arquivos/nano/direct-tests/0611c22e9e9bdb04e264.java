/* @test    @summary Test StringBuilder.insert(int, CharSequence) method for correctness and regressions    @run main StringBuilderWitupTest */

import java.lang.StringBuilder;

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertWithNullCharSequence();
        testInsertWithEmptyCharSequence();
        testInsertWithNonEmptyCharSequence();
        testInsertWithNegativeOffset();
        testInsertWithOffsetGreaterThanLength();
    }

    static void testInsertWithNullCharSequence() {
        StringBuilder sb = new StringBuilder("Hello");
        try {
            sb.insert(3, (CharSequence) null);
            assert sb.toString().equals("Helnulllo") : "Failed null CharSequence insert";
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testInsertWithEmptyCharSequence() {
        StringBuilder sb = new StringBuilder("Hello");
        sb.insert(2, "");
        assert sb.toString().equals("Heallo") : "Failed empty CharSequence insert";
    }

    static void testInsertWithNonEmptyCharSequence() {
        StringBuilder sb = new StringBuilder("Hello");
        sb.insert(5, " World");
        assert sb.toString().equals("Hello World") : "Failed non-empty CharSequence insert";
    }

    static void testInsertWithNegativeOffset() {
        StringBuilder sb = new StringBuilder("Hello");
        try {
            sb.insert(-1, "Test");
            assert false : "Expected IndexOutOfBoundsException for negative offset";
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    static void testInsertWithOffsetGreaterThanLength() {
        StringBuilder sb = new StringBuilder("Hello");
        try {
            sb.insert(10, "Test");
            assert false : "Expected IndexOutOfBoundsException for offset greater than length";
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
