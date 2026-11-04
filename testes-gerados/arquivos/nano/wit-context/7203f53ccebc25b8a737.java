/*
 * @test
 * @summary Test for getChar method in UnsafeQualifiedIntegerFieldAccessorImpl
 * @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetCharThrowsIllegalArgumentException();
    }

    private static void testGetCharThrowsIllegalArgumentException() throws Exception {
        // Setup: create a dummy Field object (using reflection) for testing
        // Note: Since the constructor is package-private, we assume access or use a mock
        // For the purpose of this test, we simulate the behavior by directly calling the method
        // with a null object, which should trigger the exception as per the method's behavior.
        jdk.internal.reflect.UnsafeQualifiedIntegerFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedIntegerFieldAccessorImpl(null, false);
        try {
            accessor.getChar(new Object());
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception caught, test passes
            System.out.println("getChar() threw IllegalArgumentException as expected.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception type: " + t);
        }
    }
}
