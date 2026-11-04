/*
 * @test
 * @summary Checks Scanner.hasNextShort(int radix) for in-range, overflow, non-numeric, empty, radix and non-consuming behavior
 * @run main ScannerWitupTest
 */

import java.io.StringReader;
import java.util.Scanner;

public class ScannerWitupTest {

    private static Scanner scannerOf(String s) {
        return new Scanner(new StringReader(s));
    }

    public static void main(String[] args) {
        check(scannerOf("123").hasNextShort(10), "decimal value in short range");
        check(!scannerOf("32768").hasNextShort(10), "positive overflow must return false");
        check(scannerOf("-32768").hasNextShort(10), "Short.MIN_VALUE must be accepted");
        check(!scannerOf("abc").hasNextShort(10), "non-numeric token must return false");
        check(!scannerOf("").hasNextShort(10), "empty input must return false");
        check(scannerOf("ff").hasNextShort(16), "hex token ff must be accepted with radix 16");
        check(scannerOf("7fff").hasNextShort(16), "hex Short.MAX_VALUE must be accepted");
        check(!scannerOf("8000").hasNextShort(16), "hex overflow must return false");

        Scanner s = scannerOf("123");
        check(s.hasNextShort(10), "hasNextShort must succeed before consumption");
        check(s.nextShort(10) == 123, "hasNextShort must not consume the token");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
