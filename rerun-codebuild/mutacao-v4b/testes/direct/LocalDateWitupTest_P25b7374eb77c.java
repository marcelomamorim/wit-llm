/* @test
   @summary Tests the datesUntil method of LocalDate for normal and exceptional behavior.
   @run main LocalDateWitupTest_P25b7374eb77c
 */
import java.time.LocalDate;
import java.util.stream.Stream;

public class LocalDateWitupTest_P25b7374eb77c {
    public static void main(String[] args) {
        testDatesUntilNormal();
        testDatesUntilEndBeforeStart();
    }

    private static void testDatesUntilNormal() {
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
        for (int i = 0; i < expectedDates.length; i++) {
            if (!expectedDates[i].equals(actualDates[i])) {
                throw new AssertionError("Mismatch at index " + i + ": expected " + expectedDates[i] + " but got " + actualDates[i]);
            }
        }
    }

    private static void testDatesUntilEndBeforeStart() {
        LocalDate startDate = LocalDate.of(2021, 1, 5);
        LocalDate endDate = LocalDate.of(2021, 1, 1);
        try {
            startDate.datesUntil(endDate);
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            String expectedMessage = endDate + " \u003c " + startDate;
            if (!e.getMessage().contains(expectedMessage)) {
                throw new AssertionError("Exception message does not contain expected text. Got: " + e.getMessage());
            }
        }
    }
}
