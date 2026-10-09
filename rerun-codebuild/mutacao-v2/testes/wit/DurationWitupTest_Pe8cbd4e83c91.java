/*
 * @test
 * @summary Test java.time.Duration.dividedBy(long) method for correct behavior and exception
 * @run main DurationWitupTest_Pe8cbd4e83c91
 */

import java.time.Duration;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class DurationWitupTest_Pe8cbd4e83c91 {
    public static void main(String[] args) {
        testDividedByZeroThrows();
        testDividedByOneReturnsSameInstance();
        testDividedByPositiveValue();
    }

    private static void testDividedByZeroThrows() {
        Duration d = Duration.ofSeconds(10);
        try {
            d.dividedBy(0);
            throw new RuntimeException("Expected ArithmeticException not thrown for divisor 0");
        } catch (ArithmeticException e) {
            if (!"Cannot divide by zero".equals(e.getMessage())) {
                throw new RuntimeException("ArithmeticException thrown but message unexpected: " + e.getMessage());
            }
        }
    }

    private static void testDividedByOneReturnsSameInstance() {
        Duration d = Duration.ofSeconds(12345, 6789);
        Duration result = d.dividedBy(1);
        if (result != d) {
            throw new RuntimeException("dividedBy(1) should return the same instance");
        }
    }

    private static void testDividedByPositiveValue() {
        Duration d = Duration.ofSeconds(10, 500_000_000); // 10.5 seconds
        long divisor = 2;
        Duration result = d.dividedBy(divisor);

        // Expected result is floor division of total seconds by divisor
        BigDecimal expectedSeconds = new BigDecimal("10.5").divide(new BigDecimal(divisor), RoundingMode.DOWN);

        // Convert result to BigDecimal seconds
        BigDecimal resultSeconds = toBigDecimalSeconds(result);

        if (resultSeconds.compareTo(expectedSeconds) != 0) {
            throw new RuntimeException("dividedBy did not produce expected result: expected " + expectedSeconds + " but got " + resultSeconds);
        }
    }

    private static BigDecimal toBigDecimalSeconds(Duration d) {
        BigDecimal seconds = BigDecimal.valueOf(d.getSeconds());
        BigDecimal nanos = BigDecimal.valueOf(d.getNano()).divide(BigDecimal.valueOf(1_000_000_000L));
        return seconds.add(nanos);
    }
}
