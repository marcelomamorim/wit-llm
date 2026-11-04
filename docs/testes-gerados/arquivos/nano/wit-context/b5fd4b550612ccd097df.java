/*
 * @test
 * @summary Test for ResourceBundle.getFallbackLocale method.
 * @run main ResourceBundleWitupTest
 */

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleWitupTest {

    public static void main(String[] args) {
        testGetFallbackLocaleWithNullBaseName();
        testGetFallbackLocaleWithDifferentLocales();
        testGetFallbackLocaleWithSameLocale();
    }

    public static void testGetFallbackLocaleWithNullBaseName() {
        ResourceBundle rb = null; // Not used directly, just to invoke method
        try {
            // Accessing the method via reflection to avoid package restrictions
            java.lang.reflect.Method method = ResourceBundle.class.getDeclaredMethod("getFallbackLocale", String.class, Locale.class);
            method.setAccessible(true);
            method.invoke(null, null, Locale.US);
            assert false : "Expected NullPointerException for null baseName";
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            assert cause instanceof NullPointerException : "Expected NullPointerException, got " + cause;
        } catch (Exception e) {
            assert false : "Unexpected exception: " + e;
        }
    }

    public static void testGetFallbackLocaleWithDifferentLocales() {
        try {
            java.lang.reflect.Method method = ResourceBundle.class.getDeclaredMethod("getFallbackLocale", String.class, Locale.class);
            method.setAccessible(true);
            Locale locale = Locale.FRANCE;
            Locale result = (Locale) method.invoke(null, "baseName", locale);
            Locale defaultLocale = Locale.getDefault();
            if (locale.equals(defaultLocale)) {
                assert result == null : "Expected null when locale equals default locale";
            } else {
                assert result.equals(defaultLocale) : "Expected fallback to default locale";
            }
        } catch (Exception e) {
            assert false : "Unexpected exception: " + e;
        }
    }

    public static void testGetFallbackLocaleWithSameLocale() {
        try {
            java.lang.reflect.Method method = ResourceBundle.class.getDeclaredMethod("getFallbackLocale", String.class, Locale.class);
            method.setAccessible(true);
            Locale locale = Locale.getDefault();
            Locale result = (Locale) method.invoke(null, "baseName", locale);
            assert result == null : "Expected null when locale is same as default locale";
        } catch (Exception e) {
            assert false : "Unexpected exception: " + e;
        }
    }
}
