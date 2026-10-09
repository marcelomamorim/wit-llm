/*
 * @test
 * @summary Test for LocalDate.datesUntil method, including exception scenarios.
 * @run main LocalDateWitupTest_P6771ccb0ca93
 */

import java.time.LocalDate;
import java.util.stream.Stream;

public class LocalDateWitupTest_P6771ccb0ca93 {

    public static void main(String[] args) {
        testDatesUntilNormal();
        testDatesUntilEndBeforeStart();
        testDatesUntilInvalidInputs();
    }

    static void testDatesUntilNormal() {
        try {
            LocalDate startDate = LocalDate.of(2020, 1, 1);
            LocalDate endDate = LocalDate.of(2020, 1, 5);
            Stream<LocalDate> stream = startDate.datesUntil(endDate);
            LocalDate[] dates = stream.toArray(LocalDate[]::new);
            if (dates.length != 4) {
                throw new AssertionError("Expected 4 dates, got " + dates.length);
            }
            if (!dates[0].equals(LocalDate.of(2020, 1, 1))) {
                throw new AssertionError("First date mismatch");
            }
            if (!dates[3].equals(LocalDate.of(2020, 1, 4))) {
                throw new AssertionError("Last date mismatch");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in normal case: " + e);
        }
    }

    static void testDatesUntilEndBeforeStart() {
        try {
            LocalDate startDate = LocalDate.of(2020, 1, 5);
            LocalDate endDate = LocalDate.of(2020, 1, 1);
            startDate.datesUntil(endDate);
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    static void testDatesUntilInvalidInputs() {
        try {
            LocalDate startDate = LocalDate.of(2020, 1, 1);
            // Creating a LocalDate with invalid epoch day to simulate invalid input
            // Since LocalDate.ofEpochDay is package-private, we test with null or invalid object via from()
            // but as from() is public, we can test with null, which should throw NullPointerException
            LocalDate invalidDate = null;
            startDate.datesUntil(invalidDate);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
