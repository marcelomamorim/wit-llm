/*
 * @test
 * @summary Witup-generated checks for LocalDate.minusYears
 * @run main LocalDateWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        checkEquals(LocalDate.of(2017, 1, 15), LocalDate.of(2020, 1, 15).minusYears(3));
        checkEquals(LocalDate.of(2020, 1, 15), LocalDate.of(2020, 1, 15).minusYears(0));
        checkEquals(LocalDate.of(2022, 1, 15), LocalDate.of(2020, 1, 15).minusYears(-2));
        checkEquals(LocalDate.of(2019, 2, 28), LocalDate.of(2020, 2, 29).minusYears(1));
        checkEquals(LocalDate.of(2000, 2, 29), LocalDate.of(2004, 2, 29).minusYears(4));

        checkThrows(() -> LocalDate.of(2020, 1, 1).minusYears(Long.MIN_VALUE));
        checkThrows(() -> LocalDate.of(2020, 1, 1).minusYears(Long.MAX_VALUE));
        checkThrows(() -> LocalDate.of(-999999999, 1, 1).minusYears(1));
        checkThrows(() -> LocalDate.of(999999999, 12, 31).minusYears(-1));
    }

    private static void checkEquals(LocalDate expected, LocalDate actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but was " + actual);
        }
    }

    private static void checkThrows(Runnable action) {
        try {
            action.run();
        } catch (DateTimeException expected) {
            return;
        }
        throw new RuntimeException("Expected DateTimeException was not thrown");
    }
}
