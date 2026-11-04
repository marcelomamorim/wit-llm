/*
 * @test
 * @summary Test for handleGetObject method in ListResourceBundle
 * @run main ListResourceBundleWitupTest
 */

import java.util.ListResourceBundle;

public class ListResourceBundleWitupTest {

    public static void main(String[] args) {
        testHandleGetObjectWithNullKey();
        testHandleGetObjectWithValidKey();
    }

    public static void testHandleGetObjectWithNullKey() {
        ListResourceBundle bundle = new ListResourceBundle() {
            protected Object[][] getContents() {
                return new Object[][] { {"key1", "value1"} };
            }
        };
        try {
            Object result = bundle.handleGetObject(null);
            if (result != null) {
                throw new AssertionError("Expected NullPointerException for null key, but got: " + result);
            }
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    public static void testHandleGetObjectWithValidKey() {
        ListResourceBundle bundle = new ListResourceBundle() {
            protected Object[][] getContents() {
                return new Object[][] { {"key1", "value1"}, {"key2", 42} };
            }
        };
        Object result1 = bundle.handleGetObject("key1");
        if (!"value1".equals(result1)) {
            throw new AssertionError("Expected 'value1' for key1, but got: " + result1);
        }
        Object result2 = bundle.handleGetObject("key2");
        if (!(result2 instanceof Integer) || ((Integer) result2).intValue() != 42) {
            throw new AssertionError("Expected 42 for key2, but got: " + result2);
        }
    }
}
