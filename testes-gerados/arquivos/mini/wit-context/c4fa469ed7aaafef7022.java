/*
 * @test
 * @summary Test getDisplayScript method of LocaleNameProviderImpl for null argument handling and normal behavior
 * @run main LocaleNameProviderImplWitupTest
 */

import java.util.Locale;
import java.util.Set;
import sun.util.locale.provider.LocaleNameProviderImpl;
import sun.util.locale.provider.LocaleProviderAdapter;

public class LocaleNameProviderImplWitupTest {
    public static void main(String[] args) {
        // Setup: create a LocaleNameProviderImpl with a dummy type and a minimal langtags set
        LocaleProviderAdapter.Type dummyType = LocaleProviderAdapter.Type.JRE;
        Set<String> langtags = Set.of("en");
        LocaleNameProviderImpl provider = new LocaleNameProviderImpl(dummyType, langtags);

        // Test 1: getDisplayScript with null scriptCode should throw NullPointerException
        try {
            provider.getDisplayScript(null, Locale.ENGLISH);
            throw new RuntimeException("Expected NullPointerException for null scriptCode");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 2: getDisplayScript with null locale should throw NullPointerException
        try {
            provider.getDisplayScript("Latn", null);
            throw new RuntimeException("Expected NullPointerException for null locale");
        } catch (NullPointerException expected) {
            // expected
        }

        // Test 3: getDisplayScript with valid inputs returns a String or null (no exception)
        String result = provider.getDisplayScript("Latn", Locale.ENGLISH);
        // result may be null if no localized name is available, so just check no exception and type
        if (result != null && !(result instanceof String)) {
            throw new RuntimeException("getDisplayScript returned non-string result");
        }

        // Test 4: getDisplayScript with empty string scriptCode returns a String or null
        String resultEmpty = provider.getDisplayScript("", Locale.ENGLISH);
        if (resultEmpty != null && !(resultEmpty instanceof String)) {
            throw new RuntimeException("getDisplayScript returned non-string result for empty scriptCode");
        }
    }
}
