/*
 * @test
 * @summary Test getChar method of UnsafeQualifiedLongFieldAccessorImpl
 * @run main UnsafeQualifiedLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedLongFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetCharThrowsException();
    }

    public static void testGetCharThrowsException() {
        try {
            // Create an instance of UnsafeQualifiedLongFieldAccessorImpl with dummy values
            Field dummyField = null; // Not used in test, but required for constructor
            boolean isReadOnly = false;
            jdk.internal.reflect.UnsafeQualifiedLongFieldAccessorImpl accessor =
                new jdk.internal.reflect.UnsafeQualifiedLongFieldAccessorImpl(dummyField, isReadOnly);
            // Call getChar, expecting an IllegalArgumentException
            accessor.getChar(new Object());
            // If no exception, test fails
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t, t);
        }
    }
}
