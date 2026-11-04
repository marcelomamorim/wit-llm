/* @test    @summary Test handleGetObject method of PropertyResourceBundle for existing key    @run main PropertyResourceBundleWitupTest */

import java.util.PropertyResourceBundle;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class PropertyResourceBundleWitupTest {

    public static void main(String[] args) {
        testHandleGetObjectWithExistingKey();
        testHandleGetObjectWithNullKey();
        testHandleGetObjectWithNonExistingKey();
    }

    static void testHandleGetObjectWithExistingKey() {
        String properties = "greeting=Hello";
        InputStream stream = new ByteArrayInputStream(properties.getBytes());
        PropertyResourceBundle bundle = new PropertyResourceBundle(stream);
        Object result = bundle.handleGetObject("greeting");
        if ("Hello".equals(result)) {
            System.out.println("PASS: handleGetObject with existing key");
        } else {
            System.out.println("FAIL: handleGetObject with existing key");
        }
    }

    static void testHandleGetObjectWithNullKey() {
        String properties = "key=value";
        InputStream stream = new ByteArrayInputStream(properties.getBytes());
        PropertyResourceBundle bundle = new PropertyResourceBundle(stream);
        try {
            bundle.handleGetObject(null);
            System.out.println("FAIL: handleGetObject with null key did not throw");
        } catch (NullPointerException e) {
            System.out.println("PASS: handleGetObject with null key threw NullPointerException");
        }
    }

    static void testHandleGetObjectWithNonExistingKey() {
        String properties = "key1=value1";
        InputStream stream = new ByteArrayInputStream(properties.getBytes());
        PropertyResourceBundle bundle = new PropertyResourceBundle(stream);
        Object result = bundle.handleGetObject("nonexistent");
        if (result == null) {
            System.out.println("PASS: handleGetObject with non-existing key returns null");
        } else {
            System.out.println("FAIL: handleGetObject with non-existing key");
        }
    }
}
