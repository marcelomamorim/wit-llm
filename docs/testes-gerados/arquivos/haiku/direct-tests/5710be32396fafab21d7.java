/*
 * @test
 * @summary Verifies observable behavior of Locale.setDefault(Locale) on the default locale and on the per-category defaults.
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Locale original = Locale.getDefault();
        try {
            Locale custom = new Locale("fr", "CA", "varX");
            Locale.setDefault(custom);
            check(custom.equals(Locale.getDefault()),
                  "getDefault() should return the locale set by setDefault(Locale)");
            check("fr".equals(Locale.getDefault().getLanguage()),
                  "language should be fr after setDefault");
            check("CA".equals(Locale.getDefault().getCountry()),
                  "country should be CA after setDefault");

            Locale second = new Locale("de");
            Locale.setDefault(second);
            check(second.equals(Locale.getDefault()),
                  "second setDefault(Locale) should replace the previous default");
            check(!custom.equals(Locale.getDefault()),
                  "previous default should no longer be returned");

            Locale formatLocale = new Locale("ja", "JP");
            Locale.setDefault(Locale.Category.FORMAT, formatLocale);
            check(second.equals(Locale.getDefault()),
                  "setDefault(Category.FORMAT, ...) should not change getDefault()");

            Locale displayLocale = new Locale("it", "IT");
            Locale.setDefault(Locale.Category.DISPLAY, displayLocale);
            check(second.equals(Locale.getDefault()),
                  "setDefault(Category.DISPLAY, ...) should not change getDefault()");

            Locale finalLocale = new Locale("en", "GB");
            Locale.setDefault(finalLocale);
            check(finalLocale.equals(Locale.getDefault()),
                  "setDefault(Locale) should override earlier category-specific settings");
        } finally {
            Locale.setDefault(original);
        }

        check(original.equals(Locale.getDefault()),
              "original default should be restored");
    }
}
