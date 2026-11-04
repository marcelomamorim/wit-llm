/*
 * @test
 * @summary Witup generated tests for Scanner.match() exception path and observable match result
 * @run main ScannerWitupTest
 */

import java.io.StringReader;
import java.util.Scanner;
import java.util.regex.MatchResult;

public class ScannerWitupTest {

    public static void main(String[] args) {
        testMatchWithoutSearchThrowsIllegalStateException();
        testMatchAfterSuccessfulFindInLineReturnsResult();
        testMatchAfterFailedFindInLineThrowsIllegalStateException();
    }

    static void testMatchWithoutSearchThrowsIllegalStateException() {
        Scanner s = new Scanner(new StringReader("12 34"));
        try {
            s.match();
            throw new RuntimeException("expected IllegalStateException when no match is available");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    static void testMatchAfterSuccessfulFindInLineReturnsResult() {
        Scanner s = new Scanner(new StringReader("12 fish"));
        String found = s.findInLine("(\\d+) fish");
        if (!"12 fish".equals(found)) {
            throw new RuntimeException("unexpected findInLine result: " + found);
        }
        MatchResult result = s.match();
        if (result.groupCount() != 1) {
            throw new RuntimeException("unexpected group count: " + result.groupCount());
        }
        if (!"12".equals(result.group(1))) {
            throw new RuntimeException("unexpected group(1): " + result.group(1));
        }
    }

    static void testMatchAfterFailedFindInLineThrowsIllegalStateException() {
        Scanner s = new Scanner(new StringReader("abc"));
        String found = s.findInLine("\\d+");
        if (found != null) {
            throw new RuntimeException("expected findInLine to return null but got: " + found);
        }
        try {
            s.match();
            throw new RuntimeException("expected IllegalStateException after failed search");
        } catch (IllegalStateException e) {
            // expected
        }
    }
}
