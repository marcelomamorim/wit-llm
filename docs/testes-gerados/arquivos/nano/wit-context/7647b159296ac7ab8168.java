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

    static void testGetFormatsWithNull() {
        ResourceBundle rb = new ResourceBundle();
        try {
            rb.getFormats(null);
            System.out.println("testGetFormatsWithNull: Failed - no exception");
        } catch (NullPointerException e) {
            System.out.println("testGetFormatsWithNull: Passed");
        } catch (Exception e) {
            System.out.println("testGetFormatsWithNull: Failed - wrong exception: " + e);
        }
    }

    static void testGetFormatsWithValidString() {
        ResourceBundle rb = new ResourceBundle();
        try {
            List<String> formats = rb.getFormats("someBaseName");
            if (formats != null) {
                System.out.println("testGetFormatsWithValidString: Passed");
            } else {
                System.out.println("testGetFormatsWithValidString: Failed - null result");
            }
        } catch (Exception e) {
            System.out.println("testGetFormatsWithValidString: Failed - exception: " + e);
        }
    }
}
