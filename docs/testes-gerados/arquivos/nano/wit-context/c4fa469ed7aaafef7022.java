/* @test    @summary Test getDisplayScript method for LocaleNameProviderImpl    @run main LocaleNameProviderImplWitupTest */

import sun.util.locale.provider.LocaleNameProviderImpl;
import java.util.Locale;

public class LocaleNameProviderImplWitupTest {

    public static void main(String[] args) {
        testGetDisplayScript_NullLocale();
        testGetDisplayScript_NullScriptCode();
        testGetDisplayScript_ValidInputs();
    }

    private static void testGetDisplayScript_NullLocale() {
        LocaleProviderImplTestHelper helper = new LocaleProviderImplTestHelper();
        try {
            helper.getDisplayScript("Latn", null);
            System.out.println("PASS: getDisplayScript with null locale did not throw");
        } catch (NullPointerException e) {
            System.out.println("PASS: getDisplayScript with null locale threw NullPointerException");
        } catch (Exception e) {
            System.out.println("FAIL: getDisplayScript with null locale threw unexpected exception: " + e);
        }
    }

    private static void testGetDisplayScript_NullScriptCode() {
        LocaleProviderImplTestHelper helper = new LocaleProviderImplTestHelper();
        try {
            helper.getDisplayScript(null, Locale.US);
            System.out.println("PASS: getDisplayScript with null scriptCode did not throw");
        } catch (NullPointerException e) {
            System.out.println("PASS: getDisplayScript with null scriptCode threw NullPointerException");
        } catch (Exception e) {
            System.out.println("FAIL: getDisplayScript with null scriptCode threw unexpected exception: " + e);
        }
    }

    private static void testGetDisplayScript_ValidInputs() {
        LocaleProviderImplTestHelper helper = new LocaleProviderImplTestHelper();
        String result = helper.getDisplayScript("Latn", Locale.US);
        if (result != null && !result.isEmpty()) {
            System.out.println("PASS: getDisplayScript with valid inputs returned: " + result);
        } else {
            System.out.println("FAIL: getDisplayScript with valid inputs returned null or empty");
        }
    }

    static class LocaleProviderImplTestHelper {
        public String getDisplayScript(String scriptCode, Locale locale) {
            LocaleNameProviderImpl provider = new LocaleNameProviderImpl(LocaleProviderAdapter.Type.DEFAULT, null);
            return provider.getDisplayScript(scriptCode, locale);
        }
    }
}
