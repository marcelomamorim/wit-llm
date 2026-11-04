/*
 * @test
 * @summary Tests observable behavior and bounds exceptions of String.substring(int, int)
 * @run main StringWitupTest
 */
public class StringWitupTest {
    public static void main(String[] args) {
        if (!"hello".substring(1, 3).equals("el")) {
            throw new RuntimeException("basic substring failed");
        }
        if (!"hello".substring(0, 0).equals("")) {
            throw new RuntimeException("empty prefix failed");
        }
        if (!"hello".substring(5, 5).equals("")) {
            throw new RuntimeException("empty suffix at end failed");
        }
        if (!"".substring(0, 0).equals("")) {
            throw new RuntimeException("empty string substring failed");
        }
        if (!"hello".substring(2).equals("llo")) {
            throw new RuntimeException("single-arg substring sanity failed");
        }
        String s = "hello";
        if (s.substring(0, s.length()) != s) {
            throw new RuntimeException("full range should return this");
        }
        if (!"\u4E2D\u6587abc".substring(1, 2).equals("\u6587")) {
            throw new RuntimeException("non-Latin1 substring failed");
        }
        if (!"a\uD83D\uDE00b".substring(1, 3).equals("\uD83D\uDE00")) {
            throw new RuntimeException("surrogate pair substring failed");
        }
        expectSIOOBE("hello", 3, 2);
        expectSIOOBE("hello", -1, 2);
        expectSIOOBE("hello", 0, 6);
        expectSIOOBE("hello", 6, 6);
        expectSIOOBE("", 0, 1);
    }

    static void expectSIOOBE(String s, int begin, int end) {
        try {
            s.substring(begin, end);
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("no StringIndexOutOfBoundsException for " + begin + "," + end);
    }
}
