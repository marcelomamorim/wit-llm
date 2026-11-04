/* @test    @summary Test getBoolean method of UnsafeStaticByteFieldAccessorImpl for exception throwing    @run main UnsafeStaticByteFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeStaticByteFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetBooleanThrowsException();
    }

    private static void testGetBooleanThrowsException() {
        try {
            // Create an instance of UnsafeStaticByteFieldAccessorImpl with a dummy Field
            Field dummyField = null; // No actual reflection needed for this test
            jdk.internal.reflect.UnsafeStaticByteFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeStaticByteFieldAccessorImpl(dummyField);
            accessor.getBoolean(null);
            System.out.println("FAIL: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: getBoolean threw IllegalArgumentException as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t);
        }
    }
}
