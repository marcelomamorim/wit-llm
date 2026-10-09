/* @test
   @summary Testa o método datesUntil de LocalDate para comportamentos observáveis.
   @run main LocalDateWitupTest_P25b7374eb77c
 */
import java.time.LocalDate;
import java.util.stream.Stream;
public class LocalDateWitupTest_P25b7374eb77c {
    public static void main(String[] args) {
        testDatesUntilNormal();
        testDatesUntilEndBeforeStart();
        testDatesUntilSameDate();
    }
    static void testDatesUntilNormal() {
        LocalDate start = LocalDate.of(2020, 1, 1);
        LocalDate end = LocalDate.of(2020, 1, 5);
        Stream<LocalDate> stream = start.datesUntil(end);
        Object[] dates = stream.toArray();
        if (dates.length != 4) {
            throw new AssertionError("Expected 4 dates, got " + dates.length);
        }
        if (!dates[0].equals(LocalDate.of(2020, 1, 1))) {
            throw new AssertionError("First date mismatch");
        }
        if (!dates[3].equals(LocalDate.of(2020, 1, 4))) {
            throw new AssertionError("Last date mismatch");
        }
    }
    static void testDatesUntilEndBeforeStart() {
        LocalDate start = LocalDate.of(2020, 1, 5);
        LocalDate end = LocalDate.of(2020, 1, 1);
        try {
            start.datesUntil(end);
            throw new AssertionError("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
    static void testDatesUntilSameDate() {
        LocalDate date = LocalDate.of(2020, 1, 1);
        Stream<LocalDate> stream = date.datesUntil(date);
        if (stream.count() != 0) {
            throw new AssertionError("Expected empty stream");
        }
    }
}
