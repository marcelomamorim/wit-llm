/*
 * @test
 * @summary Witup-derived checks for IsoFields.WEEK_OF_WEEK_BASED_YEAR.getDisplayName(Locale)
 * @run main IsoFieldsWitupTest
 */

import java.time.temporal.IsoFields;
import java.util.Locale;

public class IsoFieldsWitupTest {

    public static void main(String[] args) {
        testNullLocaleThrowsNPE();
        testNonNullLocaleReturnsNonEmptyName();
        testRootLocaleReturnsNonEmptyName();
        System.out.println("PASSED");
    }

    static void testNullLocaleThrowsNPE() {
        boolean thrown = false;
        try {
            IsoFields.WEEK_OF_WEEK_BASED_YEAR.getDisplayName(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null locale");
        }
    }

    static void testNonNullLocaleReturnsNonEmptyName() {
        String name = IsoFields.WEEK_OF_WEEK_BASED_YEAR.getDisplayName(Locale.US);
        if (name == null || name.isEmpty()) {
            throw new RuntimeException("Expected non-empty display name for Locale.US");
        }
    }

    static void testRootLocaleReturnsNonEmptyName() {
        String name = IsoFields.WEEK_OF_WEEK_BASED_YEAR.getDisplayName(Locale.ROOT);
        if (name == null || name.isEmpty()) {
            throw new RuntimeException("Expected non-empty display name for Locale.ROOT");
        }
    }
}
