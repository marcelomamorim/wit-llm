/*
 * @test
 * @summary Verifies Scanner.skip(String pattern) returns this scanner, consumes matched input, and throws NoSuchElementException when the pattern does not match
 * @run main ScannerWitupTest
 */

import java.io.StringReader;

public class ScannerWitupTest {
    public static void main(String[] args) {
        Scanner s = new Scanner(new StringReader("fish 1"));
        Scanner ret = s.skip("fish");
        if (ret != s) {
            throw new RuntimeException("skip(String) must return this scanner");
        }
        if (s.nextInt() != 1) {
            throw new RuntimeException("expected 1 after skipping 'fish'");
        }

        Scanner u = new Scanner(new StringReader("12ab"));
        u.skip("\\d+");
        String rest = u.next();
        if (!"ab".equals(rest)) {
            throw new RuntimeException("expected 'ab' after skipping digits, got: " + rest);
        }

        Scanner t = new Scanner(new StringReader("abc"));
        boolean thrown = false;
        try {
            t.skip("\\d+");
        } catch (NoSuchElementException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NoSuchElementException for non-matching pattern");
        }
        if (!"abc".equals(t.next())) {
            throw new RuntimeException("input must remain unchanged after failed skip");
        }
    }
}
