/*
 * @test
 * @summary Tests for java.time.chrono.HijrahChronology.date(int, int, int)
 * @run main HijrahChronologyWitupTest
 */

import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;
import java.time.DateTimeException;

public class HijrahChronologyWitupTest {

    public static void main(String[] args) {
        testValidDate();
        testInvalidDateBelowRange();
        testInvalidDateAboveRange();
    }

    static void testValidDate() {
        try {
            HijrahChronology chrono = HijrahChronology.INSTANCE;
            HijrahDate date = chrono.date(1442, 9, 15);
            if (date == null) {
                throw new AssertionError("Expected non-null HijrahDate for valid input");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid date: " + e);
        }
    }

    static void testInvalidDateBelowRange() {
        try {
            HijrahChronology chrono = HijrahChronology.INSTANCE;
            // Using a year, month, day that is below valid range, e.g., negative year
            chrono.date(-1, 1, 1);
            throw new AssertionError("Expected DateTimeException for date below range");
        } catch (DateTimeException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    static void testInvalidDateAboveRange() {
        try {
            HijrahChronology chrono = HijrahChronology.INSTANCE;
            // Using a year, month, day that is above valid range, e.g., very large year
            chrono.date(99999, 1, 1);
            throw new AssertionError("Expected DateTimeException for date above range");
        } catch (DateTimeException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
