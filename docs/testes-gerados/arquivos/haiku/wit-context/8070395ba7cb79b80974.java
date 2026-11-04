/*
 * @test
 * @summary Witup-generated checks for Scanner.findWithinHorizon(String, int)
 * @run main ScannerWitupTest
 */

import java.io.StringReader;
import java.util.Scanner;

public class ScannerWitupTest {

    public static void main(String[] args) throws Exception {
        testMatchReturnedWithUnboundedHorizon();
        testNoMatchWithinHorizonReturnsNull();
        testZeroHorizonIsUnbounded();
        testClosedScannerThrowsIllegalStateException();
    }

    static void testMatchReturnedWithUnboundedHorizon() {
        Scanner s = new Scanner(new StringReader("abc 123 def"));
        String r = s.findWithinHorizon("\\d+", 0);
        check("123".equals(r), "expected 123 but was " + r);
    }

    static void testNoMatchWithinHorizonReturnsNull() {
        Scanner s = new Scanner(new StringReader("abc 123"));
        String r = s.findWithinHorizon("\\d+", 2);
        check(r == null, "expected null but was " + r);
    }

    static void testZeroHorizonIsUnbounded() {
        Scanner s = new Scanner(new StringReader("aaaaaaaaaa42"));
        String r = s.findWithinHorizon("\\d+", 0);
        check("42".equals(r), "expected 42 but was " + r);
    }

    static void testClosedScannerThrowsIllegalStateException() {
        Scanner s = new Scanner(new StringReader("x"));
        s.close();
        try {
            s.findWithinHorizon("x", 0);
            throw new RuntimeException("expected IllegalStateException");
        } catch (IllegalStateException expected) {
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
