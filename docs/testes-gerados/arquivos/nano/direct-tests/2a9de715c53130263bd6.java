/* @test    @summary Test getByte method of UnsafeObjectFieldAccessorImpl class to ensure it throws IllegalArgumentException as expected    @run main UnsafeObjectFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeObjectFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetByteThrowsException();
    }

    public static void testGetByteThrowsException() {
        try {
            // Create an instance of UnsafeObjectFieldAccessorImpl with a dummy Field
            Field dummyField = null; // No actual reflection needed for this test
            jdk.internal.reflect.UnsafeObjectFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeObjectFieldAccessorImpl(dummyField);
            // Call getByte with a null object, expecting IllegalArgumentException
            accessor.getByte(null);
            System.out.println("FAIL: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: IllegalArgumentException was thrown as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
