/*
 * @test
 * @summary Test for UnsafeIntegerFieldAccessorImpl.getInt method exception behavior
 * @run main UnsafeIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeIntegerFieldAccessorImpl;

public class UnsafeIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetIntWithInvalidObject();
    }

    private static void testGetIntWithInvalidObject() throws Exception {
        // Setup: create a dummy Field object with a known declaring class
        // Since Field constructor is package-private, use reflection to obtain a real Field
        // from a class that is not assignable from the test object
        class Dummy {
            public int value;
        }
        Dummy dummyInstance = new Dummy();
        Field field = Dummy.class.getDeclaredField("value");
        // Create UnsafeIntegerFieldAccessorImpl with this field
        UnsafeIntegerFieldAccessorImpl accessor = new UnsafeIntegerFieldAccessorImpl(field);
        Object invalidObj = new Object();
        try {
            accessor.getInt(invalidObj);
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("Caught expected IllegalArgumentException.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
        }
    }
}
