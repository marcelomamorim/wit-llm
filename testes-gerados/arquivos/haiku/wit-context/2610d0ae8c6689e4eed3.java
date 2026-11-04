/*
 * @test
 * @summary White-box exception path test for BigInteger(String val): empty input must throw NumberFormatException; valid decimal strings must be parsed.
 * @run main BigIntegerWitupTest
 */
import java.math.BigInteger;

public class BigIntegerWitupTest {
    public static void main(String[] args) {
        check(new BigInteger("123").equals(BigInteger.valueOf(123L)), "123");
        check(new BigInteger("-42").equals(BigInteger.valueOf(-42L)), "-42");
        check(new BigInteger("+7").equals(BigInteger.valueOf(7L)), "+7");
        check(new BigInteger("0").signum() == 0, "0 signum");
        expectNumberFormatException("");
        expectNumberFormatException("-");
        expectNumberFormatException("12a");
    }

    static void expectNumberFormatException(String s) {
        try {
            new BigInteger(s);
        } catch (NumberFormatException e) {
            return;
        }
        throw new RuntimeException("Expected NumberFormatException for input: \"" + s + "\"");
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + msg);
        }
    }
}
