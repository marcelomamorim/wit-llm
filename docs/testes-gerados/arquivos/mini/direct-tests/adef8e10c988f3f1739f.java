/*
 * @test
 * @summary Test AbstractStringBuilder.insert(int, char[]) method
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
            char[] chars = new char[count];
            for (int i = 0; i < count; i++) {
                chars[i] = (char) (value[i] & 0xff);
            }
            return new String(chars);
        }

        // Expose protected methods for testing
        void testInsert(int offset, char[] str) {
            insert(offset, str);
        }
    }

    public static void main(String[] args) {
        SimpleStringBuilder sb = new SimpleStringBuilder("HelloWorld");

        // Test insert at beginning
        sb.testInsert(0, new char[] {'X', 'Y'});
        if (!sb.toString().equals("XYHelloWorld")) {
            throw new Error("Insert at beginning failed: " + sb.toString());
        }

        // Test insert at middle
        sb.testInsert(5, new char[] {'1', '2', '3'});
        if (!sb.toString().equals("XYHel123loWorld")) {
            throw new Error("Insert at middle failed: " + sb.toString());
        }

        // Test insert at end
        sb.testInsert(sb.length(), new char[] {'Z'});
        if (!sb.toString().equals("XYHel123loWorldZ")) {
            throw new Error("Insert at end failed: " + sb.toString());
        }

        // Test insert empty array (should not change string)
        String before = sb.toString();
        sb.testInsert(3, new char[0]);
        if (!sb.toString().equals(before)) {
            throw new Error("Insert empty array changed string: " + sb.toString());
        }

        // Test insert with offset == length (append)
        SimpleStringBuilder sb2 = new SimpleStringBuilder("");
        sb2.testInsert(0, new char[] {'a', 'b'});
        if (!sb2.toString().equals("ab")) {
            throw new Error("Insert into empty builder failed: " + sb2.toString());
        }

        // Test insert with offset out of bounds (negative)
        try {
            sb.testInsert(-1, new char[] {'a'});
            throw new Error("Expected IndexOutOfBoundsException for negative offset");
        } catch (IndexOutOfBoundsException expected) {
        }

        // Test insert with offset out of bounds (greater than length)
        try {
            sb.testInsert(sb.length() + 1, new char[] {'a'});
            throw new Error("Expected IndexOutOfBoundsException for offset > length");
        } catch (IndexOutOfBoundsException expected) {
        }

        // Test insert null array (should throw NullPointerException)
        try {
            sb.testInsert(0, null);
            throw new Error("Expected NullPointerException for null char array");
        } catch (NullPointerException expected) {
        }
    }
}
