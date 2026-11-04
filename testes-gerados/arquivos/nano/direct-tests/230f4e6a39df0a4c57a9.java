/*
 * @test
 * @summary Test HijrahDate.now() method for correct default behavior
 * @run main HijrahDateWitupTest
 */

import java.time.chrono.HijrahDate;
import java.time.Clock;

public class HijrahDateWitupTest {

    public static void main(String[] args) {
        testNowReturnsHijrahDate();
    }

    public static void testNowReturnsHijrahDate() {
        HijrahDate date = HijrahDate.now();
        if (date == null) {
            throw new AssertionError("HijrahDate.now() returned null");
        }
        // Additional check: verify that the returned object is an instance of HijrahDate
        if (!(date instanceof HijrahDate)) {
            throw new AssertionError("Returned object is not an instance of HijrahDate");
        }
        // Optional: check that the date is within a reasonable range (e.g., year > 0)
        int year = date.get( java.time.temporal.ChronoField.YEAR );
        if (year <= 0) {
            throw new AssertionError("HijrahDate year is not positive: " + year);
        }
        System.out.println("testNowReturnsHijrahDate passed.");
    }
}
