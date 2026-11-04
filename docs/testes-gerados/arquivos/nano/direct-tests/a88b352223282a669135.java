/* @test    @summary Test Integer(String s) constructor with valid and invalid inputs    @run main IntegerWitupTest */

import java.lang.Integer;

public class IntegerWitupTest {

    public static void main(String[] args) {
        testValidString();
        testInvalidString();
    }

    static void testValidString() {
        String validNumber = "12345";
        Integer result = new Integer(validNumber);
        assert result != null : "Result should not be null";
        assert result.intValue() == 12345 : "Expected 12345, got " + result.intValue();
    }

    static void testInvalidString() {
        String invalidNumber = "abcde";
        try {
            new Integer(invalidNumber);
            assert false : "Expected NumberFormatException for input 'abcde'";
        } catch (NumberFormatException e) {
            // Expected exception
        }
    }
}
