/* @test    @summary Testa o método LocalDate.datesUntil com entradas válidas e inválidas para verificar comportamento observável.    @run main LocalDateWitupTest_P868016a720da */

import java.time.LocalDate;
import java.time.Month;
import java.util.stream.Stream;

public class LocalDateWitupTest_P868016a720da {

    public static void main(String[] args) {
        testDatesUntilNormal();
        testDatesUntilEndBeforeStart();
        testDatesUntilEqual();
    }

    static void testDatesUntilNormal() {
        LocalDate startDate = LocalDate.of(2021, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.of(2021, Month.JANUARY, 5);
        Stream<LocalDate> stream = startDate.datesUntil(endDate);
        long count = stream.count();
        if (count != 4) {
            throw new AssertionError("Expected 4 dates, got " + count);
        }
    }

    static void testDatesUntilEndBeforeStart() {
        LocalDate startDate = LocalDate.of(2021, Month.JANUARY, 5);
        LocalDate endDate = LocalDate.of(2021, Month.JANUARY, 1);
        try {
            startDate.datesUntil(endDate);
            throw new AssertionError("Expected IllegalArgumentException for end before start");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testDatesUntilEqual() {
        LocalDate date = LocalDate.of(2021, Month.JANUARY, 1);
        Stream<LocalDate> stream = date.datesUntil(date);
        long count = stream.count();
        if (count != 0) {
            throw new AssertionError("Expected 0 dates for same start and end, got " + count);
        }
    }
}
