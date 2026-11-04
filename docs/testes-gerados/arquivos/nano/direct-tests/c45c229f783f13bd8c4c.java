/* @test    @summary Test getBoolean method of UnsafeQualifiedDoubleFieldAccessorImpl to ensure it throws the expected exception.    @run main UnsafeQualifiedDoubleFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedDoubleFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetBooleanThrowsException();
    }

    private static void testGetBooleanThrowsException() {
        class Dummy {
        }
        try {
            // Create an instance of UnsafeQualifiedDoubleFieldAccessorImpl with dummy parameters
            Field dummyField = null; // Not used in this test, so can be null
            boolean isReadOnly = false;
            jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl accessor =
                new jdk.internal.reflect.UnsafeQualifiedDoubleFieldAccessorImpl(dummyField, isReadOnly);
            // Call getBoolean and expect an IllegalArgumentException
            accessor.getBoolean(new Object());
            // If no exception is thrown, the test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception caught, test passes
            System.out.println("getBoolean() threw IllegalArgumentException as expected.");
        } catch (Throwable t) {
            // Unexpected exception, test fails
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
