/*
 * @test
 * @summary Witup-derived checks for Scanner.hasNextBigInteger(int radix) radix validation and token matching
 * @run main ScannerWitupTest
 */

import java.io.StringReader;
import java.util.Scanner;

public class ScannerWitupTest {

    static Scanner scan(String s) {
        return new Scanner(new StringReader(s));
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void expectIAE(int radix) {
        try {
            scan("1").hasNextBigInteger(radix);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for radix " + radix);
    }

    public static void main(String[] args) {
        // Radix bounds rejected by setRadix, invoked first in hasNextBigInteger
        expectIAE(1);
        expectIAE(0);
        expectIAE(-10);
        expectIAE(37);
        expectIAE(100);

        // Boundary radixes accepted
        check(scan("101").hasNextBigInteger(2), "binary 101 should match radix 2");
        check(scan("zz").hasNextBigInteger(36), "zz should match radix 36");

        // Token matching by radix
        check(scan("ff").hasNextBigInteger(16), "ff should match radix 16");
        check(!scan("ff").hasNextBigInteger(10), "ff should not match radix 10");
        check(scan("123").hasNextBigInteger(10), "123 should match radix 10");
        check(!scan("102").hasNextBigInteger(2), "102 should not match radix 2");

        // Non-numeric and empty input
        check(!scan("xyz").hasNextBigInteger(10), "xyz should not match radix 10");
        check(!scan("").hasNextBigInteger(10), "empty input should not match");
    }
}
