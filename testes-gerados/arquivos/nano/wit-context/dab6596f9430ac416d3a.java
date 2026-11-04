/*
 * @test
 * @summary Test for java.time.MonthDay.get(TemporalField)
 * @run main MonthDayWitupTest
 */

import java.time.MonthDay;
import java.time.temporal.ChronoField;
import java.time.temporal.UnsupportedTemporalTypeException;

public class MonthDayWitupTest {

    public static void main(String[] args) {
        testGetWithValidField();
        testGetWithInvalidField();
    }

    static void testGetWithValidField() {
        try {
            MonthDay md = MonthDay.of(5, 15);
            int day = md.get(ChronoField.DAY_OF_MONTH);
            int month = md.get(ChronoField.MONTH_OF_YEAR);
            if (day != 15 || month != 5) {
                throw new AssertionError("Unexpected get result: " + day + ", " + month);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testGetWithValidField", e);
        }
    }

    static void testGetWithInvalidField() {
        try {
            MonthDay md = MonthDay.of(5, 15);
            md.get(ChronoField.YEAR);
            throw new AssertionError("Expected UnsupportedTemporalTypeException not thrown");
        } catch (UnsupportedTemporalTypeException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        }
    }
}
