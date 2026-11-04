/*
 * @test
 * @summary Test ResourceBundle.getCandidateLocales behavior
 * @run main ResourceBundleWitupTest
 */

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import sun.util.locale.BaseLocale;

public class ResourceBundleWitupTest {
    public static void main(String[] args) throws Exception {
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

        // Test 1: baseName null should throw NullPointerException
        try {
            rb.getCandidateLocales(null, Locale.ENGLISH);
            throw new RuntimeException("Expected NullPointerException for null baseName");
        } catch (NullPointerException expected) {
        }

        // Test 2: valid baseName and locale returns non-null list
        List<Locale> candidates = rb.getCandidateLocales("baseName", Locale.ENGLISH);
        if (candidates == null) {
            throw new RuntimeException("getCandidateLocales returned null list");
        }

        // Test 3: returned list is a copy (modification does not affect cache)
        int originalSize = candidates.size();
        candidates.add(Locale.FRANCE);
        List<Locale> candidates2 = rb.getCandidateLocales("baseName", Locale.ENGLISH);
        if (candidates2.size() != originalSize) {
            throw new RuntimeException("Returned list is not a copy, cache was modified");
        }
    }
}
