/*
 * @test
 * @summary Test java.time.LocalDate.datesUntil(LocalDate) method behavior
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.util.stream.Stream;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testDatesUntilNormalRange();
        testDatesUntilSameDate();
        testDatesUntilThrowsOnEndBeforeStart();
    }

    private static void testDatesUntilNormalRange() {
        LocalDate start = LocalDate.of(2020, 1, 1);
        LocalDate end = LocalDate.of(2020, 1, 4);
        Stream<LocalDate> stream = start.datesUntil(end);
        LocalDate[] dates = stream.toArray(LocalDate[]::new);
        if (dates.length != 3) {
            throw new AssertionError("Expected 3 dates but got " + dates.length);
        }
        if (!dates[0].equals(LocalDate.of(2020, 1, 1))) {
            throw new AssertionError("First date incorrect: " + dates[0]);
        }
        if (!dates[1].equals(LocalDate.of(2020, 1, 2))) {
            throw new AssertionError("Second date incorrect: " + dates[1]);
        }
        if (!dates[2].equals(LocalDate.of(2020, 1, 3))) {
            throw new AssertionError("Third date incorrect: " + dates[2]);
        }
    }

    private static void testDatesUntilSameDate() {
        LocalDate date = LocalDate.of(2021, 5, 15);
        Stream<LocalDate> stream = date.datesUntil(date);
        LocalDate[] dates = stream.toArray(LocalDate[]::new);
        if (dates.length != 0) {
            throw new AssertionError("Expected empty stream when endExclusive equals start");
        }
    }

    private static void testDatesUntilThrowsOnEndBeforeStart() {
        LocalDate start = LocalDate.of(2022, 3, 10);
        LocalDate end = LocalDate.of(2022, 3, 5);
        try {
            start.datesUntil(end);
            throw new AssertionError("Expected IllegalArgumentException when endExclusive < start");
        } catch (IllegalArgumentException ex) {
            // Expected exception
            String msg = ex.getMessage();
            if (msg == null || !msg.contains("<")) {
                throw new AssertionError("Exception message does not contain '<': " + msg);
            }
        }
    }
}
