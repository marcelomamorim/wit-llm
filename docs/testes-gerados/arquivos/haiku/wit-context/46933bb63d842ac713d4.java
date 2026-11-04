/*
 * @test
 * @summary WITUP-derived tests for LocaleNameProvider.getDisplayUnicodeExtensionKey null checks
 * @run main LocaleNameProviderWitupTest
 */
import java.util.Locale;
import java.util.spi.LocaleNameProvider;

public class LocaleNameProviderWitupTest {

    static class TestProvider extends LocaleNameProvider {
        public Locale[] getAvailableLocales() {
            return new Locale[] { Locale.US };
        }

        public String getDisplayLanguage(String languageCode, Locale locale) {
            return null;
        }

        public String getDisplayScript(String scriptCode, Locale locale) {
            return null;
        }

        public String getDisplayCountry(String countryCode, Locale locale) {
            return null;
        }

        public String getDisplayVariant(String variant, Locale locale) {
            return null;
        }
    }

    interface Action {
        void run();
    }

    static void expectNpe(String name, Action action) {
        try {
            action.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException: " + name);
    }

    public static void main(String[] args) {
        final LocaleNameProvider provider = new TestProvider();

        expectNpe("null key", new Action() {
            public void run() {
                provider.getDisplayUnicodeExtensionKey(null, Locale.US);
            }
        });

        expectNpe("null key and null locale", new Action() {
            public void run() {
                provider.getDisplayUnicodeExtensionKey(null, null);
            }
        });

        expectNpe("null locale with non-null key", new Action() {
            public void run() {
                provider.getDisplayUnicodeExtensionKey("nu", null);
            }
        });

        String result = provider.getDisplayUnicodeExtensionKey("nu", Locale.US);
        if (result != null) {
            throw new RuntimeException("Expected null for valid input, got: " + result);
        }

        result = provider.getDisplayUnicodeExtensionKey("co", Locale.FRANCE);
        if (result != null) {
            throw new RuntimeException("Expected null for valid input, got: " + result);
        }
    }
}
