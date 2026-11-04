/*
 * @test
 * @summary Witup-style tests for StringBuilder.insert(int offset, int i)
 * @run main StringBuilderWitupTest
 */

public class StringBuilderWitupTest {

    public static void main(String[] args) {
        testInsertAtStartReturnsSameBuilder();
        testInsertIntoMiddle();
        testInsertAtEnd();
        testInsertNegativeValue();
        testInsertMinValue();
        testInsertIntoEmptyBuilder();
        testChainedInsertions();
        testOffsetGreaterThanLengthThrows();
        testNegativeOffsetThrows();
    }

    static void testInsertAtStartReturnsSameBuilder() {
        StringBuilder sb = new StringBuilder("ab");
        StringBuilder result = sb.insert(0, 42);
        if (result != sb) {
            throw new RuntimeException("insert must return this");
        }
        if (!"42ab".equals(sb.toString())) {
            throw new RuntimeException("expected 42ab but was " + sb);
        }
    }

    static void testInsertIntoMiddle() {
        StringBuilder sb = new StringBuilder("ac");
        sb.insert(1, 0);
        if (!"a0c".equals(sb.toString())) {
            throw new RuntimeException("expected a0c but was " + sb);
        }
    }

    static void testInsertAtEnd() {
        StringBuilder sb = new StringBuilder("ab");
        sb.insert(2, -7);
        if (!"ab-7".equals(sb.toString())) {
            throw new RuntimeException("expected ab-7 but was " + sb);
        }
    }

    static void testInsertNegativeValue() {
        StringBuilder sb = new StringBuilder();
        sb.insert(0, -123);
        if (!"-123".equals(sb.toString())) {
            throw new RuntimeException("expected -123 but was " + sb);
        }
    }

    static void testInsertMinValue() {
        StringBuilder sb = new StringBuilder();
        sb.insert(0, Integer.MIN_VALUE);
        if (!"-2147483648".equals(sb.toString())) {
            throw new RuntimeException("expected -2147483648 but was " + sb);
        }
    }

    static void testInsertIntoEmptyBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.insert(0, 5);
        if (sb.length() != 1 || sb.charAt(0) != '5') {
            throw new RuntimeException("expected single char 5 but was " + sb);
        }
    }

    static void testChainedInsertions() {
        StringBuilder sb = new StringBuilder("x");
        sb.insert(0, 1).insert(2, 2);
        if (!"1x2".equals(sb.toString())) {
            throw new RuntimeException("expected 1x2 but was " + sb);
        }
    }

    static void testOffsetGreaterThanLengthThrows() {
        StringBuilder sb = new StringBuilder("ab");
        try {
            sb.insert(3, 1);
            throw new RuntimeException("expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        }
    }

    static void testNegativeOffsetThrows() {
        StringBuilder sb = new StringBuilder("ab");
        try {
            sb.insert(-1, 5);
            throw new RuntimeException("expected StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        }
    }
}
