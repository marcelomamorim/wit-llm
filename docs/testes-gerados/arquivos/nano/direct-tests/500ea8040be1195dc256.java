/* @test    @summary Test getByte method of UnsafeQualifiedDoubleFieldAccessorImpl class to ensure it throws IllegalArgumentException.    @run main UnsafeQualifiedDoubleFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetByteThrowsException();
    }

    private static void testGetByteThrowsException() {
        class Dummy {
        }
        try {
            // Create an instance of UnsafeQualifiedDoubleFieldAccessorImpl with dummy parameters
            Field dummyField = null; // Not used, as getByte always throws
            boolean isReadOnly = false;
            jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl(dummyField, isReadOnly);
            // Call getByte and expect IllegalArgumentException
            accessor.getByte(new Dummy());
            // If no exception thrown, fail the test
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
