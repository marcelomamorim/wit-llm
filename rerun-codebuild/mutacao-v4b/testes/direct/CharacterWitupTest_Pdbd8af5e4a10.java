/* @test
   @summary Verifies Character.codePointAt method behavior with valid and invalid inputs.
   @run main CharacterWitupTest_Pdbd8af5e4a10
 */
import java.lang.Character;
public class CharacterWitupTest_Pdbd8af5e4a10 {
    public static void main(String[] args) {
        testCodePointAtValid();
        testCodePointAtIndexOutOfBounds();
        testCodePointAtNegativeIndex();
        testCodePointAtLimitExceedsLength();
    }
    private static void testCodePointAtValid() {
        char[] chars = {'A', '\uD83D', '\uDE00'}; // 'A', 😀 (surrogate pair)
        int cp1 = Character.codePointAt(chars, 0, chars.length);
        int cp2 = Character.codePointAt(chars, 1, chars.length);
        assert cp1 == 65 : "Expected code point 65 for 'A', but got " + cp1;
        assert cp2 == 0x1F600 : "Expected code point 0x1F600 for 😀, but got " + cp2;
    }
    private static void testCodePointAtIndexOutOfBounds() {
        char[] chars = {'A'};
        try {
            Character.codePointAt(chars, 1, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index 1 with limit 1");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
    private static void testCodePointAtNegativeIndex() {
        char[] chars = {'A'};
        try {
            Character.codePointAt(chars, -1, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
    private static void testCodePointAtLimitExceedsLength() {
        char[] chars = {'A'};
        try {
            Character.codePointAt(chars, 0, 2);
            // Should not throw, as limit > length is allowed if limit <= a.length
            int cp = Character.codePointAt(chars, 0, 2);
            assert cp == 65 : "Expected code point 65 for 'A', but got " + cp;
        } catch (IndexOutOfBoundsException e) {
            throw new AssertionError("Did not expect exception when limit exceeds array length but limit <= a.length", e);
        }
    }
}
