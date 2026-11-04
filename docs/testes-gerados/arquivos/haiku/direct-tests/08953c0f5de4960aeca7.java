/*
 * @test
 * @summary Verifies Formatter.out() returns the target Appendable and throws IllegalStateException after close()
 * @run main FormatterWitupTest
 */

import java.util.Formatter;

public class FormatterWitupTest {
    public static void main(String[] args) throws Exception {
        StringBuilder sb = new StringBuilder();
        Formatter f = new Formatter(sb);
        if (f.out() != sb) {
            throw new AssertionError("out() should return the target appendable");
        }
        f.format("%d", 42);
        if (!f.out().toString().equals("42")) {
            throw new AssertionError("out() output mismatch: " + f.out());
        }
        if (!sb.toString().equals("42")) {
            throw new AssertionError("target builder mismatch: " + sb);
        }

        Formatter d = new Formatter();
        if (d.out() == null) {
            throw new AssertionError("default out() must not be null");
        }
        d.format("%s", "abc");
        if (!d.out().toString().equals("abc")) {
            throw new AssertionError("default formatter output mismatch: " + d.out());
        }

        Formatter c = new Formatter(new StringBuilder());
        c.close();
        boolean threw = false;
        try {
            c.out();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("out() after close() must throw IllegalStateException");
        }
    }
}
