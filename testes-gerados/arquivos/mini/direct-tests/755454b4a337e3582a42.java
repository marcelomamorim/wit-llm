/*
 * @test
 * @summary Test JapaneseEra.getDisplayName(TextStyle, Locale) behavior
 * @run main JapaneseEraWitupTest
 */

import java.time.chrono.JapaneseEra;
import java.time.chrono.JapaneseChronology;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;

public class JapaneseEraWitupTest {
    public static void main(String[] args) {
        testGetDisplayNameStandardEra();
        testGetDisplayNameSupplementalEraNarrowStyle();
        testGetDisplayNameSupplementalEraFullStyle();
        testGetDisplayNameSupplementalEraNullLocale();
    }

    private static void testGetDisplayNameStandardEra() {
        // MEIJI is a standard era (value < N_ERA_CONSTANTS - ERA_OFFSET)
        JapaneseEra era = JapaneseEra.MEIJI;
        // Use FULL style and Locale.JAPAN
        String name = era.getDisplayName(TextStyle.FULL, Locale.JAPAN);
        // The returned name should not be null or empty
        if (name == null || name.isEmpty()) {
            throw new AssertionError("getDisplayName returned null or empty for standard era");
        }
        // The name should contain some Japanese characters (basic check)
        if (!name.matches(".*[\u3040-\u30FF\u4E00-\u9FAF].*")) {
            throw new AssertionError("getDisplayName for standard era does not contain Japanese characters: " + name);
        }
    }

    private static void testGetDisplayNameSupplementalEraNarrowStyle() {
        // Create a supplemental era with value > N_ERA_CONSTANTS - ERA_OFFSET
        // The constant N_ERA_CONSTANTS and ERA_OFFSET are package-private, so we use reflection to get a supplemental era
        // But reflection is disallowed, so instead we use JapaneseEra.of with a large value
        int supplementalValue = 1000; // large value to ensure supplemental
        JapaneseEra era = JapaneseEra.of(supplementalValue);

        // Narrow style should return abbreviation
        String abbrev = era.getDisplayName(TextStyle.NARROW, Locale.ENGLISH);
        if (abbrev == null || abbrev.isEmpty()) {
            throw new AssertionError("getDisplayName returned null or empty abbreviation for supplemental era");
        }
    }

    private static void testGetDisplayNameSupplementalEraFullStyle() {
        int supplementalValue = 1000;
        JapaneseEra era = JapaneseEra.of(supplementalValue);

        // Full style (not narrow) should return name
        String name = era.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        if (name == null || name.isEmpty()) {
            throw new AssertionError("getDisplayName returned null or empty name for supplemental era with full style");
        }
    }

    private static void testGetDisplayNameSupplementalEraNullLocale() {
        int supplementalValue = 1000;
        JapaneseEra era = JapaneseEra.of(supplementalValue);

        boolean thrown = false;
        try {
            era.getDisplayName(TextStyle.FULL, null);
        } catch (NullPointerException ex) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("getDisplayName did not throw NullPointerException when locale is null for supplemental era");
        }
    }
}
