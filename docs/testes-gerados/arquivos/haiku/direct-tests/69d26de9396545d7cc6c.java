/*
 * @test
 * @summary Tests BigDecimal.pow(int) for observable results, scale handling and invalid exponents
 * @run main BigDecimalWitupTest
 */

import java.math.BigDecimal;

public class BigDecimalWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void expectArithmetic(Runnable r, String message) {
        try {
            r.run();
        } catch (ArithmeticException e) {
            return;
        }
        throw new RuntimeException(message);
    }

    public static void main(String[] args) {
        BigDecimal r = new BigDecimal("2.50").pow(0);
        check(r.compareTo(BigDecimal.ONE) == 0 && r.scale() == 0, "pow(0) must return 1 with scale 0");

        r = new BigDecimal("1.5").pow(2);
        check(r.toString().equals("2.25"), "1.5^2 expected 2.25 but was " + r);

        r = new BigDecimal("1.50").pow(2);
        check(r.toString().equals("2.2500"), "1.50^2 expected 2.2500 but was " + r);

        r = new BigDecimal("-2").pow(3);
        check(r.toString().equals("-8"), "(-2)^3 expected -8 but was " + r);

        r = new BigDecimal("3.10").pow(1);
        check(r.toString().equals("3.10"), "3.10^1 expected 3.10 but was " + r);

        expectArithmetic(() -> new BigDecimal("2").pow(-1),
                "negative exponent must throw ArithmeticException");

        expectArithmetic(() -> new BigDecimal("2").pow(1000000000),
                "exponent above 999999999 must throw ArithmeticException");

        expectArithmetic(() -> BigDecimal.valueOf(1, 1000000000).pow(2),
                "scale overflow must throw ArithmeticException");
    }
}
