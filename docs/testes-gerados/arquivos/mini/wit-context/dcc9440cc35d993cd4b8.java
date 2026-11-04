/*
 * @test
 * @summary Test AbstractStringBuilder.insert(int, boolean) for valid and invalid offsets
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
            return new String(value, 0, count);
        }
    }

    public static void main(String[] args) {
        SimpleStringBuilder sb = new SimpleStringBuilder("Hello");

        // Test valid insertions
        java.lang.AbstractStringBuilder result = sb.insert(0, true);
        if (!result.toString().equals("trueHello")) {
            throw new Error("Insert at offset 0 failed: " + result.toString());
        }

        result = sb.insert(4, false);
        if (!result.toString().equals("trueHellfalseo")) {
            throw new Error("Insert at offset 4 failed: " + result.toString());
        }

        // Test offset == count (append)
        int len = sb.length();
        result = sb.insert(len, true);
        if (!result.toString().endsWith("true")) {
            throw new Error("Insert at offset == length failed: " + result.toString());
        }

        // Test invalid offsets: offset < 0
        try {
            sb.insert(-1, true);
            throw new Error("Expected StringIndexOutOfBoundsException for offset < 0");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }

        // Test invalid offsets: offset > count
        try {
            sb.insert(sb.length() + 1, false);
            throw new Error("Expected StringIndexOutOfBoundsException for offset > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
