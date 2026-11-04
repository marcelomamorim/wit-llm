/*
 * @test
 * @summary Test java.time.Duration.dividedBy(Duration) method
 * @run main java.time.DurationWitupTest
 */

import java.time.Duration;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public class DurationWitupTest {
    public static void main(String[] args) throws Exception {
        testDividedByBasic();
        testDividedByOne();
        testDividedBySelf();
        testDividedByFraction();
        testDividedByZeroThrows();
        testDividedByNullThrows();
    }

    private static void testDividedByBasic() {
        Duration d1 = Duration.ofSeconds(10, 0);
        Duration d2 = Duration.ofSeconds(2, 0);
        long result = d1.dividedBy(d2);
        if (result != 5L) {
            throw new AssertionError("Expected 5 but got " + result);
        }
    }

    private static void testDividedByOne() {
        Duration d1 = Duration.ofSeconds(123, 456789000);
        Duration d2 = Duration.ofSeconds(1, 0);
        long result = d1.dividedBy(d2);
        if (result != 123L) {
            throw new AssertionError("Expected 123 but got " + result);
        }
    }

    private static void testDividedBySelf() {
        Duration d1 = Duration.ofSeconds(7, 123456789);
        long result = d1.dividedBy(d1);
        if (result != 1L) {
            throw new AssertionError("Expected 1 but got " + result);
        }
    }

    private static void testDividedByFraction() {
        Duration d1 = Duration.ofSeconds(5, 500000000); // 5.5 seconds
        Duration d2 = Duration.ofSeconds(2, 0);         // 2 seconds
        long result = d1.dividedBy(d2);
        if (result != 2L) { // 5.5 / 2 = 2.75 truncated to 2
            throw new AssertionError("Expected 2 but got " + result);
        }
    }

    private static void testDividedByZeroThrows() {
        Duration d1 = Duration.ofSeconds(1, 0);
        Duration zero = Duration.ofSeconds(0, 0);
        try {
            d1.dividedBy(zero);
            throw new AssertionError("Expected ArithmeticException for division by zero");
        } catch (ArithmeticException expected) {
            // expected
        }
    }

    private static void testDividedByNullThrows() {
        Duration d1 = Duration.ofSeconds(1, 0);
        try {
            d1.dividedBy(null);
            throw new AssertionError("Expected NullPointerException for null divisor");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
