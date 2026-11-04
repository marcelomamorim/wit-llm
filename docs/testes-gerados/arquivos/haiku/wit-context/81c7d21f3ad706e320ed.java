/*
 * @test
 * @summary Witup-derived behavioral tests for Character.toChars(int)
 * @run main CharacterWitupTest
 */

public class CharacterWitupTest {

    public static void main(String[] args) {
        checkBmpCodePoints();
        checkSupplementaryCodePoints();
        checkInvalidCodePoints();
    }

    static void checkBmpCodePoints() {
        char[] r = Character.toChars(0x41);
        if (r.length != 1 || r[0] != 'A') {
            fail("U+0041 should map to a single char 'A'");
        }
        r = Character.toChars(0xD800);
        if (r.length != 1 || r[0] != (char) 0xD800) {
            fail("Surrogate code point U+D800 should map to a single char");
        }
        r = Character.toChars(0xFFFF);
        if (r.length != 1 || r[0] != (char) 0xFFFF) {
            fail("U+FFFF should map to a single char");
        }
    }

    static void checkSupplementaryCodePoints() {
        int[] cps = {0x10000, 0x1F600, 0x10FFFF};
        for (int cp : cps) {
            char[] r = Character.toChars(cp);
            if (r.length != 2) {
                fail("Supplementary code point must produce 2 chars: " + cp);
            }
            if (!Character.isHighSurrogate(r[0]) || !Character.isLowSurrogate(r[1])) {
                fail("Expected surrogate pair for: " + cp);
            }
            if (Character.toCodePoint(r[0], r[1]) != cp) {
                fail("Round trip failed for: " + cp);
            }
        }
    }

    static void checkInvalidCodePoints() {
        int[] cps = {-1, 0x110000, Integer.MIN_VALUE, Integer.MAX_VALUE};
        for (int cp : cps) {
            boolean thrown = false;
            try {
                Character.toChars(cp);
            } catch (IllegalArgumentException expected) {
                thrown = true;
            }
            if (!thrown) {
                fail("Expected IllegalArgumentException for: " + cp);
            }
        }
    }

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }
}
