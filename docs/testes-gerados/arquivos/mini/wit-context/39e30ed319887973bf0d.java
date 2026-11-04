/*
 * @test
 * @summary Test ResourceBundle.getCandidateLocales behavior including NPE on null baseName
 * @run main ResourceBundleWitupTest
 */

import java.util.Locale;
import java.util.List;
import java.util.ResourceBundle;

public class ResourceBundleWitupTest {
    public static void main(String[] args) {
        testGetCandidateLocalesNullBaseName();
        testGetCandidateLocalesValid();
    }

    private static void testGetCandidateLocalesNullBaseName() {
        Locale locale = Locale.US;
        try {
            new ResourceBundle() {
                @Override
                protected Object handleGetObject(String key) {
                    return null;
                }

                @Override
                public java.util.Enumeration<String> getKeys() {
                    return null;
                }
            }.getCandidateLocales(null, locale);
            throw new AssertionError("Expected NullPointerException for null baseName");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testGetCandidateLocalesValid() {
        Locale locale = Locale.US;
        ResourceBundle rb = new ResourceBundle() {
            @Override
            protected Object handleGetObject(String key) {
                return null;
            }

            @Override
            public java.util.Enumeration<String> getKeys() {
                return null;
            }
        };
        List<Locale> candidates = rb.getCandidateLocales("baseName", locale);
        if (candidates == null) {
            throw new AssertionError("getCandidateLocales returned null");
        }
        if (candidates.isEmpty()) {
            throw new AssertionError("getCandidateLocales returned empty list");
        }
        // Check that the first candidate's base locale equals locale.getBaseLocale()
        Locale first = candidates.get(0);
        if (!first.getBaseLocale().equals(locale.getBaseLocale())) {
            throw new AssertionError("First candidate locale base does not match input locale base");
        }
    }
}
