/*
 * @test
 * @summary Tests for Duration.dividedBy(long)
 * @run main DurationWitupTest_P1d4e5b9ee3ff
 */

import java.time.Duration;
import static java.time.Duration.*;

public class DurationWitupTest_P1d4e5b9ee3ff {
    public static void main(String[] args) {
        testDividedByZero();
        testDividedByOne();
        testDividedByPositive();
        testDividedByNegative();
    }

    static void testDividedByZero() {
        Duration dur = ofSeconds(60);
        try {
            dur.dividedBy(0);
            System.out.println("fail: expected ArithmeticException for divisor zero");
        } catch (ArithmeticException e) {
            System.out.println("pass: caught expected ArithmeticException for divisor zero");
        } catch (Throwable t) {
            System.out.println("fail: caught unexpected exception: " + t);
        }
    }

    static void testDividedByOne() {
        Duration dur = ofSeconds(60);
        Duration result = dur.dividedBy(1);
        if (result.equals(dur)) {
            System.out.println("pass: dividing by 1 returns the same duration");
        } else {
            System.out.println("fail: dividing by 1 did not return the same duration");
        }
    }

    static void testDividedByPositive() {
        Duration dur = ofSeconds(120);
        Duration result = dur.dividedBy(2);
        Duration expected = ofSeconds(60);
        if (result.equals(expected)) {
            System.out.println("pass: 120 seconds divided by 2 equals 60 seconds");
        } else {
            System.out.println("fail: 120 seconds divided by 2 did not equal 60 seconds");
        }
    }

    static void testDividedByNegative() {
        Duration dur = ofSeconds(60);
        Duration result = dur.dividedBy(-2);
        Duration expected = ofSeconds(-30);
        if (result.equals(expected)) {
            System.out.println("pass: 60 seconds divided by -2 equals -30 seconds");
        } else {
            System.out.println("fail: 60 seconds divided by -2 did not equal -30 seconds");
        }
    }
}
