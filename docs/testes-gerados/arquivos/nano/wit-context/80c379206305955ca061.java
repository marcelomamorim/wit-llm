/*
 * @test
 * @summary Test for Locale.getDisplayCountry(Locale inLocale)
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testGetDisplayCountryWithNull();
        testGetDisplayCountryWithValidLocale();
    }

    public static void testGetDisplayCountryWithNull() {
        Locale locale = new Locale("en", "US");
        try {
            locale.getDisplayCountry(null);
            System.out.println("PASS: getDisplayCountry(null) did not throw");
        } catch (NullPointerException e) {
            System.out.println("PASS: getDisplayCountry(null) threw NullPointerException");
        } catch (Exception e) {
            System.out.println("FAIL: getDisplayCountry(null) threw unexpected exception: " + e);
        }
    }

    public static void testGetDisplayCountryWithValidLocale() {
        Locale locale = new Locale("en", "US");
        Locale inLocale = new Locale("fr", "FR");
        String displayCountry = locale.getDisplayCountry(inLocale);
        if (displayCountry != null && !displayCountry.isEmpty()) {
            System.out.println("PASS: getDisplayCountry with valid locale returned: " + displayCountry);
        } else {
            System.out.println("FAIL: getDisplayCountry with valid locale returned null or empty");
        }
    }
}
