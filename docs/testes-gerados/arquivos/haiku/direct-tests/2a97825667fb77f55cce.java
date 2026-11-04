/*
 * @test
 * @summary Verifies String.codePointCount for Latin1 and UTF16 strings, surrogate handling and index validation
 * @run main StringWitupTest
 */
public class StringWitupTest {
    static final String HI = String.valueOf((char) 0xD83D);
    static final String LO = String.valueOf((char) 0xDE00);

    public static void main(String[] args) {
        check("".codePointCount(0, 0) == 0, "empty string");
        check("abc".codePointCount(0, 3) == 3, "latin1 full range");
        check("abc".codePointCount(1, 2) == 1, "latin1 sub range");
        check("abc".codePointCount(2, 2) == 0, "latin1 empty range");

        String pair = HI + LO;
        check(pair.length() == 2, "surrogate pair length");
        check(pair.codePointCount(0, 2) == 1, "surrogate pair counts as one code point");

        String mixed = "a" + HI + LO + "b";
        check(mixed.codePointCount(0, 4) == 3, "mixed full range");
        check(mixed.codePointCount(1, 3) == 1, "pair inside range");
        check(mixed.codePointCount(0, 1) == 1, "leading latin char");

        String lone = HI;
        check(lone.codePointCount(0, 1) == 1, "unpaired high surrogate counts as one");

        expectIOOBE("abc", -1, 2);
        expectIOOBE("abc", 2, 1);
        expectIOOBE("abc", 0, 4);
        expectIOOBE(pair, 0, 3);
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void expectIOOBE(String s, int begin, int end) {
        try {
            s.codePointCount(begin, end);
        } catch (IndexOutOfBoundsException ex) {
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for " + begin + "," + end);
    }
}
