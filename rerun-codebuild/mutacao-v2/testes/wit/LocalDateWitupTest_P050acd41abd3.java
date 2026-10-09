/*
 * @test
 * @summary Test java.time.LocalDate.datesUntil(LocalDate) for normal and exceptional cases
 * @run main LocalDateWitupTest_P050acd41abd3
 */

import java.time.LocalDate;
import java.util.stream.Stream;

public class LocalDateWitupTest_P050acd41abd3 {
    public static void main(String[] args) {
        testDatesUntilNormal();
        testDatesUntilThrowsOnEndBeforeStart();
    }

    private static void testDatesUntilNormal() {
        LocalDate start = LocalDate.of(2020, 1, 1);
        LocalDate end = LocalDate.of(2020, 1, 4);
        Stream<LocalDate> stream = start.datesUntil(end);
        LocalDate[] dates = stream.toArray(LocalDate[]::new);
        if (dates.length != 3) {
            throw new AssertionError("Expected 3 dates but got " + dates.length);
        }
        if (!dates[0].equals(LocalDate.of(2020, 1, 1))) {
            throw new AssertionError("First date mismatch: " + dates[0]);
        }
        if (!dates[1].equals(LocalDate.of(2020, 1, 2))) {
            throw new AssertionError("Second date mismatch: " + dates[1]);
        }
        if (!dates[2].equals(LocalDate.of(2020, 1, 3))) {
            throw new AssertionError("Third date mismatch: " + dates[2]);
        }
    }

    private static void testDatesUntilThrowsOnEndBeforeStart() {
        LocalDate start = LocalDate.of(2020, 1, 5);
        LocalDate end = LocalDate.of(2020, 1, 1);
        try {
            start.datesUntil(end);
            throw new AssertionError("Expected IllegalArgumentException when endExclusive < start");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }
}
