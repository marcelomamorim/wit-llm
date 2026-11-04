/*
 * @test
 * @summary Test getDisplayName method of JapaneseEra for normal and supplemental eras and null parameters
 * @run main JapaneseEraWitupTest
 */

import java.time.chrono.JapaneseEra;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;

public class JapaneseEraWitupTest {
    public static void main(String[] args) {
        testNormalEraDisplayName();
        testSupplementalEraDisplayName();
        testNullLocaleThrowsNPEForSupplementalEra();
        testNullStyleThrowsNPEForNormalEra();
    }

    private static void testNormalEraDisplayName() {
        // Use a known normal era (e.g. MEIJI) which is not supplemental
        JapaneseEra era = JapaneseEra.MEIJI;
        // Use TextStyle.FULL and Locale.JAPAN
        String name = era.getDisplayName(TextStyle.FULL, Locale.JAPAN);
        if (name == null || name.isEmpty()) {
            throw new AssertionError("Expected non-null, non-empty display name for normal era");
        }
    }

    private static void testSupplementalEraDisplayName() {
        // Create a supplemental era with value > N_ERA_CONSTANTS - ERA_OFFSET
        // The constant N_ERA_CONSTANTS and ERA_OFFSET are not public, but we can infer
        // supplemental era values are >= 100 (from source code context)
        // Use JapaneseEra.of with a large value to get supplemental era
        int supplementalValue = 100;
        JapaneseEra supplementalEra = JapaneseEra.of(supplementalValue);

        // For supplemental era, getDisplayName returns getAbbreviation or getName depending on style
        // Test with TextStyle.NARROW returns abbreviation
        String abbrev = supplementalEra.getDisplayName(TextStyle.NARROW, Locale.ENGLISH);
        if (abbrev == null || abbrev.isEmpty()) {
            throw new AssertionError("Expected non-null, non-empty abbreviation for supplemental era");
        }

        // Test with TextStyle.FULL returns name
        String name = supplementalEra.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        if (name == null || name.isEmpty()) {
            throw new AssertionError("Expected non-null, non-empty name for supplemental era");
        }
    }

    private static void testNullLocaleThrowsNPEForSupplementalEra() {
        // Supplemental era requires non-null locale, expect NullPointerException
        int supplementalValue = 100;
        JapaneseEra supplementalEra = JapaneseEra.of(supplementalValue);
        try {
            supplementalEra.getDisplayName(TextStyle.FULL, null);
            throw new AssertionError("Expected NullPointerException for null locale on supplemental era");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testNullStyleThrowsNPEForNormalEra() {
        // For normal era, passing null style should throw NullPointerException
        JapaneseEra era = JapaneseEra.MEIJI;
        try {
            era.getDisplayName(null, Locale.JAPAN);
            throw new AssertionError("Expected NullPointerException for null style on normal era");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
