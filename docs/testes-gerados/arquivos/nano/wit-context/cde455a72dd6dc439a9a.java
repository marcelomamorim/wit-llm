/*
 * @test
 * @summary Tests for java.time.MonthDay class methods
 * @run main MonthDayWitupTest
 */

import java.time.Month;
import java.time.MonthDay;
import java.time.DateTimeException;

public class MonthDayWitupTest {

    public static void main(String[] args) {
        testOfValidInputs();
        testOfNullMonth();
        testOfInvalidDayTooLarge();
        testOfInvalidDayTooSmall();
    }

    static void testOfValidInputs() {
        try {
            Month month = Month.JANUARY;
            int day = 15;
            MonthDay md = MonthDay.of(month, day);
            if (md.getMonthValue() != month.getValue() || md.getDayOfMonth() != day) {
                throw new AssertionError("Valid inputs test failed");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid inputs test", e);
        }
    }

    static void testOfNullMonth() {
        try {
            MonthDay.of(null, 10);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }

    static void testOfInvalidDayTooLarge() {
        try {
            Month month = Month.FEBRUARY;
            int invalidDay = 30; // February max length is 29
            MonthDay.of(month, invalidDay);
            throw new AssertionError("Expected DateTimeException not thrown for invalid day");
        } catch (DateTimeException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }

    static void testOfInvalidDayTooSmall() {
        try {
            Month month = Month.JANUARY;
            int invalidDay = 0; // days start at 1
            MonthDay.of(month, invalidDay);
            throw new AssertionError("Expected DateTimeException not thrown for day zero");
        } catch (DateTimeException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
