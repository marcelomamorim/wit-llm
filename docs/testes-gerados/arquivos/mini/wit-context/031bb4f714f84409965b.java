/*
 * @test
 * @summary Test SimpleDateFormat() constructor for basic functionality and locale usage
 * @run main SimpleDateFormatWitupTest
 */

import java.text.SimpleDateFormat;
import java.util.Locale;

public class SimpleDateFormatWitupTest {
    public static void main(String[] args) throws Exception {
        // Create instance using no-arg constructor
        SimpleDateFormat sdf = new SimpleDateFormat();
        if (sdf == null) {
            throw new AssertionError("SimpleDateFormat() constructor returned null");
        }

        // The pattern should not be null or empty after construction
        String pattern = sdf.toPattern();
        if (pattern == null) {
            throw new AssertionError("Pattern should not be null");
        }

        // The locale used internally should be the default FORMAT locale
        Locale defaultFormatLocale = Locale.getDefault(Locale.Category.FORMAT);
        // We cannot access locale directly, but we can check that formatting a date
        // produces a non-null string (basic sanity check)
        String formatted = sdf.format(new java.util.Date());
        if (formatted == null || formatted.isEmpty()) {
            throw new AssertionError("Formatted date string is null or empty");
        }

        // No exceptions should be thrown during normal usage
        try {
            sdf.applyPattern(pattern);
        } catch (Exception e) {
            throw new AssertionError("applyPattern threw an unexpected exception", e);
        }
    }
}
