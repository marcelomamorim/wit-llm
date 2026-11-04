/*
 * @test
 * @summary Test String.valueOf(char[], int, int) for valid and invalid inputs
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testValidValueOf();
        testInvalidValueOf();
    }

    private static void testValidValueOf() {
        char[] data = {'a', 'b', 'c', 'd', 'e'};
        // Normal case: offset=1, count=3 -> "bcd"
        String s = String.valueOf(data, 1, 3);
        if (!"bcd".equals(s)) {
            throw new AssertionError("Expected 'bcd' but got '" + s + "'");
        }
        // Edge case: offset=0, count=0 -> empty string
        s = String.valueOf(data, 0, 0);
        if (!"".equals(s)) {
            throw new AssertionError("Expected empty string but got '" + s + "'");
        }
        // Edge case: offset=4, count=1 -> "e"
        s = String.valueOf(data, 4, 1);
        if (!"e".equals(s)) {
            throw new AssertionError("Expected 'e' but got '" + s + "'");
        }
    }

    private static void testInvalidValueOf() {
        char[] data = {'x', 'y', 'z'};
        // offset < 0
        try {
            String.valueOf(data, -1, 1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset < 0");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        // count < 0
        try {
            String.valueOf(data, 0, -1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for count < 0");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        // count > data.length - offset
        try {
            String.valueOf(data, 1, 3); // data.length=3, offset=1, count=3 -> 3 > 3-1=2
            throw new AssertionError("Expected StringIndexOutOfBoundsException for count > data.length - offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
