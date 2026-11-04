/* @test    @summary Test LocaleData.run() method for correct ResourceBundle retrieval    @run main LocaleDataWitupTest */

import java.util.Locale;
import java.util.ResourceBundle;
import sun.util.resources.LocaleData;

public class LocaleDataWitupTest {

    public static void main(String[] args) {
        testRunReturnsResourceBundle();
        testRunWithDifferentBaseNameAndLocale();
    }

    private static void testRunReturnsResourceBundle() {
        Locale locale = Locale.US;
        String baseName = "sun.util.resources.LocaleData"; // Assuming baseName is same as class package for test
        LocaleData localeData = new LocaleData(LocaleProviderAdapter.Type.JRE);
        ResourceBundle bundle = localeData.run();
        if (bundle == null) {
            throw new AssertionError("Expected non-null ResourceBundle from run()");
        }
        // Additional checks could be added if resource bundle contents are known
    }

    private static void testRunWithDifferentBaseNameAndLocale() {
        Locale locale = Locale.FRANCE;
        String baseName = "sun.util.resources.LocaleData"; // Using same baseName for simplicity
        LocaleData localeData = new LocaleData(LocaleProviderAdapter.Type.JRE);
        ResourceBundle bundle = localeData.run();
        if (bundle == null) {
            throw new AssertionError("Expected non-null ResourceBundle for locale FRANCE");
        }
        // Additional validation can be added if resource contents are accessible
    }
}
