/*
 * @test
 * @summary Test for ThaiBuddhistDate.until(ChronoLocalDate)
 * @run main ThaiBuddhistDateWitupTest
 */

import java.time.chrono.ThaiBuddhistDate;
import java.time.chrono.ChronoLocalDate;
import java.time.Period;
import java.time.chrono.ThaiBuddhistChronology;

public class ThaiBuddhistDateWitupTest {

    public static void main(String[] args) {
        testUntilWithValidDate();
        testUntilWithNull();
    }

    static void testUntilWithValidDate() {
        try {
            ThaiBuddhistDate date1 = ThaiBuddhistDate.of(2564, 5, 15); // Arbitrary valid date
            ThaiBuddhistDate date2 = ThaiBuddhistDate.of(2565, 6, 20);
            ChronoLocalDate result = date1.until(date2);
            if (result == null) {
                throw new AssertionError("Result should not be null for valid dates");
            }
            // Additional checks can be added here if behavior is known
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testUntilWithValidDate", e);
        }
    }

    static void testUntilWithNull() {
        try {
            ThaiBuddhistDate date = ThaiBuddhistDate.of(2564, 5, 15);
            date.until(null);
            throw new AssertionError("Expected NullPointerException for null endDate");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }
}
