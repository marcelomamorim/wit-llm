/*
 * @test
 * @summary Tests for TimeZoneNameUtility.retrieveDisplayNames
 * @run main TimeZoneNameUtilityWitupTest
 */

import java.util.Locale;
import sun.util.locale.provider.TimeZoneNameUtility;

public class TimeZoneNameUtilityWitupTest {

    public static void main(String[] args) {
        testRetrieveDisplayNamesWithValidIdAndLocale();
        testRetrieveDisplayNamesWithNullId();
        testRetrieveDisplayNamesWithNullLocale();
        testRetrieveDisplayNamesWithUnknownId();
    }

    static void testRetrieveDisplayNamesWithValidIdAndLocale() {
        String id = "America/New_York";
        Locale locale = Locale.US;
        String[] result = TimeZoneNameUtility.retrieveDisplayNames(id, locale);
        if (result == null || result.length == 0) {
            throw new AssertionError("Expected non-empty array for valid id and locale");
        }
    }

    static void testRetrieveDisplayNamesWithNullId() {
        try {
            TimeZoneNameUtility.retrieveDisplayNames(null, Locale.US);
            throw new AssertionError("Expected NullPointerException for null id");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testRetrieveDisplayNamesWithNullLocale() {
        try {
            TimeZoneNameUtility.retrieveDisplayNames("America/New_York", null);
            throw new AssertionError("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testRetrieveDisplayNamesWithUnknownId() {
        String id = "Unknown/Zone";
        Locale locale = Locale.FRANCE;
        String[] result = TimeZoneNameUtility.retrieveDisplayNames(id, locale);
        if (result == null) {
            throw new AssertionError("Expected non-null array for unknown id");
        }
    }
}
