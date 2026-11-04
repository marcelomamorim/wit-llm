/*
 * @test
 * @summary Verifies Year.adjustInto sets the year of an ISO Year temporal and returns the adjusted value
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.temporal.Temporal;

public class YearWitupTest {

    public static void main(String[] args) {
        testAdjustIntoSetsYear();
        testAdjustIntoNegativeYear();
        testAdjustIntoReturnsYearInstance();
        testAdjustIntoIsIdempotentForSameYear();
    }

    static void testAdjustIntoSetsYear() {
        Year target = Year.of(2024);
        Temporal result = target.adjustInto(Year.of(1999));
        check(result instanceof Year, "result should be a Year");
        check(((Year) result).getValue() == 2024, "year should be adjusted to 2024");
    }

    static void testAdjustIntoNegativeYear() {
        Year target = Year.of(-5);
        Temporal result = target.adjustInto(Year.of(2000));
        check(((Year) result).getValue() == -5, "year should be adjusted to -5");
    }

    static void testAdjustIntoReturnsYearInstance() {
        Year target = Year.of(1600);
        Temporal result = target.adjustInto(Year.of(2100));
        check(result instanceof Year, "result should remain a Year");
        check(((Year) result).getValue() == 1600, "year should be 1600");
    }

    static void testAdjustIntoIsIdempotentForSameYear() {
        Year target = Year.of(2018);
        Temporal once = target.adjustInto(Year.of(2018));
        Temporal twice = target.adjustInto(once);
        check(((Year) once).getValue() == 2018, "first adjust should keep 2018");
        check(((Year) twice).getValue() == 2018, "second adjust should keep 2018");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
