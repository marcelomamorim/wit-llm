/*
 * @test
 * @summary Test getInt method of UnsafeStaticDoubleFieldAccessorImpl
 * @run main UnsafeStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetIntThrowsException();
    }

    public static void testGetIntThrowsException() {
        try {
            // Create a dummy Field object (not used in method, but required for constructor)
            Field dummyField = null;
            // Instantiate the class with the dummy field
            jdk.internal.reflect.UnsafeStaticDoubleFieldAccessorImpl accessor = 
                new jdk.internal.reflect.UnsafeStaticDoubleFieldAccessorImpl(dummyField);
            // Call getInt and expect an IllegalArgumentException
            accessor.getInt(null);
            System.out.println("testGetIntThrowsException: FAILED - no exception thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testGetIntThrowsException: PASSED");
        } catch (Throwable t) {
            System.out.println("testGetIntThrowsException: FAILED - unexpected exception: " + t);
        }
    }
}
