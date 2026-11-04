/*
 * @test
 * @summary Test String(char[], int, int) constructor for valid and invalid inputs
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testValidConstruction();
        testInvalidConstruction();
    }

    private static void testValidConstruction() {
        char[] chars = {'a', 'b', 'c', 'd', 'e'};
        // Normal case: offset=1, count=3 -> "bcd"
        String s = new String(chars, 1, 3);
        if (!s.equals("bcd")) {
            throw new Error("Expected 'bcd' but got '" + s + "'");
        }

        // Edge case: offset=0, count=0 -> empty string
        s = new String(chars, 0, 0);
        if (!s.equals("")) {
            throw new Error("Expected empty string but got '" + s + "'");
        }

        // Edge case: offset=chars.length, count=0 -> empty string
        s = new String(chars, chars.length, 0);
        if (!s.equals("")) {
            throw new Error("Expected empty string but got '" + s + "'");
        }
    }

    private static void testInvalidConstruction() {
        char[] chars = {'x', 'y', 'z'};

        // offset < 0
        try {
            new String(chars, -1, 1);
            throw new Error("Expected StringIndexOutOfBoundsException for offset < 0");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }

        // count < 0
        try {
            new String(chars, 0, -1);
            throw new Error("Expected StringIndexOutOfBoundsException for count < 0");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }

        // count > value.length - offset
        try {
            new String(chars, 1, 3); // chars.length=3, 3 > 3-1=2
            throw new Error("Expected StringIndexOutOfBoundsException for count > value.length - offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
