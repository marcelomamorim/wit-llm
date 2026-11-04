/*
 * @test
 * @summary Scanner.remove() must throw UnsupportedOperationException and leave the token stream unchanged
 * @run main ScannerWitupTest
 */

import java.io.StringReader;
import java.util.Scanner;

public class ScannerWitupTest {
    public static void main(String[] args) {
        Scanner s = new Scanner(new StringReader("alpha beta"));

        try {
            s.remove();
            throw new RuntimeException("expected UnsupportedOperationException before next()");
        } catch (UnsupportedOperationException e) {
            // expected
        }

        if (!"alpha".equals(s.next())) {
            throw new RuntimeException("token stream altered by remove()");
        }

        try {
            s.remove();
            throw new RuntimeException("expected UnsupportedOperationException after next()");
        } catch (UnsupportedOperationException e) {
            // expected
        }

        if (!"beta".equals(s.next())) {
            throw new RuntimeException("second token altered by remove()");
        }

        if (s.hasNext()) {
            throw new RuntimeException("unexpected extra token");
        }
    }
}
