/*
 * @test
 * @summary Test AbstractStringBuilder.subSequence(int, int) for valid and invalid indices
 * @run main AbstractStringBuilderWitupTest
 */

public class AbstractStringBuilderWitupTest {

    static class SimpleStringBuilder extends java.lang.AbstractStringBuilder {
        SimpleStringBuilder(String s) {
            super(s.length());
            for (int i = 0; i < s.length(); i++) {
                value[i] = (byte) s.charAt(i);
            }
            count = s.length();
            coder = java.lang.String.LATIN1;
        }

        @Override
        public String toString() {
            return new String(value, 0, count, java.lang.String.LATIN1);
        }

        @Override
        public java.lang.String substring(int start, int end) {
            if (start < 0 || end > count || start > end) {
                throw new StringIndexOutOfBoundsException();
            }
            return new String(value, start, end - start, coder);
        }
    }

    public static void main(String[] args) {
        SimpleStringBuilder sb = new SimpleStringBuilder("HelloWorld");

        // Test valid subsequence
        CharSequence cs = sb.subSequence(0, 5);
        if (!"Hello".contentEquals(cs)) {
            throw new AssertionError("Expected 'Hello' but got '" + cs + "'");
        }

        cs = sb.subSequence(5, 10);
        if (!"World".contentEquals(cs)) {
            throw new AssertionError("Expected 'World' but got '" + cs + "'");
        }

        cs = sb.subSequence(0, 10);
        if (!"HelloWorld".contentEquals(cs)) {
            throw new AssertionError("Expected 'HelloWorld' but got '" + cs + "'");
        }

        // Test empty subsequence
        cs = sb.subSequence(3, 3);
        if (!"".contentEquals(cs)) {
            throw new AssertionError("Expected empty string but got '" + cs + "'");
        }

        // Test invalid subsequence indices - expect StringIndexOutOfBoundsException
        try {
            sb.subSequence(-1, 5);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for start < 0");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }

        try {
            sb.subSequence(3, 11);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for end > count");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }

        try {
            sb.subSequence(7, 5);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for start > end");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
