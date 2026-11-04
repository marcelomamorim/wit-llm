/* @test    @summary Test getDisplayName() method of ThaiBuddhistEra for different styles and locales    @run main ThaiBuddhistEraWitupTest */

import java.time.chrono.ThaiBuddhistEra;
import java.time.format.TextStyle;
import java.util.Locale;

public class ThaiBuddhistEraWitupTest {

    public static void main(String[] args) {
        testGetDisplayName(ThaiBuddhistEra.BE, TextStyle.FULL, Locale.ENGLISH, "Buddhist Era");
        testGetDisplayName(ThaiBuddhistEra.BE, TextStyle.SHORT, Locale.ENGLISH, "Buddhist");
        testGetDisplayName(ThaiBuddhistEra.BE, TextStyle.NARROW, Locale.ENGLISH, "B");
        testGetDisplayName(ThaiBuddhistEra.BE, TextStyle.FULL, Locale.JAPANESE, "仏教暦");
        testGetDisplayName(ThaiBuddhistEra.BE, TextStyle.SHORT, Locale.JAPANESE, "仏教");
        testGetDisplayName(ThaiBuddhistEra.BE, TextStyle.NARROW, Locale.JAPANESE, "仏");
        testGetDisplayName(ThaiBuddhistEra.BE, TextStyle.FULL, Locale.FRANCE, "Buddhist Era");
        testGetDisplayName(ThaiBuddhistEra.BE, TextStyle.SHORT, Locale.FRANCE, "Buddhist");
        testGetDisplayName(ThaiBuddhistEra.BE, TextStyle.NARROW, Locale.FRANCE, "B");
        // Test for BE (current era)
        testGetDisplayName(ThaiBuddhistEra.BE, TextStyle.FULL, Locale.ENGLISH, "Buddhist Era");
        // Test for BE (current era) with different locale
        testGetDisplayName(ThaiBuddhistEra.BE, TextStyle.FULL, new Locale("th"), "พุทธศักราช");
    }

    private static void testGetDisplayName(ThaiBuddhistEra era, TextStyle style, Locale locale, String expected) {
        String result = era.getDisplayName(style, locale);
        if (!result.equals(expected)) {
            System.err.printf("Test failed for era=%s, style=%s, locale=%s. Expected: '%s', Got: '%s'%n",
                era, style, locale, expected, result);
        } else {
            System.out.printf("Test passed for era=%s, style=%s, locale=%s. Result: '%s'%n",
                era, style, locale, result);
        }
    }
}
