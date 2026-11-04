/*
 * @test
 * @summary Checks Scanner.hasNextInt() observable behavior on the default radix
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

    static Scanner scannerFor(String s) {
        return new Scanner(new StringReader(s));
    }

    public static void main(String[] args) {
        // Valid int token
        check(scannerFor("42").hasNextInt(), "42 should be an int");
        check(scannerFor("-7").hasNextInt(), "-7 should be an int");

        // Non-int tokens
        check(!scannerFor("abc").hasNextInt(), "abc should not be an int");
        check(!scannerFor("1.5").hasNextInt(), "1.5 should not be an int");
        check(!scannerFor("").hasNextInt(), "empty input should not have an int");
        check(!scannerFor("   ").hasNextInt(), "whitespace only should not have an int");

        // Boundary values
        check(scannerFor("2147483647").hasNextInt(), "Integer.MAX_VALUE should be an int");
        check(scannerFor("-2147483648").hasNextInt(), "Integer.MIN_VALUE should be an int");
        check(!scannerFor("2147483648").hasNextInt(), "overflow above MAX_VALUE should not be an int");
        check(!scannerFor("99999999999999999999").hasNextInt(), "large overflow should not be an int");

        // hasNextInt does not consume input
        Scanner sc = scannerFor("42");
        check(sc.hasNextInt(), "first hasNextInt should be true");
        check(sc.hasNextInt(), "second hasNextInt should be true (no consumption)");
        check(sc.nextInt() == 42, "nextInt should return 42 after hasNextInt");

        // Only the next token is examined
        sc = scannerFor("42 abc");
        check(sc.hasNextInt(), "first token should be an int");
        check(sc.nextInt() == 42, "nextInt should return 42");
        check(!sc.hasNextInt(), "next token 'abc' should not be an int");
    }
}
