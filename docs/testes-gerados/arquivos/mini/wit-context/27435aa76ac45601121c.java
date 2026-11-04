/*
 * @test
 * @summary Test sun.util.locale.provider.TimeZoneNameUtility.retrieveDisplayNames for null argument checks
 * @run main TimeZoneNameUtilityWitupTest
 */

import java.util.Locale;
import java.util.Objects;
import sun.util.locale.provider.TimeZoneNameUtility;

public class TimeZoneNameUtilityWitupTest {
    public static void main(String[] args) {
        testNullIdThrowsNPE();
        testNullLocaleThrowsNPE();
        testValidArguments();
    }

    private static void testNullIdThrowsNPE() {
        try {
            TimeZoneNameUtility.retrieveDisplayNames(null, Locale.ENGLISH);
            throw new RuntimeException("Expected NullPointerException for null id");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testNullLocaleThrowsNPE() {
        try {
            TimeZoneNameUtility.retrieveDisplayNames("GMT", null);
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testValidArguments() {
        String[] names = TimeZoneNameUtility.retrieveDisplayNames("GMT", Locale.ENGLISH);
        if (names == null) {
            throw new RuntimeException("retrieveDisplayNames returned null for valid inputs");
        }
        if (names.length == 0) {
            throw new RuntimeException("retrieveDisplayNames returned empty array for valid inputs");
        }
    }
}
