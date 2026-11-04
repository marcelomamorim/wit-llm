/*
 * @test
 * @summary Test for Year.now(Clock) method, including null input handling.
 * @run main YearWitupTest
 */

import java.time.Year;
import java.time.Clock;
import java.time.LocalDate;

public class YearWitupTest {

    public static void main(String[] args) {
        testNowWithValidClock();
        testNowWithNullClock();
    }

    static void testNowWithValidClock() {
        Clock clock = Clock.systemUTC();
        try {
            Year y = Year.now(clock);
            if (y == null) {
                throw new AssertionError("Year.now(clock) returned null");
            }
            int yearValue = y.getValue();
            int expectedYear = LocalDate.now(clock).getYear();
            if (yearValue != expectedYear) {
                throw new AssertionError("Expected year: " + expectedYear + ", but got: " + yearValue);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testNowWithValidClock", e);
        }
    }

    static void testNowWithNullClock() {
        try {
            Year.now(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        }
    }
}
