/*
 * @test
 * @summary Test for getFloat method in UnsafeLongFieldAccessorImpl
 * @run main UnsafeLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeLongFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetFloatWithInvalidObject();
    }

    private static void testGetFloatWithInvalidObject() throws Exception {
        // Setup: create an instance of UnsafeLongFieldAccessorImpl with a dummy Field
        Field dummyField = null;
        try {
            dummyField = String.class.getDeclaredField("value");
        } catch (NoSuchFieldException e) {
            // Should not happen, as String has a "value" field
        }
        jdk.internal.reflect.UnsafeLongFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeLongFieldAccessorImpl(dummyField);
        Object invalidObj = new Object();
        try {
            accessor.getFloat(invalidObj);
            // Expecting IllegalArgumentException due to guard condition
            System.out.println("FAIL: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException.");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }
}
