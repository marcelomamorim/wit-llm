/*
 * @test
 * @summary Witup-derived tests for String.substring(int) exception and boundary behavior
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testNegativeBeginIndexThrows();
        testBeginIndexBeyondLengthThrows();
        testBoundaryAndNormalResults();
        testNonLatin1Substring();
    }

    static void testNegativeBeginIndexThrows() {
        expectSIOOBE("abc", -1);
        expectSIOOBE("", -1);
    }

    static void testBeginIndexBeyondLengthThrows() {
        expectSIOOBE("abc", 4);
        expectSIOOBE("", 1);
        expectSIOOBE("\u4e2d\u6587", 3);
    }

    static void testBoundaryAndNormalResults() {
        String s = "abc";
        check(s.substring(0) == s, "substring(0) must return this");
        check("abc".substring(1).equals("bc"), "substring(1) of abc");
        check("abc".substring(2).equals("c"), "substring(2) of abc");
        check("abc".substring(3).equals(""), "substring(length) must be empty");
        check("".substring(0).equals(""), "empty substring(0)");
    }

    static void testNonLatin1Substring() {
        String s = "\u4e2d\u6587abc";
        check(s.substring(2).equals("abc"), "non-Latin1 substring(2)");
        check(s.substring(1).equals("\u6587abc"), "non-Latin1 substring(1)");
        check(s.substring(5).equals(""), "non-Latin1 substring(length)");
    }

    static void expectSIOOBE(String s, int idx) {
        try {
            s.substring(idx);
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("Expected StringIndexOutOfBoundsException for index " + idx);
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}
