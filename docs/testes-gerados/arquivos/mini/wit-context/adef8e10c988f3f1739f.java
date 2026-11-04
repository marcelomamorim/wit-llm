/*
 * @test
 * @summary Test AbstractStringBuilder.insert(int, char[]) for valid and invalid offsets
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
            char[] chars = new char[count];
            for (int i = 0; i < count; i++) {
                chars[i] = (char) (value[i] & 0xff);
            }
            return new String(chars);
        }
    }

    public static void main(String[] args) {
        testInsertValid();
        testInsertInvalidOffset();
    }

    private static void testInsertValid() {
        SimpleStringBuilder sb = new SimpleStringBuilder("HelloWorld");
        char[] insertChars = {' ', 'J', 'D', 'K'};
        sb.insert(5, insertChars);
        String result = sb.toString();
        if (!result.equals("Hello JDKWorld")) {
            throw new Error("testInsertValid failed: expected 'Hello JDKWorld' but got '" + result + "'");
        }
    }

    private static void testInsertInvalidOffset() {
        SimpleStringBuilder sb = new SimpleStringBuilder("Test");
        char[] insertChars = {'X'};

        // offset < 0 should throw StringIndexOutOfBoundsException
        try {
            sb.insert(-1, insertChars);
            throw new Error("testInsertInvalidOffset failed: expected StringIndexOutOfBoundsException for offset -1");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }

        // offset > count should throw StringIndexOutOfBoundsException
        try {
            sb.insert(sb.length() + 1, insertChars);
            throw new Error("testInsertInvalidOffset failed: expected StringIndexOutOfBoundsException for offset > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
