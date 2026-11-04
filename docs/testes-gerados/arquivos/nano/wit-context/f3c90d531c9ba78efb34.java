/*
 * @test
 * @summary Tests for java.time.LocalDate.datesUntil(LocalDate)
 * @run main LocalDateWitupTest
 */

import java.time.LocalDate;
import java.util.stream.Stream;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        testDatesUntilNormal();
        testDatesUntilEndBeforeStart();
        testDatesUntilEqual();
    }

    private static void testDatesUntilNormal() {
        LocalDate startDate = LocalDate.of(2020, 1, 1);
        LocalDate endDate = LocalDate.of(2020, 1, 5);
        Stream<LocalDate> stream = startDate.datesUntil(endDate);
        Object[] dates = stream.toArray();
        assert dates.length == 4 : "Expected 4 dates, got " + dates.length;
        assert dates[0].equals(LocalDate.of(2020, 1, 1)) : "First date mismatch";
        assert dates[3].equals(LocalDate.of(2020, 1, 4)) : "Last date mismatch";
    }

    private static void testDatesUntilEndBeforeStart() {
        LocalDate startDate = LocalDate.of(2020, 1, 5);
        LocalDate endDate = LocalDate.of(2020, 1, 1);
        try {
            startDate.datesUntil(endDate);
            assert false : "Expected IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            String message = e.getMessage();
            assert message.contains("2020-01-01") : "Exception message does not contain end date";
            assert message.contains("2020-01-05") : "Exception message does not contain start date";
        }
    }

    private static void testDatesUntilEqual() {
        LocalDate date = LocalDate.of(2020, 1, 1);
        Stream<LocalDate> stream = date.datesUntil(date);
        Object[] dates = stream.toArray();
        assert dates.length == 0 : "Expected 0 dates when start == end";
    }
}
