/*
 * @test
 * @summary Tests observable behavior of LocalDate.plusYears
 * @run main LocalDateWitupTest
 */
import java.time.DateTimeException;
import java.time.LocalDate;

public class LocalDateWitupTest {
    public static void main(String[] args) {
        testZeroReturnsSameInstance();
        testSimpleAddition();
        testNegativeAddition();
        testLeapDayClampedToFebruary28();
        testLeapDayPreservedOnLeapYear();
        testCenturyNonLeapClamp();
        testUpperBoundaryThrows();
        testLowerBoundaryThrows();
    }

    static void testZeroReturnsSameInstance() {
        LocalDate d = LocalDate.of(2020, 5, 15);
        check(d.plusYears(0) == d, "plusYears(0) must return this");
    }

    static void testSimpleAddition() {
        LocalDate d = LocalDate.of(2000, 1, 1);
        check(d.plusYears(1).equals(LocalDate.of(2001, 1, 1)), "plusYears(1)");
        check(d.plusYears(25).equals(LocalDate.of(2025, 1, 1)), "plusYears(25)");
    }

    static void testNegativeAddition() {
        LocalDate d = LocalDate.of(2000, 1, 1);
        check(d.plusYears(-1).equals(LocalDate.of(1999, 1, 1)), "plusYears(-1)");
        check(d.plusYears(-1990).equals(LocalDate.of(10, 1, 1)), "plusYears(-1990)");
    }

    static void testLeapDayClampedToFebruary28() {
        LocalDate leap = LocalDate.of(2020, 2, 29);
        check(leap.plusYears(1).equals(LocalDate.of(2021, 2, 28)), "2020-02-29 plus 1 year");
        check(leap.plusYears(3).equals(LocalDate.of(2023, 2, 28)), "2020-02-29 plus 3 years");
    }

    static void testLeapDayPreservedOnLeapYear() {
        LocalDate leap = LocalDate.of(2020, 2, 29);
        check(leap.plusYears(4).equals(LocalDate.of(2024, 2, 29)), "2020-02-29 plus 4 years");
    }

    static void testCenturyNonLeapClamp() {
        LocalDate leap = LocalDate.of(2000, 2, 29);
        check(leap.plusYears(100).equals(LocalDate.of(2100, 2, 28)), "2000-02-29 plus 100 years");
    }

    static void testUpperBoundaryThrows() {
        boolean thrown = false;
        try {
            LocalDate.MAX.plusYears(1);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "MAX.plusYears(1) must throw DateTimeException");
    }

    static void testLowerBoundaryThrows() {
        boolean thrown = false;
        try {
            LocalDate.MIN.plusYears(-1);
        } catch (DateTimeException e) {
            thrown = true;
        }
        check(thrown, "MIN.plusYears(-1) must throw DateTimeException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
