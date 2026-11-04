/* @test    @summary Test getDisplayName() method of HijrahEra for various styles and locales    @run main HijrahEraWitupTest */

import java.time.chrono.HijrahEra;
import java.time.format.TextStyle;
import java.util.Locale;

public class HijrahEraWitupTest {

    public static void main(String[] args) {
        testGetDisplayNameFullLocale();
        testGetDisplayNameShortLocale();
        testGetDisplayNameNarrowLocale();
        testGetDisplayNameDefaultLocale();
    }

    static void testGetDisplayNameFullLocale() {
        HijrahEra era = HijrahEra.AH;
        String result = era.getDisplayName(TextStyle.FULL, Locale.US);
        System.out.println("FULL locale: " + result);
        assert result != null && !result.isEmpty() : "Expected non-empty string for FULL style";
    }

    static void testGetDisplayNameShortLocale() {
        HijrahEra era = HijrahEra.AH;
        String result = era.getDisplayName(TextStyle.SHORT, Locale.FRANCE);
        System.out.println("SHORT locale: " + result);
        assert result != null && !result.isEmpty() : "Expected non-empty string for SHORT style";
    }

    static void testGetDisplayNameNarrowLocale() {
        HijrahEra era = HijrahEra.AH;
        String result = era.getDisplayName(TextStyle.NARROW, Locale.GERMANY);
        System.out.println("NARROW locale: " + result);
        assert result != null && !result.isEmpty() : "Expected non-empty string for NARROW style";
    }

    static void testGetDisplayNameDefaultLocale() {
        HijrahEra era = HijrahEra.AH;
        String result = era.getDisplayName(TextStyle.FULL, Locale.getDefault());
        System.out.println("Default locale: " + result);
        assert result != null && !result.isEmpty() : "Expected non-empty string for default locale";
    }
}
