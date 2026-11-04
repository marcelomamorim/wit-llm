/*
 * @test
 * @summary Witup-generated checks for Character.toChars(int, char[], int)
 * @run main CharacterWitupTest
 */

public class CharacterWitupTest {
    private static int failures = 0;

    private static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAIL: " + msg);
        }
    }

    private static void expectIllegalArgument(int codePoint) {
        char[] dst = new char[2];
        boolean thrown = false;
        try {
            Character.toChars(codePoint, dst, 0);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "expected IllegalArgumentException for code point 0x" + Integer.toHexString(codePoint));
        check(dst[0] == 0 && dst[1] == 0, "dst must not be modified for invalid code point 0x" + Integer.toHexString(codePoint));
    }

    public static void main(String[] args) {
        char[] dst = new char[4];

        int n = Character.toChars(0x41, dst, 0);
        check(n == 1, "BMP code point should return 1");
        check(dst[0] == 'A', "BMP code point should be written at dstIndex");

        n = Character.toChars(0xD800, dst, 2);
        check(n == 1, "surrogate BMP value 0xD800 should return 1");
        check(dst[2] == (char) 0xD800, "surrogate BMP value should be stored as-is");

        dst = new char[3];
        n = Character.toChars(0x1F600, dst, 1);
        check(n == 2, "supplementary code point should return 2");
        check(dst[0] == 0, "element before dstIndex must be unchanged");
        check(dst[1] == 0xD83D, "high surrogate for U+1F600 expected 0xD83D");
        check(dst[2] == 0xDE00, "low surrogate for U+1F600 expected 0xDE00");

        dst = new char[2];
        n = Character.toChars(0x10FFFF, dst, 0);
        check(n == 2, "max code point should return 2");
        check(dst[0] == 0xDBFF && dst[1] == 0xDFFF, "surrogates for U+10FFFF expected DBFF DFFF");

        expectIllegalArgument(-1);
        expectIllegalArgument(0x110000);
        expectIllegalArgument(Integer.MIN_VALUE);

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
