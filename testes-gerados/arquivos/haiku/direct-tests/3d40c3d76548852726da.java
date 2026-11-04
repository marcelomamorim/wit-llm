/*
 * @test
 * @summary Behavioral checks for LocalDate.minusYears, including the Long.MIN_VALUE and range-overflow paths
 * @run main LocalDateWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;

public class LocalDateWitupTest {

    public static void main(String[] args) {
        check(LocalDate.of(2020, 1, 15).minusYears(3).equals(LocalDate.of(2017, 1, 15)), "positive subtraction");
        check(LocalDate.of(2020, 1, 15).minusYears(0).equals(LocalDate.of(2020, 1, 15)), "zero subtraction");
        check(LocalDate.of(2020, 1, 15).minusYears(-2).equals(LocalDate.of(2022, 1, 15)), "negative subtraction adds years");
        check(LocalDate.of(2020, 2, 29).minusYears(1).equals(LocalDate.of(2019, 2, 28)), "leap day adjusts to Feb 28");
        check(LocalDate.of(2020, 2, 29).minusYears(4).equals(LocalDate.of(2016, 2, 29)), "leap day preserved on leap year");

        check(LocalDate.of(2020, 1, 15).minusYears(-2)
                .equals(LocalDate.of(2020, 1, 15).plusYears(2)), "minusYears(-n) equals plusYears(n)");

        boolean thrown = false;
        try {
            LocalDate.of(2020, 1, 15).minusYears(Long.MIN_VALUE);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "minusYears(Long.MIN_VALUE) must throw DateTimeException");

        thrown = false;
        try {
            LocalDate.MAX.minusYears(-1);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "minusYears past LocalDate.MAX year must throw DateTimeException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
