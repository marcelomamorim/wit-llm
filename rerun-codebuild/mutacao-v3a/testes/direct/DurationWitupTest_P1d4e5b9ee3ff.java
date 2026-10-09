/*
 * @test
 * @summary Test the dividedBy(long) method of Duration for normal and edge cases
 * @run main DurationWitupTest_P1d4e5b9ee3ff
 */

import java.time.Duration;

public class DurationWitupTest_P1d4e5b9ee3ff {

    public static void main(String[] args) {
        testDividedByNormal();
        testDividedByZero();
        testDividedByOne();
        testDividedByNegative();
        testDividedByLargeNumber();
    }

    static void testDividedByNormal() {
        Duration dur = Duration.ofSeconds(10);
        Duration result = dur.dividedBy(2);
        long seconds = result.getSeconds();
        assert seconds == 5 : "Expected 5 seconds, but got " + seconds;
    }

    static void testDividedByZero() {
        Duration dur = Duration.ofSeconds(10);
        try {
            dur.dividedBy(0);
            assert false : "Expected ArithmeticException was not thrown";
        } catch (ArithmeticException e) {
            assert e.getMessage().contains("divide") || e.getMessage().contains("zero") : "Unexpected exception message: " + e.getMessage();
        }
    }

    static void testDividedByOne() {
        Duration dur = Duration.ofSeconds(15);
        Duration result = dur.dividedBy(1);
        long seconds = result.getSeconds();
        assert seconds == 15 : "Expected 15 seconds, but got " + seconds;
    }

    static void testDividedByNegative() {
        Duration dur = Duration.ofSeconds(20);
        Duration result = dur.dividedBy(-2);
        long seconds = result.getSeconds();
        assert seconds == -10 : "Expected -10 seconds, but got " + seconds;
    }

    static void testDividedByLargeNumber() {
        Duration dur = Duration.ofSeconds(100);
        Duration result = dur.dividedBy(3);
        long seconds = result.getSeconds();
        long expected = 33; // floor division
        assert seconds == expected : "Expected " + expected + " seconds, but got " + seconds;
    }
}
