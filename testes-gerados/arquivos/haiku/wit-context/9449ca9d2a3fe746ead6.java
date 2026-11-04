/*
 * @test
 * @summary Witup-derived tests for String.codePointBefore(int)
 * @run main StringWitupTest
 */

public class StringWitupTest {

    public static void main(String[] args) {
        String abc = "abc";

        check(abc.codePointBefore(1) == 'a', "codePointBefore(1) of abc should be 'a'");
        check(abc.codePointBefore(2) == 'b', "codePointBefore(2) of abc should be 'b'");
        check(abc.codePointBefore(3) == 'c', "codePointBefore(3) of abc should be 'c'");

        expectSIOOBE(abc, 0);
        expectSIOOBE(abc, -1);
        expectSIOOBE(abc, 4);
        expectSIOOBE("", 0);

        String latin1 = "x" + (char) 0xE9;
        check(latin1.codePointBefore(2) == 0xE9, "Latin-1 char should be returned unsigned");

        String supp = "a" + new String(Character.toChars(0x1F600));
        check(supp.length() == 3, "supplementary string length should be 3");
        check(supp.codePointBefore(3) == 0x1F600, "surrogate pair should be returned as one code point");
    }

    static void expectSIOOBE(String s, int index) {
        try {
            s.codePointBefore(index);
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected StringIndexOutOfBoundsException for index " + index);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
