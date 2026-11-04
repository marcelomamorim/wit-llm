/*
 * @test
 * @summary Basic tests for Integer.parseInt(String) method
 * @run main IntegerWitupTest
 */
public class IntegerWitupTest {
    public static void main(String[] args) {
        // Test parsing valid decimal strings
        testParseInt("0", 0);
        testParseInt("123", 123);
        testParseInt("-456", -456);
        testParseInt("2147483647", Integer.MAX_VALUE);
        testParseInt("-2147483648", Integer.MIN_VALUE);

        // Test parsing invalid strings that should throw NumberFormatException
        testParseIntThrows(null);
        testParseIntThrows("");
        testParseIntThrows("+123"); // '+' sign not accepted by parseInt(String)
        testParseIntThrows("12a3");
        testParseIntThrows("--1");
        testParseIntThrows(" 123");
        testParseIntThrows("123 ");
        testParseIntThrows("2147483648"); // overflow
        testParseIntThrows("-2147483649"); // underflow
    }

    private static void testParseInt(String input, int expected) {
        int result = Integer.parseInt(input);
        if (result != expected) {
            throw new AssertionError("parseInt(\"" + input + "\") returned " + result + ", expected " + expected);
        }
    }

    private static void testParseIntThrows(String input) {
        try {
            Integer.parseInt(input);
            throw new AssertionError("parseInt(\"" + input + "\") did not throw NumberFormatException");
        } catch (NumberFormatException | NullPointerException e) {
            // Expected exception
        }
    }
}
