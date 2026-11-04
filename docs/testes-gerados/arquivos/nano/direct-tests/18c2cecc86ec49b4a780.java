/* @test    @summary Test getByte method of UnsafeStaticBooleanFieldAccessorImpl for exception throwing    @run main UnsafeStaticBooleanFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeStaticBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetByteThrowsException();
    }

    private static void testGetByteThrowsException() {
        try {
            // Create an instance of the class with a dummy Field object
            Field dummyField = null; // No actual reflection needed, method does not use the parameter
            jdk.internal.reflect.UnsafeStaticBooleanFieldAccessorImpl accessor =
                new jdk.internal.reflect.UnsafeStaticBooleanFieldAccessorImpl(dummyField);
            accessor.getByte(null);
            System.out.println("FAIL: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: getByte() threw IllegalArgumentException as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
