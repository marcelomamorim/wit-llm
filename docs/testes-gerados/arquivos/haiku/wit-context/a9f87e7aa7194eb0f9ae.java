/*
 * @test
 * @summary Verify Scanner.next(String pattern) returns matching tokens, rejects non-matching tokens, reports exhausted input, and throws IllegalStateException when closed
 * @run main ScannerWitupTest
 */

import java.io.StringReader;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        Scanner s = new Scanner(new StringReader("abc 123"));
        String first = s.next("[a-z]+");
        if (!"abc".equals(first)) {
            throw new RuntimeException("expected abc but was " + first);
        }

        boolean mismatch = false;
        try {
            s.next("[a-z]+");
        } catch (InputMismatchException e) {
            mismatch = true;
        }
        if (!mismatch) {
            throw new RuntimeException("expected InputMismatchException for non-matching token");
        }

        String second = s.next("[0-9]+");
        if (!"123".equals(second)) {
            throw new RuntimeException("token should not be consumed on mismatch, got " + second);
        }

        Scanner exhausted = new Scanner(new StringReader(""));
        boolean noSuch = false;
        try {
            exhausted.next("[0-9]+");
        } catch (NoSuchElementException e) {
            noSuch = true;
        }
        if (!noSuch) {
            throw new RuntimeException("expected NoSuchElementException on exhausted input");
        }

        Scanner closed = new Scanner(new StringReader("abc"));
        closed.close();
        boolean illegal = false;
        try {
            closed.next("[a-z]+");
        } catch (IllegalStateException e) {
            illegal = true;
        }
        if (!illegal) {
            throw new RuntimeException("expected IllegalStateException on closed scanner");
        }
    }
}
