/*
 * @test
 * @summary Test checkValidIntValue method of ChronoField for valid and invalid inputs
 * @run main ChronoFieldWitupTest
 */

import java.time.temporal.ChronoField;

public class ChronoFieldWitupTest {

    public static void main(String[] args) {
        testCheckValidIntValueWithValidInput();
        testCheckValidIntValueWithInvalidInput();
    }

    static void testCheckValidIntValueWithValidInput() {
        long validValue = 5L;
        try {
            int result = ChronoField.YEAR.checkValidIntValue(validValue);
            System.out.println("Valid input test passed, result: " + result);
        } catch (Exception e) {
            System.err.println("Valid input test failed: " + e);
        }
    }

    static void testCheckValidIntValueWithInvalidInput() {
        long invalidValue = -1L; // Assuming negative values are invalid for demonstration
        try {
            int result = ChronoField.YEAR.checkValidIntValue(invalidValue);
            System.err.println("Invalid input test failed, result: " + result);
        } catch (Exception e) {
            System.out.println("Invalid input test passed, caught exception: " + e);
        }
    }
}
