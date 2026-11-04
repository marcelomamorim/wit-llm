/*
 * @test
 * @summary Test java.util.Locale.getISOLanguages() for correctness and regression
 * @run main LocaleWitupTest
 */

public class LocaleWitupTest {
    public static void main(String[] args) {
        // Call getISOLanguages multiple times to check for consistency and no exceptions
        String[] langs1 = java.util.Locale.getISOLanguages();
        String[] langs2 = java.util.Locale.getISOLanguages();

        if (langs1 == null) {
            throw new RuntimeException("getISOLanguages() returned null");
        }

        if (langs1.length == 0) {
            throw new RuntimeException("getISOLanguages() returned empty array");
        }

        if (langs1.length != langs2.length) {
            throw new RuntimeException("getISOLanguages() returned arrays of different lengths on repeated calls");
        }

        for (int i = 0; i < langs1.length; i++) {
            if (langs1[i] == null) {
                throw new RuntimeException("getISOLanguages() returned null language code at index " + i);
            }
            if (langs1[i].length() != 2) {
                throw new RuntimeException("getISOLanguages() returned language code not of length 2 at index " + i + ": " + langs1[i]);
            }
            if (!langs1[i].equals(langs2[i])) {
                throw new RuntimeException("getISOLanguages() returned different language codes at index " + i);
            }
            // Check that language codes are lowercase ASCII letters
            for (int j = 0; j < 2; j++) {
                char c = langs1[i].charAt(j);
                if (c < 'a' || c > 'z') {
                    throw new RuntimeException("getISOLanguages() returned invalid language code character at index " + i + ": " + langs1[i]);
                }
            }
        }
    }
}
