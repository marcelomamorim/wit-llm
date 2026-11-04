/*
 * @test
 * @summary Checks Scanner.hasNext() basic results, closed-scanner IllegalStateException and null delimiter IllegalArgumentException
 * @run main ScannerWitupTest
 */

import java.io.ByteArrayInputStream;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ScannerWitupTest {
    public static void main(String[] args) throws Exception {
        Scanner empty = new Scanner(new ByteArrayInputStream(new byte[0]));
        if (empty.hasNext()) {
            throw new AssertionError("empty input should have no next token");
        }
        empty.close();

        Scanner s = new Scanner(new ByteArrayInputStream("tok".getBytes("US-ASCII")));
        if (!s.hasNext()) {
            throw new AssertionError("expected a token");
        }
        if (!s.hasNext()) {
            throw new AssertionError("hasNext must not consume the token");
        }
        if (!"tok".equals(s.next())) {
            throw new AssertionError("token mismatch after hasNext");
        }
        if (s.hasNext()) {
            throw new AssertionError("no token expected after consuming it");
        }
        s.close();

        Scanner closed = new Scanner(new ByteArrayInputStream("a".getBytes("US-ASCII")));
        closed.close();
        try {
            closed.hasNext();
            throw new AssertionError("hasNext on closed scanner should throw");
        } catch (IllegalStateException e) {
            // expected
        }

        Scanner nullDelim = new Scanner(new ByteArrayInputStream("x".getBytes("US-ASCII")));
        nullDelim.useDelimiter((Pattern) null);
        try {
            nullDelim.hasNext();
            throw new AssertionError("hasNext with null delimiter should throw");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
