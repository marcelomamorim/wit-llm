/*
 * @test
 * @summary Test getFormats method of ResourceBundle for various inputs
 * @run main ResourceBundleWitupTest
 */

import java.util.ResourceBundle;
import java.util.List;

public class ResourceBundleWitupTest {

    public static void main(String[] args) {
        testGetFormatsWithValidBaseName();
        testGetFormatsWithNullBaseName();
    }

    static void testGetFormatsWithValidBaseName() {
        ResourceBundle rb = ResourceBundle.getBundle("java.util.ResourceBundle");
        List<String> formats = rb.getFormats("java.util.ResourceBundle");
        if (formats == null) {
            throw new AssertionError("Expected non-null list for valid baseName");
        }
        if (!(formats instanceof List)) {
            throw new AssertionError("formats should be instance of List");
        }
        System.out.println("testGetFormatsWithValidBaseName passed");
    }

    static void testGetFormatsWithNullBaseName() {
        ResourceBundle rb = ResourceBundle.getBundle("java.util.ResourceBundle");
        try {
            rb.getFormats(null);
            throw new AssertionError("Expected NullPointerException for null baseName");
        } catch (NullPointerException e) {
            System.out.println("testGetFormatsWithNullBaseName passed");
        }
    }
}
