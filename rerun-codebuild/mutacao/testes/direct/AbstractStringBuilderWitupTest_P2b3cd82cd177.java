/*
 * @test
 * @summary Test AbstractStringBuilder.codePointBefore(int index) for correctness and exception handling
 * @run main AbstractStringBuilderWitupTest_P2b3cd82cd177
 */

import java.lang.AbstractStringBuilder;

public class AbstractStringBuilderWitupTest_P2b3cd82cd177 {

    public static void main(String[] args) {
        testCodePointBeforeValid();
        testCodePointBeforeOutOfBounds();
        testCodePointBeforeLatin1();
    }

    static void testCodePointBeforeValid() {
        AbstractStringBuilder sb = new AbstractStringBuilder(10) {
            {
                value = new byte[] {65, 66, 67, 68, 69};
                count = 5;
                coder = 0; // Latin1
            }
        };
        int result = sb.codePointBefore(3); // should return value[2] = 'C' = 67
        if (result != 67) {
            throw new AssertionError("Expected 67, got " + result);
        }
    }

    static void testCodePointBeforeOutOfBounds() {
        AbstractStringBuilder sb = new AbstractStringBuilder(10) {
            {
                value = new byte[] {65, 66, 67};
                count = 3;
                coder = 0; // Latin1
            }
        };
        try {
            sb.codePointBefore(-1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index -1");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        try {
            sb.codePointBefore(3); // index == count, should throw
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index 3");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testCodePointBeforeLatin1() {
        AbstractStringBuilder sb = new AbstractStringBuilder(10) {
            {
                value = new byte[] {(byte)0xFF, 0x00, 0x41};
                count = 3;
                coder = 0; // Latin1
            }
        };
        int result = sb.codePointBefore(2); // value[1] = 0x00
        if (result != 0) {
            throw new AssertionError("Expected 0, got " + result);
        }
        int result2 = sb.codePointBefore(3); // value[2] = 'A' = 65
        if (result2 != 65) {
            throw new AssertionError("Expected 65, got " + result2);
        }
    }
}
