/* @test    @summary Test getDisplayScript method of LocaleNameProviderImpl for various inputs    @run main LocaleNameProviderImplWitupTest */

import java.util.Locale;
import sun.util.locale.provider.LocaleNameProviderImpl;

public class LocaleNameProviderImplWitupTest {

    public static void main(String[] args) {
        testGetDisplayScriptWithValidInputs();
        testGetDisplayScriptWithNullScriptCode();
        testGetDisplayScriptWithNullLocale();
        testGetDisplayScriptWithEmptyScriptCode();
        testGetDisplayScriptWithUnknownScriptCode();
    }

    private static void testGetDisplayScriptWithValidInputs() {
        Locale locale = new Locale("en", "US");
        String scriptCode = "Latn";
        String result = LocaleNameProviderImpl.getDisplayScript(scriptCode, locale);
        if (result == null || result.isEmpty()) {
            throw new AssertionError("Expected non-null, non-empty result for valid script code.");
        }
    }

    private static void testGetDisplayScriptWithNullScriptCode() {
        Locale locale = new Locale("en", "US");
        try {
            String result = LocaleNameProviderImpl.getDisplayScript(null, locale);
            if (result != null) {
                throw new AssertionError("Expected null result for null script code.");
            }
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testGetDisplayScriptWithNullLocale() {
        String scriptCode = "Latn";
        try {
            String result = LocaleNameProviderImpl.getDisplayScript(scriptCode, null);
            throw new AssertionError("Expected NullPointerException for null locale.");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testGetDisplayScriptWithEmptyScriptCode() {
        Locale locale = new Locale("en", "US");
        String scriptCode = "";
        String result = LocaleNameProviderImpl.getDisplayScript(scriptCode, locale);
        if (result == null || result.isEmpty()) {
            throw new AssertionError("Expected non-null, non-empty result for empty script code.");
        }
    }

    private static void testGetDisplayScriptWithUnknownScriptCode() {
        Locale locale = new Locale("en", "US");
        String scriptCode = "XXXX";
        String result = LocaleNameProviderImpl.getDisplayScript(scriptCode, locale);
        if (result != null) {
            // Depending on implementation, result may be null or a string
            // Accept both, but ensure no exception is thrown
        }
    }
}
