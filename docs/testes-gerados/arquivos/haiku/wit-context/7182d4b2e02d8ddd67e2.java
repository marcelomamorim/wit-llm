/*
 * @test
 * @summary Witup-derived checks for Formatter.format(String, Object...) public behavior
 * @run main FormatterWitupTest
 */

import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.Locale;
import java.util.MissingFormatArgumentException;
import java.util.UnknownFormatConversionException;

public class FormatterWitupTest {

    public static void main(String[] args) {
        simpleFormatWritesOutput();
        formatReturnsSameFormatter();
        closedFormatterThrows();
        missingArgumentThrows();
        unknownConversionThrows();
    }

    static void simpleFormatWritesOutput() {
        StringBuilder sb = new StringBuilder();
        Formatter f = new Formatter(sb, Locale.US);
        f.format("%d-%s", 42, "x");
        check("42-x".equals(sb.toString()), "unexpected output");
    }

    static void formatReturnsSameFormatter() {
        StringBuilder sb = new StringBuilder();
        Formatter f = new Formatter(sb, Locale.US);
        Formatter r = f.format("%s", "a");
        check(r == f, "format must return this formatter");
    }

    static void closedFormatterThrows() {
        Formatter f = new Formatter(new StringBuilder(), Locale.US);
        f.close();
        boolean thrown = false;
        try {
            f.format("%s", "a");
        } catch (FormatterClosedException e) {
            thrown = true;
        }
        check(thrown, "expected FormatterClosedException");
    }

    static void missingArgumentThrows() {
        Formatter f = new Formatter(new StringBuilder(), Locale.US);
        boolean thrown = false;
        try {
            f.format("%d");
        } catch (MissingFormatArgumentException e) {
            thrown = true;
        }
        check(thrown, "expected MissingFormatArgumentException");
    }

    static void unknownConversionThrows() {
        Formatter f = new Formatter(new StringBuilder(), Locale.US);
        boolean thrown = false;
        try {
            f.format("%q", "a");
        } catch (UnknownFormatConversionException e) {
            thrown = true;
        }
        check(thrown, "expected UnknownFormatConversionException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
