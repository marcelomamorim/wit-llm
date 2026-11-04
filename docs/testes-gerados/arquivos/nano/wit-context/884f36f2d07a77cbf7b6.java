/* @test    @summary Test for getDisplayLanguage method in LocaleNameProviderImpl to verify behavior with null inputs.    @run main LocaleNameProviderImplWitupTest */

import sun.util.locale.provider.LocaleNameProviderImpl;
import java.util.Locale;

public class LocaleNameProviderImplWitupTest {

    public static void main(String[] args) {
        testGetDisplayLanguageWithNullLang();
        testGetDisplayLanguageWithNullLocale();
    }

    private static void testGetDisplayLanguageWithNullLang() {
        LocaleProviderImplTestHelper helper = new LocaleProviderImplTestHelper();
        try {
            helper.invokeGetDisplayLanguage(null, Locale.US);
            System.out.println("testGetDisplayLanguageWithNullLang: Failed - Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testGetDisplayLanguageWithNullLang: Passed");
        } catch (Throwable t) {
            System.out.println("testGetDisplayLanguageWithNullLang: Failed - Unexpected exception: " + t);
        }
    }

    private static void testGetDisplayLanguageWithNullLocale() {
        LocaleProviderImplTestHelper helper = new LocaleProviderImplTestHelper();
        try {
            helper.invokeGetDisplayLanguage("en", null);
            System.out.println("testGetDisplayLanguageWithNullLocale: Failed - Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testGetDisplayLanguageWithNullLocale: Passed");
        } catch (Throwable t) {
            System.out.println("testGetDisplayLanguageWithNullLocale: Failed - Unexpected exception: " + t);
        }
    }

    static class LocaleProviderImplTestHelper {
        public String invokeGetDisplayLanguage(String lang, Locale locale) {
            return new LocaleNameProviderImpl(LocaleProviderAdapter.Type.DEFAULT, null).getDisplayLanguage(lang, locale);
        }
    }
}
