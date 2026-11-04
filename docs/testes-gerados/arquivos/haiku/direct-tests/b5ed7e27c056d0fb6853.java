/*
 * @test
 * @summary Witup-style behavioral tests for Locale.getISOCountries(IsoCountryCode)
 * @run main LocaleWitupTest
 */

import java.util.Locale;
import java.util.Set;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testNullTypeThrowsNPE();
        testAlpha2Codes();
        testAlpha3Codes();
    }

    static void testNullTypeThrowsNPE() {
        try {
            Locale.getISOCountries(null);
            throw new AssertionError("Expected NullPointerException for null type");
        } catch (NullPointerException expected) {
            // expected: Objects.requireNonNull(type)
        }
    }

    static void testAlpha2Codes() {
        Set<String> codes = Locale.getISOCountries(Locale.IsoCountryCode.PART1_ALPHA2);
        check(codes != null, "result must not be null");
        check(!codes.isEmpty(), "ALPHA2 set must not be empty");
        check(codes.contains("US"), "ALPHA2 set must contain US");
        check(codes.contains("JP"), "ALPHA2 set must contain JP");
        for (String code : codes) {
            check(code.length() == 2, "ALPHA2 code must have length 2: " + code);
            check(code.equals(code.toUpperCase(Locale.ROOT)),
                  "ALPHA2 code must be uppercase: " + code);
        }
    }

    static void testAlpha3Codes() {
        Set<String> codes = Locale.getISOCountries(Locale.IsoCountryCode.PART1_ALPHA3);
        check(codes != null, "result must not be null");
        check(!codes.isEmpty(), "ALPHA3 set must not be empty");
        check(codes.contains("USA"), "ALPHA3 set must contain USA");
        check(codes.contains("JPN"), "ALPHA3 set must contain JPN");
        for (String code : codes) {
            check(code.length() == 3, "ALPHA3 code must have length 3: " + code);
            check(code.equals(code.toUpperCase(Locale.ROOT)),
                  "ALPHA3 code must be uppercase: " + code);
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
