/*
 * @test
 * @summary LocalDateTime.of(int,int,int,int,int,int,int) builds valid values and rejects invalid components
 * @run main LocalDateTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

public class LocalDateTimeWitupTest {

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAILED: " + msg);
        }
    }

    static void expectDateTimeException(String name, Runnable r) {
        try {
            r.run();
            failures++;
            System.err.println("FAILED (no exception): " + name);
        } catch (DateTimeException e) {
            // expected
        } catch (RuntimeException e) {
            failures++;
            System.err.println("FAILED (wrong exception " + e + "): " + name);
        }
    }

    public static void main(String[] args) {
        // Valid values: basic representation
        LocalDateTime dt = LocalDateTime.of(2020, 1, 15, 10, 30, 45, 123);
        check(dt.equals(LocalDateTime.of(2020, Month.JANUARY, 15, 10, 30, 45, 123)),
              "int month overload must equal Month overload");

        // Equivalence with LocalDate/LocalTime composition
        LocalDateTime composed = LocalDateTime.of(LocalDate.of(2020, 1, 15),
                                                  LocalTime.of(10, 30, 45, 123));
        check(dt.equals(composed), "of(int...) must equal of(LocalDate, LocalTime)");

        // Seven-argument overload with zero seconds/nanos matches five-argument overload
        check(LocalDateTime.of(2020, 1, 15, 10, 30)
                .equals(LocalDateTime.of(2020, 1, 15, 10, 30, 0, 0)),
              "five-arg overload must equal seven-arg overload with zeros");

        // Observable string form
        check(LocalDateTime.of(2020, 1, 15, 10, 30).toString().equals("2020-01-15T10:30"),
              "toString for minute precision");

        // Boundary values
        LocalDateTime min = LocalDateTime.of(2020, 12, 31, 23, 59, 59, 999_999_999);
        check(min.equals(LocalDateTime.of(2020, Month.DECEMBER, 31, 23, 59, 59, 999_999_999)),
              "max boundary of time fields");
        check(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)
                .equals(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0)),
              "min boundary of time fields");

        // Leap day accepted in leap year
        check(LocalDateTime.of(2024, 2, 29, 12, 0).toString().equals("2024-02-29T12:00"),
              "leap day 2024-02-29 must be valid");

        // Invalid components must raise DateTimeException
        expectDateTimeException("month 0", () -> LocalDateTime.of(2020, 0, 1, 0, 0));
        expectDateTimeException("month 13", () -> LocalDateTime.of(2020, 13, 1, 0, 0));
        expectDateTimeException("day 0", () -> LocalDateTime.of(2020, 1, 0, 0, 0));
        expectDateTimeException("day 32", () -> LocalDateTime.of(2020, 1, 32, 0, 0));
        expectDateTimeException("Feb 29 in non-leap year", () -> LocalDateTime.of(2023, 2, 29, 0, 0));
        expectDateTimeException("hour 24", () -> LocalDateTime.of(2020, 1, 1, 24, 0));
        expectDateTimeException("minute 60", () -> LocalDateTime.of(2020, 1, 1, 0, 60));
        expectDateTimeException("second 60", () -> LocalDateTime.of(2020, 1, 1, 0, 0, 60, 0));
        expectDateTimeException("nano 1e9", () -> LocalDateTime.of(2020, 1, 1, 0, 0, 0, 1_000_000_000));
        expectDateTimeException("negative nano", () -> LocalDateTime.of(2020, 1, 1, 0, 0, 0, -1));
        expectDateTimeException("Month overload invalid day",
                () -> LocalDateTime.of(2021, Month.APRIL, 31, 0, 0));

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
