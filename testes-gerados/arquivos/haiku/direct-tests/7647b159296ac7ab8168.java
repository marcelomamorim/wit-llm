/*
 * @test
 * @summary Verifies ResourceBundle.Control.getFormats null handling and default formats
 * @run main ResourceBundleWitupTest
 */

import java.util.List;
import java.util.ResourceBundle;

public class ResourceBundleWitupTest {

    public static void main(String[] args) {
        ResourceBundle.Control control = ResourceBundle.Control.getControl(ResourceBundle.Control.FORMAT_DEFAULT);

        boolean npeThrown = false;
        try {
            control.getFormats(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("Expected NullPointerException for null baseName");
        }

        List<String> formats = control.getFormats("sample.Base");
        if (formats == null) {
            throw new RuntimeException("getFormats returned null");
        }
        if (formats.size() != 2) {
            throw new RuntimeException("Unexpected format count: " + formats.size());
        }
        if (!formats.contains("java.class") || !formats.contains("java.properties")) {
            throw new RuntimeException("Unexpected formats: " + formats);
        }

        List<String> again = control.getFormats("other.Base");
        if (!formats.equals(again)) {
            throw new RuntimeException("Formats differ between calls");
        }
    }
}
