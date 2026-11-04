/*
 * @test
 * @summary Observable behavior of Scanner.hasNextInt() using the default radix
 * @run main ScannerWitupTest
 */

import java.io.StringReader;
import java.util.Scanner;

public class ScannerWitupTest {
    public static void main(String[] args) {
        check(new Scanner(new StringReader("42")).hasNextInt(), "plain int token");
        check(new Scanner(new StringReader("-7 x")).hasNextInt(), "negative int token");
        check(!new Scanner(new StringReader("abc")).hasNextInt(), "non-numeric token");
        check(!new Scanner(new StringReader("")).hasNextInt(), "empty input");
        check(!new Scanner(new StringReader("2147483648")).hasNextInt(), "int overflow");

        Scanner s = new Scanner(new StringReader("42"));
        check(s.hasNextInt(), "first hasNextInt");
        check(s.hasNextInt(), "second hasNextInt must not consume");
        check(s.nextInt() == 42, "nextInt after hasNextInt");

        Scanner hex = new Scanner(new StringReader("ff"));
        hex.setRadix(16);
        check(hex.hasNextInt(), "hex token with radix 16");

        Scanner dec = new Scanner(new StringReader("ff"));
        dec.setRadix(10);
        check(!dec.hasNextInt(), "hex token with radix 10");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
