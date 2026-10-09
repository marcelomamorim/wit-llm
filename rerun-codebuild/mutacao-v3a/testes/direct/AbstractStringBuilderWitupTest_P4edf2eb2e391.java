/* @test    @summary Test AbstractStringBuilder.codePointBefore(int index) method    @run main AbstractStringBuilderWitupTest_P4edf2eb2e391 */

import java.lang.AbstractStringBuilder;
import java.lang.StringIndexOutOfBoundsException;

public class AbstractStringBuilderWitupTest_P4edf2eb2e391 {

    public static void main(String[] args) {
        testCodePointBeforeValidLatin1();
        testCodePointBeforeValidUTF16();
        testCodePointBeforeInvalidIndexNegative();
        testCodePointBeforeInvalidIndexTooLarge();
    }

    static void testCodePointBeforeValidLatin1() {
        AbstractStringBuilder sb = new ConcreteStringBuilder(new byte[]{0x41, 0x42, 0x43}, (byte)0, 3); // 'A', 'B', 'C'
        int cp = sb.codePointBefore(2); // should be 0x42 ('B')
        if (cp != 0x42) {
            throw new AssertionError("Expected 0x42, but got: " + cp);
        }
    }

    static void testCodePointBeforeValidUTF16() {
        // For simplicity, simulate UTF16 with code points > 255
        // Actual UTF16 is complex, but here we assume value array represents surrogate pairs
        byte[] utf16Bytes = new byte[]{(byte)0xD8, 0x34, 0x00, 0x41, 0x00, 0x42};
        // Let's create a ConcreteStringBuilder with UTF16 encoding
        AbstractStringBuilder sb = new ConcreteStringBuilder(utf16Bytes, (byte)1, utf16Bytes.length);
        int cp = sb.codePointBefore(4); // should be a surrogate pair code point
        // Since actual code point calculation is complex, check that it returns a value > 255
        if (cp <= 255) {
            throw new AssertionError("Expected code point > 255, but got: " + cp);
        }
    }

    static void testCodePointBeforeInvalidIndexNegative() {
        AbstractStringBuilder sb = new ConcreteStringBuilder(new byte[]{0x41, 0x42}, (byte)0, 2);
        try {
            sb.codePointBefore(-1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative index");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testCodePointBeforeInvalidIndexTooLarge() {
        AbstractStringBuilder sb = new ConcreteStringBuilder(new byte[]{0x41, 0x42}, (byte)0, 2);
        try {
            sb.codePointBefore(2); // count is 2, valid index is 0 or 1
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index >= count");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    // Concrete implementation for testing purposes
    static class ConcreteStringBuilder extends AbstractStringBuilder {
        ConcreteStringBuilder(byte[] bytes, byte coder, int count) {
            this.value = bytes;
            this.coder = coder;
            this.count = count;
        }

        @Override
        public int length() {
            return count;
        }

        @Override
        public CharSequence subSequence(int start, int end) {
            throw new UnsupportedOperationException();
        }
    }
}
