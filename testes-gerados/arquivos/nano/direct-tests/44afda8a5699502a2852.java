/*
 * @test
 * @summary Test getBoolean method of UnsafeStaticShortFieldAccessorImpl
 * @run main UnsafeStaticShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticShortFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetBooleanThrowsException();
    }

    private static void testGetBooleanThrowsException() {
        try {
            // Create an instance of UnsafeStaticShortFieldAccessorImpl with a dummy Field
            Field dummyField = null; // No actual reflection needed for this test
            jdk.internal.reflect.UnsafeStaticShortFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeStaticShortFieldAccessorImpl(dummyField);
            // Call getBoolean, expecting an IllegalArgumentException
            accessor.getBoolean(null);
            System.out.println("FAIL: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: getBoolean threw IllegalArgumentException as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
