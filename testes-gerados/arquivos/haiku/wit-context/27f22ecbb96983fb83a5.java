/*
 * @test
 * @summary Verify ResourceBundle.Control.getFormats(String) rejects null base names and returns the default formats otherwise
 * @run main ResourceBundleWitupTest
 */

import java.util.List;
import java.util.ResourceBundle;

public class ResourceBundleWitupTest {
    public static void main(String[] args) {
        ResourceBundle.Control[] controls = new ResourceBundle.Control[] {
            ResourceBundle.Control.getControl(ResourceBundle.Control.FORMAT_DEFAULT),
            ResourceBundle.Control.getNoFallbackControl(ResourceBundle.Control.FORMAT_DEFAULT)
        };
        for (ResourceBundle.Control control : controls) {
            try {
                control.getFormats(null);
                throw new AssertionError("expected NullPointerException for null baseName");
            } catch (NullPointerException expected) {
            }
            List<String> formats = control.getFormats("base");
            if (!ResourceBundle.Control.FORMAT_DEFAULT.equals(formats)) {
                throw new AssertionError("unexpected formats: " + formats);
            }
        }
    }
}
