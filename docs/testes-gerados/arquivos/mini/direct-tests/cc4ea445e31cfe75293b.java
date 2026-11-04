/*
 * @test
 * @summary Test java.util.Locale.getDisplayVariant(Locale) method behavior
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {
    public static void main(String[] args) {
        // Test 1: Locale with empty variant returns empty string
        Locale loc1 = new Locale("en", "US", "");
        String result1 = loc1.getDisplayVariant(Locale.ENGLISH);
        if (!"".equals(result1)) {
            throw new RuntimeException("Test 1 failed: expected empty string but got '" + result1 + "'");
        }

        // Test 2: Locale with non-empty variant returns non-empty string
        Locale loc2 = new Locale("en", "US", "POSIX");
        String result2 = loc2.getDisplayVariant(Locale.ENGLISH);
        if (result2 == null || result2.isEmpty()) {
            throw new RuntimeException("Test 2 failed: expected non-empty string but got '" + result2 + "'");
        }

        // Test 3: Passing Locale.ROOT as inLocale parameter
        Locale loc3 = new Locale("fr", "FR", "VARIANT");
        String result3 = loc3.getDisplayVariant(Locale.ROOT);
        if (result3 == null) {
            throw new RuntimeException("Test 3 failed: expected non-null string but got null");
        }

        // Test 4: Passing null as inLocale parameter should not throw NullPointerException
        // According to source code, no explicit null check, but getLocaleResources(inLocale) may handle null
        // We test that no exception is thrown and result is non-null
        Locale loc4 = new Locale("de", "DE", "TEST");
        String result4 = null;
        try {
            result4 = loc4.getDisplayVariant(null);
        } catch (NullPointerException npe) {
            throw new RuntimeException("Test 4 failed: getDisplayVariant(null) threw NullPointerException");
        }
        if (result4 == null) {
            throw new RuntimeException("Test 4 failed: expected non-null string but got null");
        }

        System.out.println("All tests passed.");
    }
}
