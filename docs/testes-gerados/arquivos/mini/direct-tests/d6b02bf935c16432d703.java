/*
 * @test
 * @summary Test java.util.Locale.getDisplayScript(Locale) method behavior
 * @run main LocaleWitupTest
 */

public class LocaleWitupTest {
    public static void main(String[] args) {
        // Test 1: Basic usage with default locale
        java.util.Locale locale = new java.util.Locale("en", "US", "POSIX");
        String script = locale.getDisplayScript(java.util.Locale.ENGLISH);
        if (script == null) {
            throw new RuntimeException("getDisplayScript returned null for locale with no script");
        }

        // Test 2: Locale with explicit script
        java.util.Locale localeWithScript = new java.util.Locale.Builder().setLanguage("zh").setScript("Hans").setRegion("CN").build();
        String displayScript = localeWithScript.getDisplayScript(java.util.Locale.ENGLISH);
        if (displayScript == null || displayScript.isEmpty()) {
            throw new RuntimeException("getDisplayScript returned null or empty for locale with script");
        }

        // Test 3: Passing null as inLocale should use default locale
        String scriptDefaultLocale = localeWithScript.getDisplayScript(null);
        if (scriptDefaultLocale == null || scriptDefaultLocale.isEmpty()) {
            throw new RuntimeException("getDisplayScript returned null or empty when inLocale is null");
        }

        // Test 4: Passing a different locale for display
        String scriptFrench = localeWithScript.getDisplayScript(java.util.Locale.FRENCH);
        if (scriptFrench == null || scriptFrench.isEmpty()) {
            throw new RuntimeException("getDisplayScript returned null or empty for French display locale");
        }

        System.out.println("All tests passed.");
    }
}
