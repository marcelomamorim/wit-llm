/*
 * @test
 * @summary Verifies String.charAt returns expected chars and throws StringIndexOutOfBoundsException for invalid indexes (Latin1 and UTF16 storage)
 * @run main StringWitupTest
 */

public class StringWitupTest {

    public static void main(String[] args) {
        String ascii = "abc";
        check(ascii.charAt(0) == 'a', "charAt(0) on abc");
        check(ascii.charAt(1) == 'b', "charAt(1) on abc");
        check(ascii.charAt(2) == 'c', "charAt(2) on abc");

        expectSIOOBE(ascii, -1);
        expectSIOOBE(ascii, 3);
        expectSIOOBE(ascii, Integer.MAX_VALUE);
        expectSIOOBE("", 0);

        char euro = (char) 0x20AC;
        String utf16 = "a" + euro + "b";
        check(utf16.charAt(0) == 'a', "charAt(0) on UTF16 string");
        check(utf16.charAt(1) == euro, "charAt(1) on UTF16 string");
        check(utf16.charAt(2) == 'b', "charAt(2) on UTF16 string");
        expectSIOOBE(utf16, -1);
        expectSIOOBE(utf16, 3);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }

    static void expectSIOOBE(String s, int index) {
        boolean thrown = false;
        try {
            s.charAt(index);
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for index " + index);
        }
    }
}
