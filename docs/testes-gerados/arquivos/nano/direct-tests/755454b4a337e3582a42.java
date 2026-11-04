/*
 * @test
 * @summary Test getDisplayName method of JapaneseEra for various styles and locales.
 * @run main JapaneseEraWitupTest
 */

import java.time.chrono.JapaneseEra;
import java.time.format.TextStyle;
import java.util.Locale;

public class JapaneseEraWitupTest {

    public static void main(String[] args) {
        testGetDisplayNameNarrow();
        testGetDisplayNameShort();
        testGetDisplayNameFull();
        testGetDisplayNameWithLocale();
    }

    static void testGetDisplayNameNarrow() {
        JapaneseEra era = JapaneseEra.MEIJI;
        String name = era.getDisplayName(TextStyle.NARROW, Locale.ENGLISH);
        System.out.println("NARROW: " + name);
        assert name != null && !name.isEmpty() : "Narrow display name should not be null or empty";
    }

    static void testGetDisplayNameShort() {
        JapaneseEra era = JapaneseEra.MEIJI;
        String name = era.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        System.out.println("SHORT: " + name);
        assert name != null && !name.isEmpty() : "Short display name should not be null or empty";
    }

    static void testGetDisplayNameFull() {
        JapaneseEra era = JapaneseEra.MEIJI;
        String name = era.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        System.out.println("FULL: " + name);
        assert name != null && !name.isEmpty() : "Full display name should not be null or empty";
    }

    static void testGetDisplayNameWithLocale() {
        JapaneseEra era = JapaneseEra.MEIJI;
        String name = era.getDisplayName(TextStyle.FULL, Locale.JAPANESE);
        System.out.println("JAPANESE locale: " + name);
        assert name != null && !name.isEmpty() : "Display name with Japanese locale should not be null or empty";
    }
}
