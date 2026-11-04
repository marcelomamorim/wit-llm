/*
 * @test
 * @summary Test java.time.Duration.dividedBy(Duration) method
 * @run main DurationWitupTest
 */

import java.time.Duration;
import java.util.Objects;
import java.math.BigDecimal;
import java.math.BigInteger;

public class DurationWitupTest {
    public static void main(String[] args) {
        testDividedByNullThrowsNPE();
        testDividedByBasicDivision();
        testDividedByExactDivision();
        testDividedByFractionalResult();
    }

    private static void testDividedByNullThrowsNPE() {
        Duration d = Duration.ofSeconds(10);
        try {
            d.dividedBy(null);
            throw new AssertionError("Expected NullPointerException for null divisor");
        } catch (NullPointerException npe) {
            // expected
            if (!"divisor".equals(npe.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch");
            }
        }
    }

    private static void testDividedByBasicDivision() {
        Duration dividend = Duration.ofSeconds(20);
        Duration divisor = Duration.ofSeconds(5);
        long result = dividend.dividedBy(divisor);
        if (result != 4L) {
            throw new AssertionError("Expected 4 but got " + result);
        }
    }

    private static void testDividedByExactDivision() {
        Duration dividend = Duration.ofSeconds(15, 500_000_000); // 15.5 seconds
        Duration divisor = Duration.ofSeconds(5, 500_000_000);  // 5.5 seconds
        long result = dividend.dividedBy(divisor);
        if (result != 2L) {
            throw new AssertionError("Expected 2 but got " + result);
        }
    }

    private static void testDividedByFractionalResult() {
        Duration dividend = Duration.ofSeconds(7);
        Duration divisor = Duration.ofSeconds(3);
        long result = dividend.dividedBy(divisor);
        if (result != 2L) {
            throw new AssertionError("Expected 2 (integral part) but got " + result);
        }
    }
}
