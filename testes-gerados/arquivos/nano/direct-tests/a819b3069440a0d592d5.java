/*
 * @test
 * @summary Test the static method Year.of(int) for valid and invalid inputs
 * @run main YearWitupTest
 */

import java.time.Year;

public class YearWitupTest {

    public static void main(String[] args) {
        testValidYear();
        testInvalidYear();
    }

    static void testValidYear() {
        int[] validYears = {1, 1000, 2018, 9999, -1, -1000};
        for (int y : validYears) {
            Year year = Year.of(y);
            assert year.getValue() == y : "Expected year: " + y + ", but got: " + year.getValue();
        }
    }

    static void testInvalidYear() {
        int[] invalidYears = {Integer.MAX_VALUE, Integer.MIN_VALUE};
        for (int y : invalidYears) {
            try {
                Year.of(y);
                assert false : "Expected exception for year: " + y;
            } catch (DateTimeException e) {
                // Expected exception
            }
        }
    }
}
