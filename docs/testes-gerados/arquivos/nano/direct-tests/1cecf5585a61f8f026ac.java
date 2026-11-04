/* @test    @summary Test Long(String s) constructor with valid and invalid inputs    @run main LongWitupTest */

import java.lang.Long;

public class LongWitupTest {

    public static void main(String[] args) {
        testValidString();
        testInvalidString();
    }

    static void testValidString() {
        String validNumber = "12345";
        Long longObj = new Long(validNumber);
        assert longObj != null : "Long object should not be null for valid input";
        assert longObj.longValue() == 12345L : "Expected value 12345L, but got " + longObj.longValue();
    }

    static void testInvalidString() {
        String invalidNumber = "notANumber";
        try {
            new Long(invalidNumber);
            assert false : "Expected NumberFormatException for invalid input";
        } catch (NumberFormatException e) {
            // Expected exception
        }
    }
}
