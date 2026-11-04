/*
 * @test
 * @summary Test for UnsafeShortFieldAccessorImpl.getInt method behavior
 * @run main UnsafeShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeShortFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetIntWithInvalidObject();
        testGetIntWithValidObject();
    }

    private static void testGetIntWithInvalidObject() throws Exception {
        Field dummyField = null; // Placeholder, as actual field is not used in this test
        // Create an instance of UnsafeShortFieldAccessorImpl with a dummy field
        jdk.internal.reflect.UnsafeShortFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeShortFieldAccessorImpl(dummyField);
        Object invalidObject = new Object();
        try {
            accessor.getInt(invalidObject);
            System.out.println("FAIL: Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testGetIntWithValidObject() throws Exception {
        // Setup a dummy class with a short field
        class Dummy {
            public short value = 42;
        }
        Dummy dummyInstance = new Dummy();
        Field field = Dummy.class.getField("value");
        jdk.internal.reflect.UnsafeShortFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeShortFieldAccessorImpl(field);
        // Ensure the object is of the correct class
        Object obj = dummyInstance;
        int result = accessor.getInt(obj);
        if (result == 42) {
            System.out.println("PASS: getInt returned expected value 42");
        } else {
            System.out.println("FAIL: getInt returned " + result);
        }
    }
}
