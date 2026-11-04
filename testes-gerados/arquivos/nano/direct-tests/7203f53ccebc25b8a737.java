/* @test    @summary Test getChar method of UnsafeQualifiedIntegerFieldAccessorImpl that always throws IllegalArgumentException    @run main UnsafeQualifiedIntegerFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedIntegerFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetCharThrowsException();
    }

    private static void testGetCharThrowsException() {
        try {
            // Create a dummy Field object (null) as the method does not use it directly in this test
            Field dummyField = null;
            // Instantiate the class with dummy data
            jdk.internal.reflect.UnsafeQualifiedIntegerFieldAccessorImpl accessor = 
                new jdk.internal.reflect.UnsafeQualifiedIntegerFieldAccessorImpl(dummyField, false);
            // Call getChar, expecting an IllegalArgumentException
            accessor.getChar(new Object());
            // If no exception, the test fails
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
