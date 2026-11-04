/*
 * @test
 * @summary Verifies Character.getName(int) for valid, unassigned and invalid code points
 * @run main CharacterWitupTest
 */

public class CharacterWitupTest {

    public static void main(String[] args) {
        check("LATIN CAPITAL LETTER A", Character.getName(0x0041));
        check("SPACE", Character.getName(0x0020));

        if (Character.getName(0x0378) != null) {
            throw new RuntimeException("unassigned code point should yield null");
        }

        String pu = Character.getName(0xE000);
        if (pu == null || !pu.startsWith("PRIVATE USE AREA") || !pu.endsWith(" E000")) {
            throw new RuntimeException("unexpected name for U+E000: " + pu);
        }

        expectIllegalArgument(-1);
        expectIllegalArgument(0x110000);
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but got " + actual);
        }
    }

    static void expectIllegalArgument(int codePoint) {
        try {
            Character.getName(codePoint);
        } catch (IllegalArgumentException e) {
            if (e.getMessage() == null || !e.getMessage().startsWith("Not a valid Unicode code point")) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for " + codePoint);
    }
}
