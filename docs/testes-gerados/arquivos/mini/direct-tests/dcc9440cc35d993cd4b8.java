/*
 * @test
 * @summary Test AbstractStringBuilder.insert(int, boolean) method
 * @run main AbstractStringBuilderWitupTest
 */

public class AbstractStringBuilderWitupTest {

    static class SimpleStringBuilder extends java.lang.AbstractStringBuilder {
        SimpleStringBuilder(String s) {
            super(s.length());
            for (int i = 0; i < s.length(); i++) {
                this.append(s.charAt(i));
            }
        }

        @Override
        public java.lang.String toString() {
            return new String(value, 0, count, coder == LATIN1 ? java.nio.charset.StandardCharsets.ISO_8859_1 : java.nio.charset.StandardCharsets.UTF_16);
        }
    }

    public static void main(String[] args) {
        // Test insert at beginning
        SimpleStringBuilder sb1 = new SimpleStringBuilder("test");
        sb1.insert(0, true);
        if (!sb1.toString().equals("truetest")) {
            throw new Error("Failed insert true at offset 0: " + sb1.toString());
        }

        // Test insert at middle
        SimpleStringBuilder sb2 = new SimpleStringBuilder("test");
        sb2.insert(2, false);
        if (!sb2.toString().equals("tefalset")) {
            throw new Error("Failed insert false at offset 2: " + sb2.toString());
        }

        // Test insert at end
        SimpleStringBuilder sb3 = new SimpleStringBuilder("test");
        sb3.insert(4, true);
        if (!sb3.toString().equals("testtrue")) {
            throw new Error("Failed insert true at offset 4: " + sb3.toString());
        }

        // Test insert with empty string builder
        SimpleStringBuilder sb4 = new SimpleStringBuilder("");
        sb4.insert(0, false);
        if (!sb4.toString().equals("false")) {
            throw new Error("Failed insert false at offset 0 on empty builder: " + sb4.toString());
        }

        // Test insert with offset equal to length
        SimpleStringBuilder sb5 = new SimpleStringBuilder("abc");
        sb5.insert(sb5.length(), true);
        if (!sb5.toString().equals("abctrue")) {
            throw new Error("Failed insert true at offset length: " + sb5.toString());
        }

        // Test insert with offset zero on single char
        SimpleStringBuilder sb6 = new SimpleStringBuilder("x");
        sb6.insert(0, false);
        if (!sb6.toString().equals("falsex")) {
            throw new Error("Failed insert false at offset 0 on single char: " + sb6.toString());
        }

        // Test insert with offset in middle of single char string (should throw IndexOutOfBoundsException)
        SimpleStringBuilder sb7 = new SimpleStringBuilder("x");
        boolean thrown = false;
        try {
            sb7.insert(2, true);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new Error("Expected IndexOutOfBoundsException for offset 2 on length 1");
        }
    }

    private static final byte LATIN1 = 0;
}