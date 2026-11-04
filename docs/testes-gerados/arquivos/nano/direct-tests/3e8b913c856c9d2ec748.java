/*
 * @test
 * @summary Test getShort method of UnsafeStaticDoubleFieldAccessorImpl
 * @run main UnsafeStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetShortThrowsException();
    }

    private static void testGetShortThrowsException() {
        try {
            // Create a dummy instance of UnsafeStaticDoubleFieldAccessorImpl with null field
            Field dummyField = null;
            jdk.internal.reflect.UnsafeStaticDoubleFieldAccessorImpl accessor = 
                new jdk.internal.reflect.UnsafeStaticDoubleFieldAccessorImpl(dummyField);
            // Call getShort, expecting an IllegalArgumentException
            accessor.getShort(null);
            System.out.println("testGetShortThrowsException: FAILED - no exception thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testGetShortThrowsException: PASSED");
        } catch (Throwable t) {
            System.out.println("testGetShortThrowsException: FAILED - unexpected exception: " + t);
        }
    }
}
