/*
 * @test
 * @summary Verifies Formatter.out() returns the backing Appendable and throws FormatterClosedException after the formatter is closed
 * @run main FormatterWitupTest
 */

import java.util.Formatter;
import java.util.FormatterClosedException;

public class FormatterWitupTest {

    public static void main(String[] args) throws Exception {
        testOutReturnsSuppliedAppendable();
        testOutReflectsFormattedOutput();
        testDefaultConstructorOutIsNotNull();
        testOutAfterCloseThrows();
        testOutAfterDoubleCloseThrows();
    }

    static void testOutReturnsSuppliedAppendable() {
        StringBuilder sb = new StringBuilder();
        Formatter f = new Formatter(sb);
        if (f.out() != sb) {
            throw new RuntimeException("out() must return the supplied Appendable");
        }
    }

    static void testOutReflectsFormattedOutput() {
        StringBuilder sb = new StringBuilder();
        Formatter f = new Formatter(sb);
        f.format("%d", 42);
        if (!"42".equals(f.out().toString())) {
            throw new RuntimeException("unexpected output: " + f.out());
        }
    }

    static void testDefaultConstructorOutIsNotNull() {
        Formatter f = new Formatter();
        if (f.out() == null) {
            throw new RuntimeException("out() returned null for default formatter");
        }
    }

    static void testOutAfterCloseThrows() throws Exception {
        Formatter f = new Formatter(new StringBuilder());
        f.close();
        try {
            f.out();
        } catch (FormatterClosedException e) {
            return;
        }
        throw new RuntimeException("expected FormatterClosedException after close()");
    }

    static void testOutAfterDoubleCloseThrows() throws Exception {
        Formatter f = new Formatter(new StringBuilder());
        f.close();
        f.close();
        try {
            f.out();
        } catch (FormatterClosedException e) {
            return;
        }
        throw new RuntimeException("expected FormatterClosedException after repeated close()");
    }
}
