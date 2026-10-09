/* @test
   @summary Verifies behavior of codePointBefore method in AbstractStringBuilder.
   @run main AbstractStringBuilderWitupTest_P563ee5e398cf
 */
import java.lang.AbstractStringBuilder;
public class AbstractStringBuilderWitupTest_P563ee5e398cf {
    public static void main(String[] args) {
        testLatin1();
        testUTF16();
        testIndexOutOfBounds();
    }

    static void testLatin1() {
        AbstractStringBuilder sb = new AbstractStringBuilder(5) {
            { value = new byte[] {65, 66, 67, 68, 69}; };
            { count = 5; }
            { coder = (byte) 0; } // LATIN1
        };
        int index = 3;
        int expected = sb.value[index - 1] & 0xff;
        int result = sb.codePointBefore(index);
        if (result != expected) {
            throw new AssertionError("Latin1: expected " + expected + " but got " + result);
        }
    }

    static void testUTF16() {
        AbstractStringBuilder sb = new AbstractStringBuilder(4) {
            { value = new byte[] {0x00, 0x41, 0x00, 0x42}; };
            { count = 4; }
            { coder = (byte) 1; } // UTF16
        };
        int index = 3;
        int expected = StringUTF16.codePointBeforeSB(sb.value, index);
        int result = sb.codePointBefore(index);
        if (result != expected) {
            throw new AssertionError("UTF16: expected " + expected + " but got " + result);
        }
    }

    static void testIndexOutOfBounds() {
        AbstractStringBuilder sb = new AbstractStringBuilder(3) {
            { value = new byte[] {65, 66, 67}; };
            { count = 3; }
            { coder = (byte) 0; }
        };
        try {
            sb.codePointBefore(0);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index 0");
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
