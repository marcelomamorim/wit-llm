/* @test    @summary Test constructor Short(String s) with valid and invalid inputs    @run main ShortWitupTest */

import java.lang.Short;

public class ShortWitupTest {

    public static void main(String[] args) {
        testValidShortString();
        testInvalidShortString();
    }

    private static void testValidShortString() {
        String validString = "12345";
        try {
            Short s = new Short(validString);
            assert s != null : "Short object should not be null for valid input";
            assert s.shortValue() == 12345 : "Expected value 12345, got " + s.shortValue();
        } catch (NumberFormatException e) {
            throw new AssertionError("Unexpected NumberFormatException for valid input", e);
        }
    }

    private static void testInvalidShortString() {
        String invalidString = "notANumber";
        boolean thrown = false;
        try {
            new Short(invalidString);
        } catch (NumberFormatException e) {
            thrown = true;
        }
        assert thrown : "NumberFormatException expected for invalid input";
    }
}
