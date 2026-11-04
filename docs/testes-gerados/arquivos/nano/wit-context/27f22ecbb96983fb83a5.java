/*
 * @test
 * @summary Test for ResourceBundle.getFormats(String)
 * @run main ResourceBundleWitupTest
 */

import java.util.List;
import java.util.ResourceBundle;

public class ResourceBundleWitupTest {

    public static void main(String[] args) {
        testGetFormatsWithNull();
        testGetFormatsWithValidString();
    }

    public static void testGetFormatsWithNull() {
        ResourceBundle rb = null;
        try {
            // Invoca o método com null, esperando NullPointerException
            ResourceBundle.getFormats(null);
            System.out.println("FAIL: Expected NullPointerException for null baseName");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected for null baseName");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    public static void testGetFormatsWithValidString() {
        try {
            List<String> formats = ResourceBundle.getFormats("dummy");
            if (formats != null && !formats.isEmpty()) {
                System.out.println("PASS: getFormats returned non-empty list for valid string");
            } else {
                System.out.println("FAIL: getFormats returned null or empty list for valid string");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
