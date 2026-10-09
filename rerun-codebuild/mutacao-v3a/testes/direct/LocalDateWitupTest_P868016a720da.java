/*
 * @test
 * @summary Test LocalDate.datesUntil for normal and edge cases
 * @run main LocalDateWitupTest_P868016a720da
 */

import java.time.LocalDate;
import java.util.stream.Stream;

public class LocalDateWitupTest_P868016a720da {

    public static void main(String[] args) {
        testDatesUntilNormal();
        testDatesUntilEmpty();
        testDatesUntilException();
    }

    static void testDatesUntilNormal() {
        LocalDate start = LocalDate.of(2020, 1, 1);
        LocalDate end = LocalDate.of(2020, 1, 5);
        Stream<LocalDate> stream = start.datesUntil(end);
        LocalDate[] expectedDates = {
            LocalDate.of(2020, 1, 1),
            LocalDate.of(2020, 1, 2),
            LocalDate.of(2020, 1, 3),
            LocalDate.of(2020, 1, 4)
        };
        int index = 0;
        for (LocalDate date : (Iterable<LocalDate>) stream::iterator) {
            assert date.equals(expectedDates[index]) : "Expected: " + expectedDates[index] + " but got: " + date;
            index++;
        }
        assert index == expectedDates.length : "Stream length mismatch";
    }

    static void testDatesUntilEmpty() {
        LocalDate start = LocalDate.of(2020, 1, 5);
        LocalDate end = LocalDate.of(2020, 1, 1);
        try {
            start.datesUntil(end);
            assert false : "Expected IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    static void testDatesUntilException() {
        LocalDate start = LocalDate.of(2020, 1, 1);
        Object notALocalDate = new Object();
        try {
            // Using reflection to invoke datesUntil with invalid argument type
            java.lang.reflect.Method method = LocalDate.class.getMethod("datesUntil", LocalDate.class);
            method.invoke(start, notALocalDate);
            assert false : "Expected IllegalArgumentException";
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            assert cause instanceof IllegalArgumentException : "Expected IllegalArgumentException, got: " + cause;
        } catch (Exception e) {
            assert false : "Unexpected exception: " + e;
        }
    }
}
