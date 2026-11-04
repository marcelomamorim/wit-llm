/*
 * @test
 * @summary Tests for LocalDate.of(int, int, int)
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.time.Month;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testValidDate();
        testInvalidMonth();
        testInvalidDay();
        testBoundaryYear();
    }

    static void testValidDate() {
        LocalDate date = LocalDate.of(2023, 3, 15);
        assert date.getYear() == 2023 : "Year mismatch";
        assert date.getMonth() == Month.MARCH : "Month mismatch";
        assert date.getDayOfMonth() == 15 : "Day of month mismatch";
    }

    static void testInvalidMonth() {
        try {
            LocalDate.of(2023, 0, 10);
            assert false : "Expected exception for invalid month";
        } catch (DateTimeException e) {
            // expected
        }
        try {
            LocalDate.of(2023, 13, 10);
            assert false : "Expected exception for invalid month";
        } catch (DateTimeException e) {
            // expected
        }
    }

    static void testInvalidDay() {
        try {
            LocalDate.of(2023, 2, 29);
            assert false : "Expected exception for invalid day";
        } catch (DateTimeException e) {
            // expected
        }
        try {
            LocalDate.of(2023, 4, 31);
            assert false : "Expected exception for invalid day";
        } catch (DateTimeException e) {
            // expected
        }
    }

    static void testBoundaryYear() {
        LocalDate date = LocalDate.of(1, 1, 1);
        assert date.getYear() == 1 : "Year mismatch at lower boundary";
        date = LocalDate.of(9999, 12, 31);
        assert date.getYear() == 9999 : "Year mismatch at upper boundary";
    }
}
