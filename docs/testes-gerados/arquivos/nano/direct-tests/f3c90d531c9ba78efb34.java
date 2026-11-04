/*
 * @test
 * @summary Test LocalDate.datesUntil method for normal and edge cases
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.util.stream.Stream;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testDatesUntilNormal();
        testDatesUntilEndBeforeStart();
        testDatesUntilSameDate();
    }

    static void testDatesUntilNormal() {
        LocalDate startDate = LocalDate.of(2021, 1, 1);
        LocalDate endDate = LocalDate.of(2021, 1, 5);
        Stream<LocalDate> stream = startDate.datesUntil(endDate);
        LocalDate[] expected = {
            LocalDate.of(2021, 1, 1),
            LocalDate.of(2021, 1, 2),
            LocalDate.of(2021, 1, 3),
            LocalDate.of(2021, 1, 4)
        };
        LocalDate[] result = stream.toArray(LocalDate[]::new);
        for (int i = 0; i < expected.length; i++) {
            if (!expected[i].equals(result[i])) {
                throw new AssertionError("Failed normal case at index " + i);
            }
        }
    }

    static void testDatesUntilEndBeforeStart() {
        LocalDate startDate = LocalDate.of(2021, 1, 10);
        LocalDate endDate = LocalDate.of(2021, 1, 5);
        try {
            startDate.datesUntil(endDate);
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testDatesUntilSameDate() {
        LocalDate date = LocalDate.of(2021, 1, 1);
        Stream<LocalDate> stream = date.datesUntil(date);
        if (stream.count() != 0) {
            throw new AssertionError("Expected empty stream for same start and end date");
        }
    }
}
