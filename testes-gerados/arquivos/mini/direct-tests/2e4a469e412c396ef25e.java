/*
 * @test
 * @summary Test AbstractStringBuilder.insert(int offset, int i) method
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
        public String toString() {
            return new String(this.value, 0, this.count, this.coder == java.lang.String.LATIN1 ? java.nio.charset.StandardCharsets.ISO_8859_1 : java.nio.charset.StandardCharsets.UTF_16);
        }
    }

    public static void main(String[] args) {
        SimpleStringBuilder sb = new SimpleStringBuilder("HelloWorld");

        // Insert integer 123 at offset 5 (middle)
        sb.insert(5, 123);
        String result = sb.toString();
        if (!result.equals("Hello123World")) {
            throw new Error("Test failed: expected 'Hello123World' but got '" + result + "'");
        }

        // Insert integer 0 at offset 0 (start)
        sb = new SimpleStringBuilder("Test");
        sb.insert(0, 0);
        result = sb.toString();
        if (!result.equals("0Test")) {
            throw new Error("Test failed: expected '0Test' but got '" + result + "'");
        }

        // Insert negative integer at end
        sb = new SimpleStringBuilder("End");
        sb.insert(sb.length(), -456);
        result = sb.toString();
        if (!result.equals("End-456")) {
            throw new Error("Test failed: expected 'End-456' but got '" + result + "'");
        }

        // Insert integer at offset equal to length (append)
        sb = new SimpleStringBuilder("");
        sb.insert(sb.length(), 789);
        result = sb.toString();
        if (!result.equals("789")) {
            throw new Error("Test failed: expected '789' but got '" + result + "'");
        }

        // Insert integer zero into empty string at offset 0
        sb = new SimpleStringBuilder("");
        sb.insert(0, 0);
        result = sb.toString();
        if (!result.equals("0")) {
            throw new Error("Test failed: expected '0' but got '" + result + "'");
        }

        System.out.println("All tests passed.");
    }
}
