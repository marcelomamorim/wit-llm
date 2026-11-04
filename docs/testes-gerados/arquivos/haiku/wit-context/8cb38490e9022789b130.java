/*
 * @test
 * @summary Behavioral checks for Locale.getISOCountries(): non-empty, 2-letter uppercase codes, defensive copy
 * @run main LocaleWitupTest
 */

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testNotNullAndNonEmpty();
        testEntriesAreTwoUppercaseLetters();
        testEntriesAreUnique();
        testKnownCountriesPresent();
        testReturnedArrayIsDefensiveCopy();
        testRepeatedCallsAreConsistent();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNotNullAndNonEmpty() {
        String[] result = Locale.getISOCountries();
        check(result != null, "result must not be null");
        check(result.length > 0, "result must not be empty");
        for (int i = 0; i < result.length; i++) {
            check(result[i] != null, "element " + i + " must not be null");
        }
    }

    static void testEntriesAreTwoUppercaseLetters() {
        String[] result = Locale.getISOCountries();
        for (int i = 0; i < result.length; i++) {
            String code = result[i];
            check(code.length() == 2, "code must have length 2: " + code);
            char c0 = code.charAt(0);
            char c1 = code.charAt(1);
            check(c0 >= 'A' && c0 <= 'Z', "first char must be A-Z: " + code);
            check(c1 >= 'A' && c1 <= 'Z', "second char must be A-Z: " + code);
        }
    }

    static void testEntriesAreUnique() {
        String[] result = Locale.getISOCountries();
        Set<String> seen = new HashSet<>();
        for (int i = 0; i < result.length; i++) {
            check(seen.add(result[i]), "duplicate country code: " + result[i]);
        }
    }

    static void testKnownCountriesPresent() {
        Set<String> codes = new HashSet<>();
        String[] result = Locale.getISOCountries();
        for (int i = 0; i < result.length; i++) {
            codes.add(result[i]);
        }
        check(codes.contains("US"), "US must be present");
        check(codes.contains("JP"), "JP must be present");
        check(codes.contains("GB"), "GB must be present");
        check(!codes.contains("USA"), "3-letter code must not be present");
    }

    static void testReturnedArrayIsDefensiveCopy() {
        String[] first = Locale.getISOCountries();
        String original = first[0];
        first[0] = "XX";
        String[] second = Locale.getISOCountries();
        check(!"XX".equals(second[0]), "modification of returned array must not affect later calls");
        check(original.equals(second[0]), "first element must remain unchanged in a new call");
    }

    static void testRepeatedCallsAreConsistent() {
        String[] a = Locale.getISOCountries();
        String[] b = Locale.getISOCountries();
        check(a != b, "each call must return a new array instance");
        check(a.length == b.length, "lengths must match across calls");
        for (int i = 0; i < a.length; i++) {
            check(a[i].equals(b[i]), "element " + i + " differs across calls");
        }
    }
}
