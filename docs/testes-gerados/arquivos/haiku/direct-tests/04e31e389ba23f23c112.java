/*
 * @test
 * @summary Verifies Scanner.hasNextShort() default-radix behavior: range checks, non-consuming semantics, and end-of-input handling
 * @run main ScannerWitupTest
 */

import java.io.StringReader;
import java.util.Scanner;

public class ScannerWitupTest {

    public static void main(String[] args) throws Exception {
        // Valid short tokens in default radix 10
        check(sc("123").hasNextShort(), "123 should be a short");
        check(sc("-5").hasNextShort(), "-5 should be a short");
        check(sc("0").hasNextShort(), "0 should be a short");

        // Boundary values of Short
        check(sc("32767").hasNextShort(), "Short.MAX_VALUE should be a short");
        check(sc("-32768").hasNextShort(), "Short.MIN_VALUE should be a short");
        check(!sc("32768").hasNextShort(), "32768 is out of short range");
        check(!sc("-32769").hasNextShort(), "-32769 is out of short range");

        // Non-integer and empty input
        check(!sc("abc").hasNextShort(), "abc should not be a short");
        check(!sc("").hasNextShort(), "empty input should not be a short");
        check(!sc("1.5").hasNextShort(), "1.5 should not be a short");

        // Only the next token is examined
        check(sc("12 abc").hasNextShort(), "first token 12 should be a short");
        check(!sc("abc 12").hasNextShort(), "first token abc should not be a short");

        // hasNextShort must not consume the token
        Scanner s = sc("42");
        check(s.hasNextShort(), "first call should be true");
        check(s.hasNextShort(), "second call should still be true");
        check(s.nextShort() == 42, "nextShort should return 42 after hasNextShort");
        check(!s.hasNextShort(), "no tokens should remain");

        System.out.println("PASS");
    }

    private static Scanner sc(String input) {
        return new Scanner(new StringReader(input));
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
