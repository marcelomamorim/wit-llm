/*
 * @test
 * @summary Behavioral tests for Scanner.hasNextLong() using only public API
 * @run main ScannerWitupTest
 */
import java.io.StringReader;
import java.util.Scanner;

public class ScannerWitupTest {
    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static Scanner sc(String s) {
        return new Scanner(new StringReader(s));
    }

    public static void main(String[] args) {
        check(sc("123").hasNextLong(), "positive long token expected");
        check(sc("-42").hasNextLong(), "negative long token expected");
        check(sc("  7  ").hasNextLong(), "whitespace-surrounded token expected");
        check(!sc("abc").hasNextLong(), "non-numeric token must be rejected");
        check(!sc("").hasNextLong(), "empty input must return false");
        check(sc("9223372036854775807").hasNextLong(), "Long.MAX_VALUE expected");
        check(!sc("9223372036854775808").hasNextLong(), "overflow must return false");

        Scanner s = sc("ff");
        s.setRadix(16);
        check(s.hasNextLong(), "hex token must be accepted with radix 16");

        Scanner s2 = sc("102");
        s2.setRadix(2);
        check(!s2.hasNextLong(), "digit 2 must be rejected with radix 2");

        Scanner s3 = sc("12 x");
        check(s3.hasNextLong(), "first token must be a long");
        check(s3.nextLong() == 12L, "hasNextLong must not consume the token");
        check(!s3.hasNextLong(), "non-numeric second token must return false");

        Scanner c = sc("5");
        c.close();
        boolean threw = false;
        try {
            c.hasNextLong();
        } catch (IllegalStateException e) {
            threw = true;
        }
        check(threw, "closed scanner must throw IllegalStateException");
    }
}
