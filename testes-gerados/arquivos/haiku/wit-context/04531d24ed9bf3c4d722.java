/*
 * @test
 * @summary Checks observable behavior and exception contracts of String.format(String, Object...)
 * @run main StringWitupTest
 */

import java.util.IllegalFormatConversionException;
import java.util.MissingFormatArgumentException;

public class StringWitupTest {

    public static void main(String[] args) {
        check("42-x", String.format("%d-%s", 42, "x"));
        check("abc", String.format("abc"));
        check("null", String.format("%s", (Object) null));
        expectNullPointerException();
        expectIllegalFormatConversion();
        expectMissingFormatArgument();
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected <" + expected + "> but was <" + actual + ">");
        }
    }

    static void expectNullPointerException() {
        try {
            String.format((String) null, new Object[0]);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for null format");
    }

    static void expectIllegalFormatConversion() {
        try {
            String.format("%d", "x");
        } catch (IllegalFormatConversionException e) {
            return;
        }
        throw new RuntimeException("IllegalFormatConversionException expected");
    }

    static void expectMissingFormatArgument() {
        try {
            String.format("%s");
        } catch (MissingFormatArgumentException e) {
            return;
        }
        throw new RuntimeException("MissingFormatArgumentException expected");
    }
}
