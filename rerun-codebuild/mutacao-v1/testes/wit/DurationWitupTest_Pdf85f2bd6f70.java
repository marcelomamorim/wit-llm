/* @test    @summary Test Duration.dividedBy(long) for normal and exceptional behavior    @run main DurationWitupTest_Pdf85f2bd6f70 */

import java.time.Duration;

public class DurationWitupTest_Pdf85f2bd6f70 {

    public static void main(String[] args) {
        testDividedByNormal();
        testDividedByZero();
        testDividedByOne();
    }

    static void testDividedByNormal() {
        Duration duration = Duration.ofSeconds(10);
        Duration result = duration.dividedBy(2);
        long expectedSeconds = 5;
        if (result == null || result.getSeconds() != expectedSeconds) {
            throw new AssertionError("Failed: dividedBy(2) should return Duration of 5 seconds");
        }
    }

    static void testDividedByZero() {
        Duration duration = Duration.ofSeconds(10);
        try {
            duration.dividedBy(0);
            throw new AssertionError("Failed: dividedBy(0) should throw ArithmeticException");
        } catch (ArithmeticException e) {
            if (!"Cannot divide by zero".equals(e.getMessage())) {
                throw new AssertionError("Failed: Exception message mismatch");
            }
        }
    }

    static void testDividedByOne() {
        Duration duration = Duration.ofSeconds(10);
        Duration result = duration.dividedBy(1);
        if (result != duration) {
            throw new AssertionError("Failed: dividedBy(1) should return the same Duration instance");
        }
    }
}
