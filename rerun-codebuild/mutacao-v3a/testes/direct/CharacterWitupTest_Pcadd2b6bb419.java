/* @test @summary Test Character.codePointAt with various char arrays and indices @run main CharacterWitupTest_Pcadd2b6bb419 */

import java.lang.Character;

public class CharacterWitupTest_Pcadd2b6bb419 {
    public static void main(String[] args) {
        testCodePointAtBasic();
        testCodePointAtEdgeCases();
        testCodePointAtInvalidIndices();
    }

    static void testCodePointAtBasic() {
        char[] data = {'A', '\uD83D', '\uDE00', 'Z'}; // 'A', smiley face (surrogate pair), 'Z'
        int cp1 = Character.codePointAt(data, 0, data.length); // 'A'
        int cp2 = Character.codePointAt(data, 1, data.length); // surrogate pair for U+1F600
        int cp3 = Character.codePointAt(data, 2, data.length); // 'Z'
        assert cp1 == 65 : "Expected 65 at index 0, got " + cp1;
        assert cp2 == 0x1F600 : "Expected U+1F600 at index 1, got " + Integer.toHexString(cp2);
        assert cp3 == 90 : "Expected 90 at index 2, got " + cp3;
    }

    static void testCodePointAtEdgeCases() {
        char[] data = {'A'};
        int cp = Character.codePointAt(data, 0, data.length); // single character
        assert cp == 65 : "Expected 65 at index 0, got " + cp;

        char[] empty = {};
        try {
            Character.codePointAt(empty, 0, 0);
            assert false : "Should have thrown IndexOutOfBoundsException";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testCodePointAtInvalidIndices() {
        char[] data = {'A', 'B'};
        try {
            Character.codePointAt(data, data.length, data.length);
            assert false : "Should have thrown IndexOutOfBoundsException";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            Character.codePointAt(data, -1, data.length);
            assert false : "Should have thrown IndexOutOfBoundsException";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
