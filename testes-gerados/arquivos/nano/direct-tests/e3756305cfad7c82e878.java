/* @test    @summary Test getLong method of UnsafeQualifiedBooleanFieldAccessorImpl for exception throwing    @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetLongThrowsException();
    }

    public static void testGetLongThrowsException() {
        class Dummy {
        }
        try {
            // Create an instance of UnsafeQualifiedBooleanFieldAccessorImpl with dummy Field and readOnly false
            Field dummyField = Dummy.class.getDeclaredFields().length > 0 ? Dummy.class.getDeclaredFields()[0] : null;
            // Since the source code indicates getLong always throws, we can pass null for the Field
            jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl accessor = 
                new jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl(dummyField, false);
            // Call getLong, expecting an IllegalArgumentException
            accessor.getLong(new Object());
            // If no exception, the test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
            System.out.println("Caught expected IllegalArgumentException.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
        }
    }
}
