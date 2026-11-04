/*
 * @test
 * @summary Test setting the default Locale.
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testSetDefaultLocale();
    }

    public static void testSetDefaultLocale() {
        Locale originalLocale = Locale.getDefault();
        try {
            Locale newLocale = new Locale("fr", "FR");
            Locale.setDefault(newLocale);
            assert Locale.getDefault().equals(newLocale) : "Locale should be set to French (France)";
        } finally {
            // Restore original locale to avoid side effects
            Locale.setDefault(originalLocale);
        }
    }
}
