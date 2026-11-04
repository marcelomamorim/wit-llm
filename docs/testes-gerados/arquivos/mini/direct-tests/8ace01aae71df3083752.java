/*
 * @test
 * @summary Test java.util.Locale.getISOLanguages() returns a non-null copy of ISO language codes
 * @run main java.util.LocaleWitupTest
 */

public class LocaleWitupTest {
    public static void main(String[] args) {
        String[] langs1 = java.util.Locale.getISOLanguages();
        if (langs1 == null) {
            throw new RuntimeException("getISOLanguages() returned null");
        }
        if (langs1.length == 0) {
            throw new RuntimeException("getISOLanguages() returned empty array");
        }
        // Check that all elements are non-null and non-empty strings
        for (int i = 0; i < langs1.length; i++) {
            if (langs1[i] == null || langs1[i].isEmpty()) {
                throw new RuntimeException("getISOLanguages() returned null or empty element at index " + i);
            }
        }
        // Check that the returned array is a copy (modifying it does not affect subsequent calls)
        String[] langs2 = java.util.Locale.getISOLanguages();
        if (langs1 == langs2) {
            throw new RuntimeException("getISOLanguages() returned the same array instance on multiple calls");
        }
        // Modify langs1 and verify langs2 is unaffected
        String originalFirst = langs2[0];
        langs1[0] = "xx";
        String[] langs3 = java.util.Locale.getISOLanguages();
        if (!langs3[0].equals(originalFirst)) {
            throw new RuntimeException("Modifying returned array affected subsequent calls");
        }
    }
}
