/*
 * @test
 * @summary Test LocalDate.datesUntil method for normal and exceptional behavior
 * @run main LocalDateWitupTest_P6771ccb0ca93
 */

import java.time.LocalDate;
import java.util.stream.Stream;

public class LocalDateWitupTest_P6771ccb0ca93 {

    public static void main(String[] args) {
        testDatesUntilNormal();
        testDatesUntilEndBeforeStart();
    }

    static void testDatesUntilNormal() {
        LocalDate startDate = LocalDate.of(2021, 1, 1);
        LocalDate endDate = LocalDate.of(2021, 1, 5);
        Stream<LocalDate> stream = startDate.datesUntil(endDate);
        LocalDate[] expectedDates = {
            LocalDate.of(2021, 1, 1),
            LocalDate.of(2021, 1, 2),
            LocalDate.of(2021, 1, 3),
            LocalDate.of(2021, 1, 4)
        };
        LocalDate[] actualDates = stream.toArray(LocalDate[]::new);
        if (actualDates.length != expectedDates.length) {
            throw new AssertionError("Length mismatch: expected " + expectedDates.length + " but got " + actualDates.length);
        }
        for (int i = 0; i < expectedDates.length; i++) {
            if (!expectedDates[i].equals(actualDates[i])) {
                throw new AssertionError("Mismatch at index " + i + ": expected " + expectedDates[i] + " but got " + actualDates[i]);
            }
        }
    }

    static void testDatesUntilEndBeforeStart() {
        LocalDate startDate = LocalDate.of(2021, 1, 5);
        LocalDate endDate = LocalDate.of(2021, 1, 1);
        try {
            startDate.datesUntil(endDate);
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            String message = e.getMessage();
            if (message == null || !message.contains(endDate.toString())) {
                throw new AssertionError("Exception message does not contain end date: " + message);
            }
        }
    }
}
