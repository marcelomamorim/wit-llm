/* @test    @summary Test for handleGetObject method in PropertyResourceBundle to verify behavior with null key and valid key    @run main PropertyResourceBundleWitupTest */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class PropertyResourceBundleWitupTest {

    public static void main(String[] args) {
        testHandleGetObjectWithNullKey();
        testHandleGetObjectWithValidKey();
    }

    static void testHandleGetObjectWithNullKey() {
        try {
            String propertiesContent = "key1=value1\nkey2=value2";
            InputStream stream = new ByteArrayInputStream(propertiesContent.getBytes());
            PropertyResourceBundle bundle = new PropertyResourceBundle(stream);
            Object result = bundle.handleGetObject(null);
            System.out.println("testHandleGetObjectWithNullKey: FAILED - Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testHandleGetObjectWithNullKey: PASSED");
        } catch (Exception e) {
            System.out.println("testHandleGetObjectWithNullKey: FAILED - Unexpected exception: " + e);
        }
    }

    static void testHandleGetObjectWithValidKey() {
        try {
            String propertiesContent = "key1=value1\nkey2=value2";
            InputStream stream = new ByteArrayInputStream(propertiesContent.getBytes());
            PropertyResourceBundle bundle = new PropertyResourceBundle(stream);
            Object result = bundle.handleGetObject("key1");
            if ("value1".equals(result)) {
                System.out.println("testHandleGetObjectWithValidKey: PASSED");
            } else {
                System.out.println("testHandleGetObjectWithValidKey: FAILED - Unexpected value: " + result);
            }
        } catch (Exception e) {
            System.out.println("testHandleGetObjectWithValidKey: FAILED - Exception: " + e);
        }
    }
}
