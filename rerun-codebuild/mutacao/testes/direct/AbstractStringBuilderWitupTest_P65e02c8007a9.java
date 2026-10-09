/* @test    @summary Test offsetByCodePoints method for AbstractStringBuilder    @run main AbstractStringBuilderWitupTest_P65e02c8007a9 */

import java.lang.AbstractStringBuilder;

public class AbstractStringBuilderWitupTest_P65e02c8007a9 {

    public static void main(String[] args) {
        testOffsetByCodePoints();
    }

    public static void testOffsetByCodePoints() {
        AbstractStringBuilder builder = new ConcreteStringBuilder(10);
        // Initialize with some characters
        builder.append("abc\uD83D\uDE00"); // 'abc' + 😀 (emoji surrogate pair)
        int length = builder.length();
        // Test normal offset
        int offset = builder.offsetByCodePoints(0, 3); // should be index after 'abc'
        assert offset == 3 : "Expected offset 3, got " + offset;
        // Test offset with surrogate pair
        offset = builder.offsetByCodePoints(3, 1); // should move over emoji surrogate pair
        assert offset == 5 : "Expected offset 5, got " + offset;
        // Test offset with negative codePointOffset
        offset = builder.offsetByCodePoints(5, -1); // move back over emoji
        assert offset == 3 : "Expected offset 3, got " + offset;
        // Test offset at end of string
        offset = builder.offsetByCodePoints(length, 0); // should be same as length
        assert offset == length : "Expected offset " + length + ", got " + offset;
        // Test invalid index (negative)
        try {
            builder.offsetByCodePoints(-1, 1);
            assert false : "Expected IndexOutOfBoundsException for negative index";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        // Test invalid index (greater than count)
        try {
            builder.offsetByCodePoints(length + 1, 1);
            assert false : "Expected IndexOutOfBoundsException for index > count";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    // Concrete subclass for testing
    static class ConcreteStringBuilder extends AbstractStringBuilder {
        public ConcreteStringBuilder(int capacity) {
            super(capacity);
        }
        public ConcreteStringBuilder append(String str) {
            // Simplified append implementation for testing
            int len = str.length();
            checkIndex(count, count + len);
            for (int i = 0; i < len; i++) {
                value[count + i] = (byte) str.charAt(i);
            }
            count += len;
            return this;
        }
    }

    // Helper method to check index bounds
    static void checkIndex(int start, int end) {
        if (start < 0 || end < start) {
            throw new IndexOutOfBoundsException();
        }
    }
}
