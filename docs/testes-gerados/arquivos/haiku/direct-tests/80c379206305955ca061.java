/*
 * @test
 * @summary Checks observable behavior of Locale.getDisplayCountry(Locale) for regular, empty and unknown regions
 * @run main LocaleWitupTest
 */

public class LocaleWitupTest {

    public static void main(String[] args) throws Exception {
        testUnitedStatesInEnglish();
        testGermanyInEnglish();
        testEmptyCountryReturnsEmptyString();
        testRootLocaleReturnsEmptyString();
        testUnknownRegionFallsBackToCode();
        testResultIsStableAcrossCalls();
    }

    static void check(String expected, String actual, String what) {
        if (!expected.equals(actual)) {
            throw new RuntimeException(what + ": expected <" + expected + "> but was <" + actual + ">");
        }
    }

    static void testUnitedStatesInEnglish() {
        check("United States", Locale.US.getDisplayCountry(Locale.US), "US in US");
    }

    static void testGermanyInEnglish() {
        check("Germany", Locale.GERMANY.getDisplayCountry(Locale.ENGLISH), "GERMANY in ENGLISH");
    }

    static void testEmptyCountryReturnsEmptyString() {
        Locale noCountry = new Locale("en");
        check("", noCountry.getDisplayCountry(Locale.US), "no country");
    }

    static void testRootLocaleReturnsEmptyString() {
        check("", Locale.ROOT.getDisplayCountry(Locale.US), "ROOT country");
    }

    static void testUnknownRegionFallsBackToCode() {
        Locale unknown = new Locale("en", "ZZ");
        check("ZZ", unknown.getDisplayCountry(Locale.US), "unknown region");
    }

    static void testResultIsStableAcrossCalls() {
        String first = Locale.US.getDisplayCountry(Locale.US);
        String second = Locale.US.getDisplayCountry(Locale.US);
        check(first, second, "repeated call");
    }
}
