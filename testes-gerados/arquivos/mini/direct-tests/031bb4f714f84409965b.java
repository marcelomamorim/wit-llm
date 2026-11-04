/*
 * @test
 * @summary Test default constructor of SimpleDateFormat for basic functionality
 * @run main SimpleDateFormatWitupTest
 */

import java.text.SimpleDateFormat;
import java.util.Locale;

public class SimpleDateFormatWitupTest {
    public static void main(String[] args) throws Exception {
        // Create instance using default constructor
        SimpleDateFormat sdf = new SimpleDateFormat();
        // Check that pattern is not null and not empty
        String pattern = sdf.toPattern();
        if (pattern == null) {
            throw new RuntimeException("Pattern should not be null");
        }
        if (pattern.isEmpty()) {
            throw new RuntimeException("Pattern should not be empty");
        }
        // Check that locale is default FORMAT locale
        Locale defaultFormatLocale = Locale.getDefault(Locale.Category.FORMAT);
        Locale sdfLocale = sdf.getDateFormatSymbols().getLocale();
        if (!defaultFormatLocale.equals(sdfLocale)) {
            throw new RuntimeException("Locale of SimpleDateFormat does not match default FORMAT locale");
        }
        // Format current date and parse back
        String formatted = sdf.format(new java.util.Date());
        java.util.Date parsed = sdf.parse(formatted);
        if (parsed == null) {
            throw new RuntimeException("Parsed date should not be null");
        }
    }
}
