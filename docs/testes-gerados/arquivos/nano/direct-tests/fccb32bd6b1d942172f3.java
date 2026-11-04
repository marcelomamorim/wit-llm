/*
 * @test
 * @summary Test getUnicodeLocaleType method of java.util.Locale
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testLocaleWithUnicodeExtension();
        testLocaleWithoutExtensions();
        testLocaleWithInvalidKey();
    }

    private static void testLocaleWithUnicodeExtension() {
        Locale locale = new Locale.Builder()
            .setLanguage("en")
            .setRegion("US")
            .setUnicodeLocaleExtension("ca", "buddhist")
            .build();
        String result = locale.getUnicodeLocaleType("ca");
        if ("buddhist".equals(result)) {
            System.out.println("PASS: Unicode locale type for 'ca' is 'buddhist'");
        } else {
            System.out.println("FAIL: Expected 'buddhist', got: " + result);
        }
    }

    private static void testLocaleWithoutExtensions() {
        Locale locale = new Locale("fr", "FR");
        String result = locale.getUnicodeLocaleType("ca");
        if (result == null) {
            System.out.println("PASS: No extensions, getUnicodeLocaleType returns null");
        } else {
            System.out.println("FAIL: Expected null, got: " + result);
        }
    }

    private static void testLocaleWithInvalidKey() {
        try {
            Locale locale = new Locale.Builder().setLanguage("en").build();
            // Using reflection to invoke getUnicodeLocaleType with invalid key
            java.lang.reflect.Method method = Locale.class.getMethod("getUnicodeLocaleType", String.class);
            method.invoke(locale, "invalid-key!");
            System.out.println("FAIL: Expected IllegalArgumentException for invalid key");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException) {
                System.out.println("PASS: Caught expected IllegalArgumentException for invalid key");
            } else {
                System.out.println("FAIL: Caught unexpected exception: " + cause);
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
