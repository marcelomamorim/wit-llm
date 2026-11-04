/*
 * @test
 * @summary Verifies ResourceBundle.Control.getCandidateLocales null-name handling and returned list independence
 * @run main ResourceBundleWitupTest
 */

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleWitupTest {

    public static void main(String[] args) {
        ResourceBundle.Control control =
            ResourceBundle.Control.getControl(ResourceBundle.Control.FORMAT_DEFAULT);

        try {
            control.getCandidateLocales(null, Locale.US);
            throw new RuntimeException("Expected NullPointerException for null baseName");
        } catch (NullPointerException expected) {
        }

        List<Locale> candidates = control.getCandidateLocales("base", Locale.US);
        if (candidates.isEmpty()) {
            throw new RuntimeException("Candidate list must not be empty");
        }
        if (!candidates.get(0).equals(Locale.US)) {
            throw new RuntimeException("First candidate must be the requested locale, got " + candidates.get(0));
        }
        if (!candidates.contains(Locale.ROOT)) {
            throw new RuntimeException("Candidate list must contain Locale.ROOT: " + candidates);
        }

        int size = candidates.size();
        candidates.clear();
        List<Locale> again = control.getCandidateLocales("base", Locale.US);
        if (again.size() != size) {
            throw new RuntimeException("Returned list must be an independent copy");
        }
    }
}
