/*
 * @test
 * @summary Test String.copyValueOf(char[], int, int) for valid and invalid inputs
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testValidCopyValueOf();
        testInvalidCopyValueOf();
        System.out.println("StringWitupTest passed.");
    }

    private static void testValidCopyValueOf() {
        char[] data = {'a', 'b', 'c', 'd', 'e'};
        // Normal case: offset=1, count=3 -> "bcd"
        String s = String.copyValueOf(data, 1, 3);
        if (!"bcd".equals(s)) {
            throw new AssertionError("Expected 'bcd' but got '" + s + "'");
        }
        // Edge case: offset=0, count=0 -> empty string
        s = String.copyValueOf(data, 0, 0);
        if (!"".equals(s)) {
            throw new AssertionError("Expected empty string but got '" + s + "'");
        }
        // Edge case: offset=0, count=data.length -> full string
        s = String.copyValueOf(data, 0, data.length);
        if (!"abcde".equals(s)) {
            throw new AssertionError("Expected 'abcde' but got '" + s + "'");
        }
    }

    private static void testInvalidCopyValueOf() {
        char[] data = {'x', 'y', 'z'};
        // offset < 0
        try {
            String.copyValueOf(data, -1, 1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset < 0");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        // count < 0
        try {
            String.copyValueOf(data, 0, -1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for count < 0");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
        // count > data.length - offset
        try {
            String.copyValueOf(data, 1, 3); // data.length=3, 3 > 3-1=2
            throw new AssertionError("Expected StringIndexOutOfBoundsException for count > data.length - offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
