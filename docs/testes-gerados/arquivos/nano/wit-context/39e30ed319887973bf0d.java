/*
 * @test
 * @summary Test for ResourceBundle.getCandidateLocales handling null baseName
 * @run main ResourceBundleWitupTest
 */

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleWitupTest {

    public static void main(String[] args) {
        testGetCandidateLocalesWithNullBaseName();
        testGetCandidateLocalesWithValidInputs();
    }

    public static void testGetCandidateLocalesWithNullBaseName() {
        try {
            ResourceBundle.getCandidateLocales(null, Locale.US);
            System.err.println("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("NullPointerException correctly thrown for null baseName");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }

    public static void testGetCandidateLocalesWithValidInputs() {
        try {
            List<Locale> locales = ResourceBundle.getCandidateLocales("baseName", Locale.US);
            if (locales != null && !locales.isEmpty()) {
                System.out.println("getCandidateLocales returned: " + locales);
            } else {
                System.err.println("getCandidateLocales returned null or empty list");
            }
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }
}
