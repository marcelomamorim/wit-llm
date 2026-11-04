/*
 * @test
 * @summary Test java.time.Month.getDisplayName(TextStyle, Locale) for correctness and exceptions
 * @run main MonthWitupTest
 */

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class MonthWitupTest {
    public static void main(String[] args) {
        testGetDisplayNameBasic();
        testGetDisplayNameNullStyle();
        testGetDisplayNameNullLocale();
    }

    private static void testGetDisplayNameBasic() {
        Month m = Month.JANUARY;
        String nameFull = m.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        if (nameFull == null || nameFull.isEmpty()) {
            throw new AssertionError("Expected non-empty display name for FULL style");
        }
        String nameShort = m.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        if (nameShort == null || nameShort.isEmpty()) {
            throw new AssertionError("Expected non-empty display name for SHORT style");
        }
        String nameNarrow = m.getDisplayName(TextStyle.NARROW, Locale.ENGLISH);
        if (nameNarrow == null || nameNarrow.isEmpty()) {
            throw new AssertionError("Expected non-empty display name for NARROW style");
        }
    }

    private static void testGetDisplayNameNullStyle() {
        Month m = Month.FEBRUARY;
        try {
            m.getDisplayName(null, Locale.ENGLISH);
            throw new AssertionError("Expected NullPointerException for null TextStyle");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testGetDisplayNameNullLocale() {
        Month m = Month.MARCH;
        try {
            m.getDisplayName(TextStyle.FULL, null);
            throw new AssertionError("Expected NullPointerException for null Locale");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
