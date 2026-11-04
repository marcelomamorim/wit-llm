/*
 * @test
 * @summary Test getDisplayName method of ChronoField for various locales.
 * @run main ChronoFieldWitupTest
 */

import java.time.temporal.ChronoField;
import java.util.Locale;

public class ChronoFieldWitupTest {

    public static void main(String[] args) {
        testGetDisplayNameWithDefaultLocale();
        testGetDisplayNameWithSpecificLocale();
    }

    static void testGetDisplayNameWithDefaultLocale() {
        try {
            String name = ChronoField.DAY_OF_WEEK.getDisplayName(Locale.getDefault());
            System.out.println("Default locale display name: " + name);
        } catch (Exception e) {
            System.err.println("Error in default locale test: " + e);
        }
    }

    static void testGetDisplayNameWithSpecificLocale() {
        try {
            Locale locale = new Locale("fr", "FR");
            String name = ChronoField.MONTH_OF_YEAR.getDisplayName(locale);
            System.out.println("French locale display name: " + name);
        } catch (Exception e) {
            System.err.println("Error in specific locale test: " + e);
        }
    }
}
