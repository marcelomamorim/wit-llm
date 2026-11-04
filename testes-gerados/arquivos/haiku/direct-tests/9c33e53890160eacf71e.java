/*
 * @test
 * @summary Tests LocalDateTime.of(LocalDate, LocalTime) for valid inputs and null arguments
 * @run main LocalDateTimeWitupTest
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LocalDateTimeWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testValidCombinationEqualsIntOverload() {
        LocalDate date = LocalDate.of(2020, 1, 15);
        LocalTime time = LocalTime.of(10, 30);
        LocalDateTime result = LocalDateTime.of(date, time);
        check(result != null, "result must not be null");
        check(result.equals(LocalDateTime.of(2020, 1, 15, 10, 30)),
              "of(date, time) must equal of(int...) with same components");
    }

    static void testSameInputsGiveEqualResults() {
        LocalDate date = LocalDate.of(2021, 12, 31);
        LocalTime time = LocalTime.of(23, 59, 59, 999_999_999);
        LocalDateTime a = LocalDateTime.of(date, time);
        LocalDateTime b = LocalDateTime.of(date, time);
        check(a.equals(b), "same inputs must produce equal LocalDateTime");
        check(a.hashCode() == b.hashCode(), "equal LocalDateTime must have equal hashCode");
    }

    static void testDifferentTimeProducesDifferentResult() {
        LocalDate date = LocalDate.of(2020, 6, 1);
        LocalDateTime a = LocalDateTime.of(date, LocalTime.of(10, 0));
        LocalDateTime b = LocalDateTime.of(date, LocalTime.of(11, 0));
        check(!a.equals(b), "different time must produce different LocalDateTime");
    }

    static void testDifferentDateProducesDifferentResult() {
        LocalTime time = LocalTime.of(12, 0);
        LocalDateTime a = LocalDateTime.of(LocalDate.of(2020, 6, 1), time);
        LocalDateTime b = LocalDateTime.of(LocalDate.of(2020, 6, 2), time);
        check(!a.equals(b), "different date must produce different LocalDateTime");
    }

    static void testNullDateThrowsNullPointerException() {
        boolean thrown = false;
        try {
            LocalDateTime.of((LocalDate) null, LocalTime.of(10, 0));
        } catch (NullPointerException e) {
            thrown = true;
            check("date".equals(e.getMessage()), "NPE message for null date must be 'date'");
        }
        check(thrown, "null date must throw NullPointerException");
    }

    static void testNullTimeThrowsNullPointerException() {
        boolean thrown = false;
        try {
            LocalDateTime.of(LocalDate.of(2020, 1, 1), (LocalTime) null);
        } catch (NullPointerException e) {
            thrown = true;
            check("time".equals(e.getMessage()), "NPE message for null time must be 'time'");
        }
        check(thrown, "null time must throw NullPointerException");
    }

    public static void main(String[] args) {
        testValidCombinationEqualsIntOverload();
        testSameInputsGiveEqualResults();
        testDifferentTimeProducesDifferentResult();
        testDifferentDateProducesDifferentResult();
        testNullDateThrowsNullPointerException();
        testNullTimeThrowsNullPointerException();
    }
}
