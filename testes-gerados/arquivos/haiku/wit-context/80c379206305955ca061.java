/*
 * @test
 * @summary Witup-derived tests for Locale.getDisplayCountry(Locale)
 * @run main LocaleWitupTest
 */
import java.util.Locale;

public class LocaleWitupTest {
    public static void main(String[] args) {
        Locale us = new Locale("en", "US");
        check("United States", us.getDisplayCountry(Locale.ENGLISH));
        check("", new Locale("en").getDisplayCountry(Locale.ENGLISH));
        try {
            us.getDisplayCountry(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    private static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected [" + expected + "] but got [" + actual + "]");
        }
    }
}
