/*
 * @test
 * @summary Test String constructor String(int[] codePoints, int offset, int count) for bounds and normal cases
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testValidConstruction();
        testEmptyConstruction();
        testInvalidBounds();
    }

    private static void testValidConstruction() {
        int[] codePoints = {65, 66, 67, 68, 69}; // A B C D E
        String s = new String(codePoints, 1, 3); // B C D
        if (!s.equals("BCD")) {
            throw new Error("Expected 'BCD' but got '" + s + "'");
        }
    }

    private static void testEmptyConstruction() {
        int[] codePoints = {65, 66};
        String s = new String(codePoints, 0, 0);
        if (!s.equals("")) {
            throw new Error("Expected empty string but got '" + s + "'");
        }
    }

    private static void testInvalidBounds() {
        int[] codePoints = {65, 66, 67};
        // offset < 0
        try {
            new String(codePoints, -1, 2);
            throw new Error("Expected StringIndexOutOfBoundsException for offset < 0");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        // count < 0
        try {
            new String(codePoints, 0, -1);
            throw new Error("Expected StringIndexOutOfBoundsException for count < 0");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        // offset + count > codePoints.length
        try {
            new String(codePoints, 1, 3);
            throw new Error("Expected StringIndexOutOfBoundsException for offset + count > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
