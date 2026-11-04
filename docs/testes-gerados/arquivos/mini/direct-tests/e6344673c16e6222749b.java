/*
 * @test
 * @summary Test String constructor with char array, offset and count
 * @run main StringWitupTest
 */
public class StringWitupTest {
    public static void main(String[] args) {
        testValidConstruction();
        testEmptyStringConstruction();
        testOffsetZeroCountZero();
        testFullArrayConstruction();
    }

    private static void testValidConstruction() {
        char[] chars = {'a', 'b', 'c', 'd', 'e'};
        String s = new String(chars, 1, 3);
        if (!s.equals("bcd")) {
            throw new RuntimeException("Expected 'bcd' but got '" + s + "'");
        }
    }

    private static void testEmptyStringConstruction() {
        char[] chars = {'x', 'y', 'z'};
        String s = new String(chars, 1, 0);
        if (!s.equals("")) {
            throw new RuntimeException("Expected empty string but got '" + s + "'");
        }
    }

    private static void testOffsetZeroCountZero() {
        char[] chars = {};
        String s = new String(chars, 0, 0);
        if (!s.equals("")) {
            throw new RuntimeException("Expected empty string but got '" + s + "'");
        }
    }

    private static void testFullArrayConstruction() {
        char[] chars = {'h', 'e', 'l', 'l', 'o'};
        String s = new String(chars, 0, chars.length);
        if (!s.equals("hello")) {
            throw new RuntimeException("Expected 'hello' but got '" + s + "'");
        }
    }
}