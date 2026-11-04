/*
 * @test
 * @summary Test getDisplayScript(Locale) method of java.util.Locale for null input and normal usage
 * @run main LocaleWitupTest
 */

public class LocaleWitupTest {
    public static void main(String[] args) {
        testGetDisplayScriptWithNull();
        testGetDisplayScriptWithValidLocale();
    }

    private static void testGetDisplayScriptWithNull() {
        Locale locale = new Locale("en", "US");
        try {
            locale.getDisplayScript(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testGetDisplayScriptWithValidLocale() {
        Locale locale = new Locale("en", "US");
        Locale inLocale = new Locale("fr", "FR");
        String script = locale.getDisplayScript(inLocale);
        if (script == null) {
            throw new RuntimeException("getDisplayScript returned null for valid input");
        }
        // The script is usually empty string for these locales, so just check non-null
    }
}
