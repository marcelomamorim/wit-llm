/* @test    @summary Test getDisplayName with non-null style and locale    @run main MonthWitupTest */

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class MonthWitupTest {

    public static void main(String[] args) {
        testGetDisplayNameWithValidParameters();
        testGetDisplayNameWithNullStyle();
        testGetDisplayNameWithNullMonth();
    }

    static void testGetDisplayNameWithValidParameters() {
        Month month = Month.JULY;
        TextStyle style = TextStyle.FULL;
        Locale locale = Locale.US;
        String result = month.getDisplayName(style, locale);
        if (result == null || result.isEmpty()) {
            throw new AssertionError("Expected non-empty display name for JULY with FULL style and US locale, but got: " + result);
        }
    }

    static void testGetDisplayNameWithNullStyle() {
        try {
            Month month = Month.JULY;
            Locale locale = Locale.US;
            String result = month.getDisplayName(null, locale);
            if (result == null) {
                throw new AssertionError("Expected non-null result when style is null, but got null");
            }
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    static void testGetDisplayNameWithNullMonth() {
        try {
            Month month = null;
            TextStyle style = TextStyle.FULL;
            Locale locale = Locale.US;
            // This will throw NullPointerException
            String result = month.getDisplayName(style, locale);
            throw new AssertionError("Expected NullPointerException when month is null, but got: " + result);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
