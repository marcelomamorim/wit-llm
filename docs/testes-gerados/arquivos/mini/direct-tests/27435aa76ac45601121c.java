/*
 * @test
 * @summary Test TimeZoneNameUtility.retrieveDisplayNames method
 * @run main TimeZoneNameUtilityWitupTest
 */

import java.util.Locale;
import java.util.Objects;

public class TimeZoneNameUtilityWitupTest {

    public static void main(String[] args) {
        testRetrieveDisplayNamesWithValidInputs();
        testRetrieveDisplayNamesWithNullId();
        testRetrieveDisplayNamesWithNullLocale();
    }

    private static void testRetrieveDisplayNamesWithValidInputs() {
        // Use a known time zone ID and locale
        String id = "GMT";
        Locale locale = Locale.ENGLISH;

        String[] names = sun.util.locale.provider.TimeZoneNameUtility.retrieveDisplayNames(id, locale);

        if (names == null) {
            throw new AssertionError("retrieveDisplayNames returned null for valid inputs");
        }
        if (names.length == 0) {
            throw new AssertionError("retrieveDisplayNames returned empty array for valid inputs");
        }
        for (String name : names) {
            if (name == null) {
                throw new AssertionError("retrieveDisplayNames returned array containing null element");
            }
        }
    }

    private static void testRetrieveDisplayNamesWithNullId() {
        try {
            sun.util.locale.provider.TimeZoneNameUtility.retrieveDisplayNames(null, Locale.ENGLISH);
            throw new AssertionError("Expected NullPointerException for null id");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testRetrieveDisplayNamesWithNullLocale() {
        try {
            sun.util.locale.provider.TimeZoneNameUtility.retrieveDisplayNames("GMT", null);
            throw new AssertionError("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
            // expected
        }
    }

}
