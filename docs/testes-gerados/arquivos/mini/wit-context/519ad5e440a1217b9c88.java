/*
 * @test
 * @summary Test SimpleDateFormat.applyLocalizedPattern(String) for valid and edge cases
 * @run main SimpleDateFormatWitupTest
 */

import java.text.SimpleDateFormat;
import java.text.ParseException;

public class SimpleDateFormatWitupTest {
    public static void main(String[] args) throws Exception {
        testApplyLocalizedPatternValid();
        testApplyLocalizedPatternEmptyString();
    }

    private static void testApplyLocalizedPatternValid() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat();
        // Apply a valid localized pattern
        String pattern = "dd/MM/yyyy";
        sdf.applyLocalizedPattern(pattern);
        // Format a known date and check output
        java.util.Date date = new java.util.Date(0L); // Epoch
        String formatted = sdf.format(date);
        if (formatted == null || formatted.isEmpty()) {
            throw new AssertionError("Formatted date should not be null or empty");
        }
    }

    private static void testApplyLocalizedPatternEmptyString() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        try {
            // Applying empty pattern should not throw but produce some pattern
            sdf.applyLocalizedPattern("");
            // Format current date to check no exception
            String result = sdf.format(new java.util.Date());
            if (result == null || result.isEmpty()) {
                throw new AssertionError("Formatted date with empty pattern should not be null or empty");
            }
        } catch (IllegalArgumentException iae) {
            // According to current code, no exception is thrown for empty string
            // So if thrown, fail test
            throw new AssertionError("applyLocalizedPattern threw IllegalArgumentException for empty string", iae);
        }
    }
}
