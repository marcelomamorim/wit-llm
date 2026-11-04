/*
 * @test
 * @summary Test YearMonth.of(int, int) method for valid and invalid inputs
 * @run main YearMonthWitupTest
 */

import java.time.YearMonth;

public class YearMonthWitupTest {

    public static void main(String[] args) {
        testValidInputs();
        testInvalidYear();
        testInvalidMonth();
    }

    static void testValidInputs() {
        try {
            YearMonth ym = YearMonth.of(2023, 5);
            assert ym.getYear() == 2023 : "Year should be 2023";
            assert ym.getMonthValue() == 5 : "Month should be 5";
        } catch (Exception e) {
            throw new AssertionError("Valid input caused exception", e);
        }
    }

    static void testInvalidYear() {
        try {
            YearMonth.of(Integer.MIN_VALUE, 1);
            throw new AssertionError("Expected exception for invalid year not thrown");
        } catch (DateTimeException e) {
            // expected
        }
    }

    static void testInvalidMonth() {
        try {
            YearMonth.of(2023, 0);
            throw new AssertionError("Expected exception for invalid month not thrown");
        } catch (DateTimeException e) {
            // expected
        }
        try {
            YearMonth.of(2023, 13);
            throw new AssertionError("Expected exception for invalid month not thrown");
        } catch (DateTimeException e) {
            // expected
        }
    }
}
