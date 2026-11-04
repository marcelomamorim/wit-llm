/*
 * @test
 * @summary Verify ResourceBundle.Control.getFormats(String) null handling and default formats
 * @run main ResourceBundleWitupTest
 */

import java.util.ResourceBundle;
import java.util.List;

public class ResourceBundleWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        ResourceBundle.Control control = new ResourceBundle.Control() {};

        boolean npeThrown = false;
        try {
            control.getFormats(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        check(npeThrown, "getFormats(null) must throw NullPointerException");

        List<String> formats = control.getFormats("sample");
        check(formats != null, "getFormats must not return null for non-null baseName");
        check(formats.size() == 2, "default formats must contain 2 entries, got " + formats.size());
        check(formats.contains("java.class"), "default formats must contain java.class");
        check(formats.contains("java.properties"), "default formats must contain java.properties");

        List<String> formats2 = control.getFormats("");
        check(formats2.equals(formats), "empty baseName must yield the same default formats");

        List<String> formats3 = control.getFormats("other.pkg.Bundle");
        check(formats3.equals(formats), "formats must not depend on baseName");
    }
}
