/* @test    @summary Test AbstractStringBuilder.codePointBefore(int index) for valid and invalid inputs    @run main AbstractStringBuilderWitupTest_Pca03b7afd02e */

import java.lang.AbstractStringBuilder;

public class AbstractStringBuilderWitupTest_Pca03b7afd02e {
    public static void main(String[] args) {
        testCodePointBeforeValidLatin1();
        testCodePointBeforeValidUTF16();
        testCodePointBeforeOutOfBounds();
    }

    static void testCodePointBeforeValidLatin1() {
        AbstractStringBuilder sb = new AbstractStringBuilder(5);
        sb.value = new byte[] {65, 66, 67, 68, 69}; // 'A', 'B', 'C', 'D', 'E'
        sb.count = 5;
        sb.coder = (byte) 0; // LATIN1
        int result = sb.codePointBefore(3); // should be 'B' (66)
        if (result != 66) {
            throw new AssertionError("Expected 66, got " + result);
        }
    }

    static void testCodePointBeforeValidUTF16() {
        AbstractStringBuilder sb = new AbstractStringBuilder(4);
        sb.value = new byte[] {0x00, 0x61, 0x00, 0x62}; // 'a', 'b' in UTF16BE
        sb.count = 4;
        sb.coder = (byte) 1; // UTF16
        int result = sb.codePointBefore(2); // should be 'a' (0x0061)
        if (result != 0x0061) {
            throw new AssertionError("Expected 0x0061, got " + result);
        }
    }

    static void testCodePointBeforeOutOfBounds() {
        AbstractStringBuilder sb = new AbstractStringBuilder(3);
        sb.value = new byte[] {65, 66, 67};
        sb.count = 3;
        sb.coder = (byte) 0; // LATIN1
        try {
            sb.codePointBefore(-1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index -1");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        try {
            sb.codePointBefore(3);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index 3");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
