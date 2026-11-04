/*
 * @test
 * @summary Test java.time.Year.of(int) method for valid and invalid inputs
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.DateTimeException;

public class YearWitupTest {
    public static void main(String[] args) {
        testValidYear();
        testYearTooSmall();
        testYearTooLarge();
    }

    private static void testValidYear() {
        // Test with a valid year within range
        Year y = Year.of(2000);
        if (y.getValue() != 2000) {
            throw new AssertionError("Year.of(2000) returned Year with value " + y.getValue());
        }
    }

    private static void testYearTooSmall() {
        // Test with a year smaller than allowed minimum to check exception
        int tooSmall = Integer.MIN_VALUE;
        try {
            Year.of(tooSmall);
            throw new AssertionError("Year.of(" + tooSmall + ") did not throw DateTimeException");
        } catch (DateTimeException expected) {
            // Expected exception
        }
    }

    private static void testYearTooLarge() {
        // Test with a year larger than allowed maximum to check exception
        int tooLarge = Integer.MAX_VALUE;
        try {
            Year.of(tooLarge);
            throw new AssertionError("Year.of(" + tooLarge + ") did not throw DateTimeException");
        } catch (DateTimeException expected) {
            // Expected exception
        }
    }
}
