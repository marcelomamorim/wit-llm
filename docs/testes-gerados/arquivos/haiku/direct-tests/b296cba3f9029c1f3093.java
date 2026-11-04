/*
 * @test
 * @summary Basic observable behavior of String.substring(int)
 * @run main StringWitupTest
 */

public class StringWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectStringIndexOutOfBounds(String s, int beginIndex) {
        try {
            s.substring(beginIndex);
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("No StringIndexOutOfBoundsException for index " + beginIndex);
    }

    public static void main(String[] args) {
        String s = "hello";
        check(s.substring(0) == s, "substring(0) should return this");
        check(s.substring(2).equals("llo"), "substring(2) of hello");
        check(s.substring(5).equals(""), "substring(length) should be empty");
        check("".substring(0).isEmpty(), "empty string substring(0) should be empty");

        expectStringIndexOutOfBounds(s, -1);
        expectStringIndexOutOfBounds(s, 6);
        expectStringIndexOutOfBounds("", 1);

        String utf16 = "a\u20ACb";
        check(utf16.substring(1).equals("\u20ACb"), "UTF16 substring(1)");
        check(utf16.substring(3).isEmpty(), "UTF16 substring(length) should be empty");
        expectStringIndexOutOfBounds(utf16, 4);

        String latin1 = "h\u00e9llo";
        check(latin1.substring(1).equals("\u00e9llo"), "Latin1 substring(1)");
        check(latin1.substring(4).equals("o"), "Latin1 substring(4)");
    }
}
