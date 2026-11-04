/*
 * @test
 * @summary Test StringBuffer.codePointAt(int) for valid and invalid indices
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testCodePointAtValidIndex();
        testCodePointAtInvalidIndex();
    }

    private static void testCodePointAtValidIndex() {
        java.lang.StringBuffer sb = new java.lang.StringBuffer("abc\uD834\uDD1Edef"); // includes a surrogate pair at index 3
        // codePointAt should return the code point of 'a' at index 0
        int cp0 = sb.codePointAt(0);
        if (cp0 != 'a') {
            throw new Error("Expected codePointAt(0) to be 'a' (97), got " + cp0);
        }
        // codePointAt at index 3 should return the supplementary code point (0x1D11E)
        int cp3 = sb.codePointAt(3);
        if (cp3 != 0x1D11E) {
            throw new Error("Expected codePointAt(3) to be 0x1D11E, got " + cp3);
        }
        // codePointAt at last valid index (length-1) should return 'f'
        int lastIndex = sb.length() - 1;
        int cpLast = sb.codePointAt(lastIndex);
        if (cpLast != 'f') {
            throw new Error("Expected codePointAt(lastIndex) to be 'f' (102), got " + cpLast);
        }
    }

    private static void testCodePointAtInvalidIndex() {
        java.lang.StringBuffer sb = new java.lang.StringBuffer("test");
        // Test index < 0 returns StringIndexOutOfBoundsException
        try {
            sb.codePointAt(-1);
            throw new Error("Expected StringIndexOutOfBoundsException for index -1");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        // Test index == length returns StringIndexOutOfBoundsException
        try {
            sb.codePointAt(sb.length());
            throw new Error("Expected StringIndexOutOfBoundsException for index == length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        // Test index > length returns StringIndexOutOfBoundsException
        try {
            sb.codePointAt(sb.length() + 1);
            throw new Error("Expected StringIndexOutOfBoundsException for index > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
