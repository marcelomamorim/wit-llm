/* @test
   @summary Tests Character.getName(int) for valid and invalid code points.
   @run main CharacterWitupTest_P2125497be365
 */
import java.util.Locale;
public class CharacterWitupTest_P2125497be365 {
    public static void main(String[] args) {
        testValidCodePoint();
        testInvalidCodePoint();
        testCodePointInUnicodeBlock();
        testUnassignedCodePoint();
    }

    static void testValidCodePoint() {
        int codePoint = 0x0041; // 'A'
        String name = Character.getName(codePoint);
        if (name == null || !name.contains("A")) {
            throw new AssertionError("Expected name containing 'A' for code point 0x0041, but got: " + name);
        }
    }

    static void testInvalidCodePoint() {
        int invalidCodePoint = -1;
        try {
            Character.getName(invalidCodePoint);
            throw new AssertionError("Expected IllegalArgumentException for code point -1");
        } catch (IllegalArgumentException e) {
            // expected
        }
        int invalidCodePoint2 = 0x110000; // beyond Unicode range
        try {
            Character.getName(invalidCodePoint2);
            throw new AssertionError("Expected IllegalArgumentException for code point 0x110000");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testCodePointInUnicodeBlock() {
        int codePoint = 0x1F600; // 😀
        String name = Character.getName(codePoint);
        if (name == null || !name.toUpperCase(Locale.ROOT).contains("1F600")) {
            throw new AssertionError("Expected name containing '1F600' for code point 0x1F600, but got: " + name);
        }
    }

    static void testUnassignedCodePoint() {
        int unassignedCodePoint = 0x0378; // unassigned in Unicode
        String name = Character.getName(unassignedCodePoint);
        if (name != null) {
            throw new AssertionError("Expected null for unassigned code point 0x0378, but got: " + name);
        }
    }
}
