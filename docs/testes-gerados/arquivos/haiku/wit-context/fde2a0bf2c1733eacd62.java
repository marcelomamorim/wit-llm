/*
 * @test
 * @summary Checks begin/end bounds validation for UTF16-backed strings through public String APIs that reach the same range checks used by StringUTF16.checkBoundsBeginEnd
 * @run main StringUTF16WitupTest
 */

public class StringUTF16WitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static boolean getCharsThrowsSIOOBE(String s, int srcBegin, int srcEnd) {
        char[] dst = new char[8];
        try {
            s.getChars(srcBegin, srcEnd, dst, 0);
            return false;
        } catch (StringIndexOutOfBoundsException e) {
            return true;
        }
    }

    static boolean substringThrowsSIOOBE(String s, int begin, int end) {
        try {
            s.substring(begin, end);
            return false;
        } catch (StringIndexOutOfBoundsException e) {
            return true;
        }
    }

    public static void main(String[] args) {
        String s = "\u4e2d\u6587\u5b57\u7b26";

        char[] dst = new char[4];
        s.getChars(0, 4, dst, 0);
        check(dst[0] == '\u4e2d' && dst[3] == '\u7b26', "valid full range copy failed");

        s.getChars(2, 2, dst, 0);

        check(s.substring(0, 4).equals(s), "valid full substring failed");
        check(s.substring(1, 3).equals("\u6587\u5b57"), "valid middle substring failed");
        check(s.substring(2, 2).isEmpty(), "valid empty substring failed");

        check(getCharsThrowsSIOOBE(s, -1, 2), "negative begin not rejected");
        check(getCharsThrowsSIOOBE(s, 3, 2), "begin greater than end not rejected");
        check(getCharsThrowsSIOOBE(s, 0, 5), "end greater than length not rejected");
        check(getCharsThrowsSIOOBE(s, 0, -1), "negative end not rejected");

        check(substringThrowsSIOOBE(s, -1, 2), "substring negative begin not rejected");
        check(substringThrowsSIOOBE(s, 3, 2), "substring begin greater than end not rejected");
        check(substringThrowsSIOOBE(s, 0, 5), "substring end greater than length not rejected");
    }
}
