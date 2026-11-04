/*
 * @test
 * @summary Witup-style checks for Character.getName(int) valid, unassigned and invalid code points
 * @run main CharacterWitupTest
 */

public class CharacterWitupTest {

    public static void main(String[] args) {
        check("LATIN CAPITAL LETTER A", Character.getName(0x0041));
        check("LATIN SMALL LETTER A", Character.getName('a'));
        check("SPACE", Character.getName(0x0020));
        if (Character.getName(0x0378) != null) {
            throw new RuntimeException("expected null for unassigned U+0378");
        }
        expectIAE(-1);
        expectIAE(0x110000);
        expectIAE(Integer.MIN_VALUE);
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }

    static void expectIAE(int codePoint) {
        try {
            Character.getName(codePoint);
        } catch (IllegalArgumentException e) {
            if (e.getMessage() == null || !e.getMessage().startsWith("Not a valid Unicode code point")) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("IllegalArgumentException not thrown for " + codePoint);
    }
}
