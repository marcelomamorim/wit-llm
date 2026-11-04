/*
 * @test
 * @summary Checks observable codePointAt behavior on UTF16-backed StringBuilder, which reaches StringUTF16.codePointAtSB
 * @run main StringUTF16WitupTest
 */

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        StringBuilder bmp = new StringBuilder();
        bmp.append('\u4e00').append('\u4e8c');
        check(bmp.codePointAt(0) == 0x4e00, "bmp index 0");
        check(bmp.codePointAt(1) == 0x4e8c, "bmp index 1");

        StringBuilder pair = new StringBuilder();
        pair.append('\uD83D').append('\uDE00').append('\u4e00');
        check(pair.codePointAt(0) == 0x1F600, "valid surrogate pair");
        check(pair.codePointAt(1) == 0xDE00, "low surrogate alone");
        check(pair.codePointAt(2) == 0x4e00, "bmp after pair");

        StringBuilder lone = new StringBuilder();
        lone.append('\u4e00').append('\uD83D');
        check(lone.codePointAt(1) == 0xD83D, "high surrogate at end");

        expectOutOfBounds(bmp, -1);
        expectOutOfBounds(bmp, bmp.length());
        expectOutOfBounds(pair, -1);
        expectOutOfBounds(pair, pair.length());
    }

    private static void expectOutOfBounds(StringBuilder sb, int index) {
        try {
            sb.codePointAt(index);
        } catch (StringIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected StringIndexOutOfBoundsException for index " + index);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
