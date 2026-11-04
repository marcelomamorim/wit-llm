/* @test    @summary Test CollatorProviderImpl.getInstance(Locale) for various locales    @run main CollatorProviderImplWitupTest */

import java.text.Collator;
import java.text.ParseException;
import java.util.Locale;
import sun.util.locale.provider.CollatorProviderImpl;

public class CollatorProviderImplWitupTest {

    public static void main(String[] args) {
        testGetInstanceWithLocale(Locale.US);
        testGetInstanceWithLocale(Locale.FRANCE);
        testGetInstanceWithLocale(Locale.JAPAN);
        testGetInstanceWithLocale(new Locale("xx")); // unlikely supported locale
        testGetInstanceWithNull();
    }

    private static void testGetInstanceWithLocale(Locale locale) {
        try {
            Collator collator = new CollatorProviderImpl(LocaleProviderAdapter.Type.LDAP, null).getInstance(locale);
            if (collator == null) {
                throw new AssertionError("Collator is null for locale: " + locale);
            }
            // Verify that the returned Collator's locale matches or is supported
            Locale collatorLocale = collator.getLocale();
            if (!locale.equals(collatorLocale) && !collator.isSupportedLocale(locale)) {
                throw new AssertionError("Returned Collator locale mismatch for locale: " + locale);
            }
            System.out.println("Supported locale: " + locale + ", Collator class: " + collator.getClass().getName());
        } catch (NullPointerException e) {
            if (locale == null) {
                System.out.println("Correctly threw NullPointerException for null locale.");
            } else {
                throw e;
            }
        } catch (Exception e) {
            throw new AssertionError("Exception for locale: " + locale, e);
        }
    }

    private static void testGetInstanceWithNull() {
        try {
            new CollatorProviderImpl(LocaleProviderAdapter.Type.LDAP, null).getInstance(null);
            throw new AssertionError("Expected NullPointerException for null locale.");
        } catch (NullPointerException e) {
            System.out.println("Correctly caught NullPointerException for null locale.");
        }
    }
}
