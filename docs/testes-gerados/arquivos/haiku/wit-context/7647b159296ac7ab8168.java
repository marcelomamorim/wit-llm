/*
 * @test
 * @summary ResourceBundle.Control.getFormats(null) must throw NullPointerException and valid base names must return the configured formats
 * @run main ResourceBundleWitupTest
 */

import java.util.List;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

public class ResourceBundleWitupTest {
    public static void main(String[] args) {
        Control control = Control.getControl(Control.FORMAT_PROPERTIES);

        List<String> formats = control.getFormats("sample");
        if (formats == null) {
            throw new RuntimeException("getFormats returned null for a valid base name");
        }
        if (!formats.equals(Control.FORMAT_PROPERTIES)) {
            throw new RuntimeException("unexpected formats: " + formats);
        }

        try {
            control.getFormats(null);
            throw new RuntimeException("expected NullPointerException for null baseName");
        } catch (NullPointerException expected) {
        }
    }
}
