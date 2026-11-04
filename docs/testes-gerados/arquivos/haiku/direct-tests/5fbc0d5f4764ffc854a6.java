/*
 * @test
 * @summary Witup-generated tests for BigInteger.divide(BigInteger)
 * @run main BigIntegerWitupTest
 */

import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        checkDivide("7", "2", "3");
        checkDivide("-7", "2", "-3");
        checkDivide("7", "-2", "-3");
        checkDivide("-7", "-2", "3");
        checkDivide("0", "5", "0");
        checkDivide("100", "100", "1");
        checkDivide("5", "7", "0");

        // Knuth path: small operands
        checkDivide(ten(400), ten(200), ten(200));

        // Burnikel-Ziegler path: large operands (divisor mag length >= threshold)
        checkDivide(ten(2000), ten(1000), ten(1000));
        checkDivide("-" + ten(2000), ten(1000), "-" + ten(1000));
        checkDivide(ten(2000), "-" + ten(1000), "-" + ten(1000));
        checkDivide(ten(2000), ten(1999), "1" + zeros(1));

        // Division by zero must throw ArithmeticException
        boolean thrown = false;
        try {
            new BigInteger("1").divide(new BigInteger("0"));
        } catch (ArithmeticException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected ArithmeticException on divide by zero");
        }
    }

    private static void checkDivide(String a, String b, String expected) {
        BigInteger r = new BigInteger(a).divide(new BigInteger(b));
        if (!r.toString().equals(expected)) {
            throw new RuntimeException("divide(" + a + ", " + b + ") = " + r
                    + ", expected " + expected);
        }
    }

    private static String ten(int n) {
        return "1" + zeros(n);
    }

    private static String zeros(int n) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            sb.append('0');
        }
        return sb.toString();
    }
}
