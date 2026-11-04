/*
 * @test
 * @summary Basic tests for java.util.Formatter.format(Locale, String, Object...)
 * @run main FormatterWitupTest
 */

import java.util.Formatter;
import java.util.Locale;
import java.util.MissingFormatArgumentException;

public class FormatterWitupTest {
    public static void main(String[] args) throws Exception {
        testFixedStringPercentNAndPercentPercent();
        testRelativeIndexWithValidArgs();
        testRelativeIndexWithMissingArgs();
        testOrdinaryIndexWithValidArgs();
        testOrdinaryIndexWithMissingArgs();
        testExplicitIndexWithValidArgs();
        testExplicitIndexWithMissingArgs();
    }

    private static void testFixedStringPercentNAndPercentPercent() {
        Formatter f = new Formatter();
        // %n and %% are fixed strings, should not throw
        f.format(Locale.US, "%% %n");
        f.close();
    }

    private static void testRelativeIndexWithValidArgs() {
        Formatter f = new Formatter();
        // relative index uses last argument index
        f.format(Locale.US, "%s %s", "a", "b");
        f.format(Locale.US, "%s %s", "a", "b", "c");
        f.close();
    }

    private static void testRelativeIndexWithMissingArgs() {
        Formatter f = new Formatter();
        boolean thrown = false;
        try {
            // relative index with no args should throw
            f.format(Locale.US, "%s");
        } catch (MissingFormatArgumentException e) {
            thrown = true;
        }
        if (!thrown) throw new RuntimeException("Expected MissingFormatArgumentException for relative index with no args");
        f.close();
    }

    private static void testOrdinaryIndexWithValidArgs() {
        Formatter f = new Formatter();
        // ordinary index increments lasto and uses args[lasto]
        f.format(Locale.US, "%s %s", "x", "y");
        f.close();
    }

    private static void testOrdinaryIndexWithMissingArgs() {
        Formatter f = new Formatter();
        boolean thrown = false;
        try {
            // ordinary index with fewer args than format specifiers
            f.format(Locale.US, "%s %s %s", "x", "y");
        } catch (MissingFormatArgumentException e) {
            thrown = true;
        }
        if (!thrown) throw new RuntimeException("Expected MissingFormatArgumentException for ordinary index with missing args");
        f.close();
    }

    private static void testExplicitIndexWithValidArgs() {
        Formatter f = new Formatter();
        // explicit index uses args[index-1]
        f.format(Locale.US, "%2$s %1$s", "first", "second");
        f.close();
    }

    private static void testExplicitIndexWithMissingArgs() {
        Formatter f = new Formatter();
        boolean thrown = false;
        try {
            // explicit index 3 but only 2 args
            f.format(Locale.US, "%3$s", "a", "b");
        } catch (MissingFormatArgumentException e) {
            thrown = true;
        }
        if (!thrown) throw new RuntimeException("Expected MissingFormatArgumentException for explicit index with missing args");
        f.close();
    }
}
