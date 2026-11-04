/*
 * @test
 * @summary Tests the public BigInteger(String) constructor for valid and invalid decimal input
 * @run main BigIntegerWitupTest
 */
import java.math.BigInteger;

public class BigIntegerWitupTest {

    public static void main(String[] args) {
        check(new BigInteger("0").equals(BigInteger.valueOf(0L)), "zero");
        check(new BigInteger("123").equals(BigInteger.valueOf(123L)), "positive value");
        check(new BigInteger("-45").equals(BigInteger.valueOf(-45L)), "negative value");
        check(new BigInteger("+7").equals(BigInteger.valueOf(7L)), "explicit plus sign");
        check(new BigInteger("007").equals(BigInteger.valueOf(7L)), "leading zeros");
        check(new BigInteger("-0").signum() == 0, "negative zero normalizes to zero");
        check(new BigInteger("123456789012345678901234567890").toString()
                .equals("123456789012345678901234567890"), "large positive value");
        check(new BigInteger("-98765432109876543210").toString()
                .equals("-98765432109876543210"), "large negative value");

        expectNumberFormatException("12a");
        expectNumberFormatException("");
        expectNumberFormatException("--1");
        expectNumberFormatException("1 2");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }

    private static void expectNumberFormatException(String input) {
        try {
            new BigInteger(input);
        } catch (NumberFormatException e) {
            return;
        }
        throw new RuntimeException("Expected NumberFormatException for input: [" + input + "]");
    }
}
