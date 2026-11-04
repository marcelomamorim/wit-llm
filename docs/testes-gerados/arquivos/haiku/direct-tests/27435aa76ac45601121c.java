/*
 * @test
 * @summary Verifies null-argument handling and determinism of TimeZoneNameUtility.retrieveDisplayNames
 * @modules java.base/sun.util.locale.provider
 * @run main TimeZoneNameUtilityWitupTest
 */

import java.util.Arrays;
import java.util.Locale;

public class TimeZoneNameUtilityWitupTest {

    public static void main(String[] args) {
        testNullIdThrowsNPE();
        testNullLocaleThrowsNPE();
        testKnownIdIsDeterministic();
    }

    static void testNullIdThrowsNPE() {
        try {
            TimeZoneNameUtility.retrieveDisplayNames(null, Locale.US);
            throw new RuntimeException("expected NullPointerException for null id");
        } catch (NullPointerException expected) {
            // expected: Objects.requireNonNull(id)
        }
    }

    static void testNullLocaleThrowsNPE() {
        try {
            TimeZoneNameUtility.retrieveDisplayNames("America/New_York", null);
            throw new RuntimeException("expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
            // expected: Objects.requireNonNull(locale)
        }
    }

    static void testKnownIdIsDeterministic() {
        String[] first = TimeZoneNameUtility.retrieveDisplayNames("America/New_York", Locale.US);
        String[] second = TimeZoneNameUtility.retrieveDisplayNames("America/New_York", Locale.US);
        if (first == null) {
            throw new RuntimeException("expected non-null display names for a known zone id");
        }
        if (!Arrays.equals(first, second)) {
            throw new RuntimeException("repeated calls returned different display names");
        }
    }
}
