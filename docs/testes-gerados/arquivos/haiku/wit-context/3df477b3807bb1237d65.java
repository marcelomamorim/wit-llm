/*
 * @test
 * @summary Observable behavior of appending a char range to a UTF16-coded StringBuilder, which exercises the StringUTF16.putCharsSB path
 * @run main StringUTF16WitupTest
 */

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder().append('\u4e00');
        sb.append("abcdef", 1, 4);
        check("\u4e00bcd", sb.toString());

        StringBuilder empty = new StringBuilder().append('\u4e00');
        empty.append("abc", 2, 2);
        check("\u4e00", empty.toString());

        StringBuilder full = new StringBuilder().append('\u4e00');
        full.append("xyz", 0, 3);
        check("\u4e00xyz", full.toString());

        expectIndexFailure("abc", 2, 1);
        expectIndexFailure("abc", 0, 4);
        expectIndexFailure("abc", -1, 2);
    }

    private static void expectIndexFailure(String s, int start, int end) {
        StringBuilder sb = new StringBuilder().append('\u4e00');
        try {
            sb.append(s, start, end);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("Expected IndexOutOfBoundsException for range " + start + "," + end);
    }

    private static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but was " + actual);
        }
    }
}
