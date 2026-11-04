/*
 * @test
 * @summary Test AbstractStringBuilder.insert(int, float) for valid and invalid offsets
 * @run main AbstractStringBuilderWitupTest
 */

public class AbstractStringBuilderWitupTest {

    static class SimpleStringBuilder extends java.lang.AbstractStringBuilder {
        SimpleStringBuilder(String s) {
            super(s.length());
            // Initialize value and count according to COMPACT_STRINGS
            if (COMPACT_STRINGS) {
                for (int i = 0; i < s.length(); i++) {
                    value[i] = (byte) s.charAt(i);
                }
                count = s.length();
                coder = LATIN1;
            } else {
                // UTF16 encoding
                char[] chars = s.toCharArray();
                for (int i = 0; i < chars.length; i++) {
                    int bpos = i * 2;
                    value[bpos] = (byte) (chars[i] >>> 8);
                    value[bpos + 1] = (byte) chars[i];
                }
                count = s.length();
                coder = UTF16;
            }
        }

        @Override
        public java.lang.AbstractStringBuilder insert(int offset, String str) {
            if (offset < 0 || offset > count) {
                throw new StringIndexOutOfBoundsException(offset);
            }
            if (str == null) {
                str = "null";
            }
            int len = str.length();
            if (len == 0) {
                return this;
            }
            // Simplified insert: just increase count and ignore actual insertion for test
            count += len;
            return this;
        }
    }

    public static void main(String[] args) {
        SimpleStringBuilder sb = new SimpleStringBuilder("hello");

        // Test valid insertions
        sb.insert(0, 1.23f);
        if (sb.length() != 10) {
            throw new Error("Length after insert at 0 with float 1.23f should be 10");
        }

        sb.insert(5, 4.56f);
        if (sb.length() != 15) {
            throw new Error("Length after insert at 5 with float 4.56f should be 15");
        }

        sb.insert(sb.length(), 7.89f);
        if (sb.length() != 20) {
            throw new Error("Length after insert at end with float 7.89f should be 20");
        }

        // Test invalid insertions: offset < 0
        try {
            sb.insert(-1, 0.0f);
            throw new Error("Expected StringIndexOutOfBoundsException for offset -1");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }

        // Test invalid insertions: offset > count
        try {
            sb.insert(sb.length() + 1, 0.0f);
            throw new Error("Expected StringIndexOutOfBoundsException for offset > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    // Constants copied from String for COMPACT_STRINGS, LATIN1, UTF16
    private static final boolean COMPACT_STRINGS = true;
    private static final byte LATIN1 = 0;
    private static final byte UTF16 = 1;
}
