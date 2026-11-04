/*
 * @test
 * @summary Test AbstractStringBuilder.insert(int offset, int i) for valid and invalid offsets
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
        }

        @Override
        public String toString() {
            // Decode bytes as Latin1 since coder is LATIN1 in JDK 11
            return new String(value, 0, count, java.nio.charset.StandardCharsets.ISO_8859_1);
        }
    }

    public static void main(String[] args) {
        SimpleStringBuilder sb = new SimpleStringBuilder("Hello");

        // Test valid insertions
        java.lang.AbstractStringBuilder result = sb.insert(0, 123);
        if (!result.toString().equals("123Hello")) {
            throw new Error("Insert at offset 0 failed: " + result.toString());
        }

        result = sb.insert(3, 456);
        if (!result.toString().equals("123456Hello")) {
            throw new Error("Insert at offset 3 failed: " + result.toString());
        }

        result = sb.insert(sb.length(), 789);
        if (!result.toString().equals("123456Hello789")) {
            throw new Error("Insert at end failed: " + result.toString());
        }

        // Test invalid offsets: offset < 0
        try {
            sb.insert(-1, 0);
            throw new Error("Expected StringIndexOutOfBoundsException for offset -1");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }

        // Test invalid offsets: offset > length
        try {
            sb.insert(sb.length() + 1, 0);
            throw new Error("Expected StringIndexOutOfBoundsException for offset > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
