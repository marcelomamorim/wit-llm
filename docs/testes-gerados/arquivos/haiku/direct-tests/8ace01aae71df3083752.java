/*
 * @test
 * @summary Verifies observable behavior of Locale.getISOLanguages: non-null, two-letter codes, fresh copy per call
 * @run main LocaleWitupTest
 */

public class LocaleWitupTest {
    public static void main(String[] args) {
        String[] langs = Locale.getISOLanguages();
        check(langs != null, "result must not be null");
        check(langs.length > 0, "result must not be empty");

        boolean hasEn = false;
        boolean hasFr = false;
        for (String l : langs) {
            check(l != null, "null element returned");
            check(l.length() == 2, "expected two-letter code but got: " + l);
            if (l.equals("en")) {
                hasEn = true;
            }
            if (l.equals("fr")) {
                hasFr = true;
            }
        }
        check(hasEn, "expected \"en\" in ISO languages");
        check(hasFr, "expected \"fr\" in ISO languages");

        String first = langs[0];
        langs[0] = "zz";
        String[] again = Locale.getISOLanguages();
        check(again != langs, "must return a fresh array on each call");
        check(again.length == langs.length, "length must be stable across calls");
        check(first.equals(again[0]), "mutation of returned array leaked into later calls");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
