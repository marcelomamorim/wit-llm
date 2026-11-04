/*
 * @test
 * @summary Test AbstractStringBuilder.subSequence(int,int) method
 * @run main AbstractStringBuilderWitupTest
 */

public class AbstractStringBuilderWitupTest {

    static class SimpleStringBuilder extends java.lang.AbstractStringBuilder {
        SimpleStringBuilder(String s) {
            super(s.length());
            for (int i = 0; i < s.length(); i++) {
                if (coder == java.lang.String.LATIN1) {
                    value[i] = (byte) s.charAt(i);
                } else {
                    // UTF16 encoding: 2 bytes per char
                    byte[] b = java.lang.StringUTF16.newBytesFor(1);
                    int c = s.charAt(i);
                    b[0] = (byte) (c >>> 8);
                    b[1] = (byte) c;
                    value[i * 2] = b[0];
                    value[i * 2 + 1] = b[1];
                }
            }
            count = s.length();
        }

        @Override
        public String toString() {
            return new String(value, coder);
        }
    }

    public static void main(String[] args) {
        // Test 1: subSequence returns correct substring
        SimpleStringBuilder sb = new SimpleStringBuilder("Hello, world!");
        CharSequence cs = sb.subSequence(0, 5);
        if (!"Hello".contentEquals(cs)) {
            throw new Error("Test 1 failed: expected 'Hello' but got '" + cs + "'");
        }

        // Test 2: subSequence with full length
        cs = sb.subSequence(0, sb.length());
        if (!"Hello, world!".contentEquals(cs)) {
            throw new Error("Test 2 failed: expected full string but got '" + cs + "'");
        }

        // Test 3: subSequence with start == end returns empty string
        cs = sb.subSequence(3, 3);
        if (!"".contentEquals(cs)) {
            throw new Error("Test 3 failed: expected empty string but got '" + cs + "'");
        }

        // Test 4: subSequence with start > end should throw StringIndexOutOfBoundsException
        boolean threw = false;
        try {
            sb.subSequence(5, 3);
        } catch (StringIndexOutOfBoundsException e) {
            threw = true;
        }
        if (!threw) {
            throw new Error("Test 4 failed: expected StringIndexOutOfBoundsException for start > end");
        }

        // Test 5: subSequence with negative start should throw StringIndexOutOfBoundsException
        threw = false;
        try {
            sb.subSequence(-1, 3);
        } catch (StringIndexOutOfBoundsException e) {
            threw = true;
        }
        if (!threw) {
            throw new Error("Test 5 failed: expected StringIndexOutOfBoundsException for negative start");
        }

        // Test 6: subSequence with end > length should throw StringIndexOutOfBoundsException
        threw = false;
        try {
            sb.subSequence(0, sb.length() + 1);
        } catch (StringIndexOutOfBoundsException e) {
            threw = true;
        }
        if (!threw) {
            throw new Error("Test 6 failed: expected StringIndexOutOfBoundsException for end > length");
        }
    }
}
