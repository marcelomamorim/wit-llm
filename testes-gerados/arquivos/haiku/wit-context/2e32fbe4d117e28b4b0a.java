/*
 * @test
 * @summary Witup-generated observable checks for StringUTF16.putCharSB via StringBuilder.setCharAt on UTF16-coded builders
 * @run main StringUTF16WitupTest
 */

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        char cjk = (char) 0x4e2d;
        char euro = (char) 0x20ac;
        char eAcute = (char) 0x00e9;

        StringBuilder sb = new StringBuilder();
        sb.append(cjk).append(cjk).append(cjk);

        sb.setCharAt(1, 'a');
        check(sb.length() == 3, "length changed after setCharAt");
        check(sb.charAt(0) == cjk, "index 0 changed unexpectedly");
        check(sb.charAt(1) == 'a', "index 1 not updated to 'a'");
        check(sb.charAt(2) == cjk, "index 2 changed unexpectedly");

        sb.setCharAt(0, euro);
        check(sb.charAt(0) == euro, "index 0 not updated to euro sign");
        check(sb.charAt(1) == 'a', "index 1 changed unexpectedly");

        sb.setCharAt(2, eAcute);
        check(sb.charAt(2) == eAcute, "index 2 not updated to e-acute");
        check(sb.length() == 3, "length changed after final setCharAt");

        expectIndexFailure(sb, 3, 'x');
        expectIndexFailure(sb, -1, 'x');

        check(sb.charAt(0) == euro && sb.charAt(1) == 'a' && sb.charAt(2) == eAcute,
              "content changed after rejected out-of-range writes");
    }

    static void expectIndexFailure(StringBuilder sb, int index, char c) {
        try {
            sb.setCharAt(index, c);
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
