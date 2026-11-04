/*
 * @test
 * @summary Behavioral checks for LocalTime.withSecond(int)
 * @run main LocalTimeWitupTest
 */

import java.time.DateTimeException;
import java.time.LocalTime;

public class LocalTimeWitupTest {
    public static void main(String[] args) {
        testChangesSecondAndKeepsOtherFields();
        testSameSecondReturnsSameInstance();
        testNanoPreserved();
        testBoundaryZeroAtEndOfDay();
        testEqualityWithFactory();
        testInvalidSecondTooSmall();
        testInvalidSecondTooLarge();
    }

    static void testChangesSecondAndKeepsOtherFields() {
        LocalTime t = LocalTime.of(10, 15);
        LocalTime r = t.withSecond(30);
        check(r.getHour() == 10, "hour changed");
        check(r.getMinute() == 15, "minute changed");
        check(r.getSecond() == 30, "second not updated");
        check(r.getNano() == 0, "nano changed");
    }

    static void testSameSecondReturnsSameInstance() {
        LocalTime t = LocalTime.of(10, 15, 42);
        check(t.withSecond(42) == t, "expected same instance when second is unchanged");
    }

    static void testNanoPreserved() {
        LocalTime t = LocalTime.of(1, 2, 3, 456);
        LocalTime r = t.withSecond(9);
        check(r.getSecond() == 9, "second not updated");
        check(r.getNano() == 456, "nano not preserved");
        check(r.getMinute() == 2 && r.getHour() == 1, "hour or minute changed");
    }

    static void testBoundaryZeroAtEndOfDay() {
        LocalTime t = LocalTime.of(23, 59, 59, 999999999);
        LocalTime r = t.withSecond(0);
        check(r.equals(LocalTime.of(23, 59, 0, 999999999)), "boundary second 0 incorrect");
    }

    static void testEqualityWithFactory() {
        LocalTime r = LocalTime.of(10, 15).withSecond(30);
        check(r.equals(LocalTime.of(10, 15, 30)), "result differs from factory result");
    }

    static void testInvalidSecondTooSmall() {
        expectDateTimeException(-1);
    }

    static void testInvalidSecondTooLarge() {
        expectDateTimeException(60);
    }

    static void expectDateTimeException(int second) {
        try {
            LocalTime.of(10, 15).withSecond(second);
        } catch (DateTimeException e) {
            return;
        }
        throw new RuntimeException("expected DateTimeException for second " + second);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
