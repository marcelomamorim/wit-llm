/* @test    @summary Test getDisplayName() method of MinguoEra for different styles and locales    @run main MinguoEraWitupTest */

import java.time.chrono.MinguoEra;
import java.time.format.TextStyle;
import java.util.Locale;

public class MinguoEraWitupTest {

    public static void main(String[] args) {
        testGetDisplayNameForROCLocale();
        testGetDisplayNameForBeforeROCLocale();
        testGetDisplayNameWithDifferentStyles();
    }

    static void testGetDisplayNameForROCLocale() {
        MinguoEra era = MinguoEra.ROC;
        String displayName = era.getDisplayName(TextStyle.FULL, Locale.US);
        String expected = "Republic of China";
        if (!displayName.equals(expected)) {
            throw new AssertionError("Expected: " + expected + ", but got: " + displayName);
        }
    }

    static void testGetDisplayNameForBeforeROCLocale() {
        MinguoEra era = MinguoEra.BEFORE_ROC;
        String displayName = era.getDisplayName(TextStyle.SHORT, Locale.FRANCE);
        String expected = "Before ROC"; // Expected value depends on implementation, assuming default
        if (!displayName.equals(expected)) {
            throw new AssertionError("Expected: " + expected + ", but got: " + displayName);
        }
    }

    static void testGetDisplayNameWithDifferentStyles() {
        MinguoEra era = MinguoEra.ROC;
        String fullName = era.getDisplayName(TextStyle.FULL, Locale.ITALY);
        String shortName = era.getDisplayName(TextStyle.SHORT, Locale.ITALY);
        if (fullName == null || shortName == null) {
            throw new AssertionError("Display names should not be null");
        }
        if (fullName.equals(shortName)) {
            throw new AssertionError("Full and short display names should differ");
        }
    }
}
