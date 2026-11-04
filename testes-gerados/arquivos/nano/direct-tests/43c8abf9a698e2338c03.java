/* @test    @summary Test Short.valueOf(String s) with valid and invalid inputs    @run main ShortWitupTest */

import java.lang.Short;

public class ShortWitupTest {

    public static void main(String[] args) {
        testValidValueOf();
        testInvalidValueOf();
    }

    static void testValidValueOf() {
        String validInput = "12345";
        Short result = Short.valueOf(validInput);
        assert result != null : "Expected non-null result for valid input";
        assert result.shortValue() == 12345 : "Expected value 12345, got " + result;
    }

    static void testInvalidValueOf() {
        String invalidInput = "notANumber";
        boolean exceptionThrown = false;
        try {
            Short.valueOf(invalidInput);
        } catch (NumberFormatException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected NumberFormatException for invalid input";
    }
}
