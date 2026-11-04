/* @test    @summary Test getDouble method throws IllegalArgumentException as specified.    @run main UnsafeBooleanFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetDoubleThrowsException();
    }

    public static void testGetDoubleThrowsException() {
        try {
            // Create a dummy Field object; since the method does not use it,
            // it can be null or a mock, but here we instantiate a real Field via reflection.
            // For simplicity, use a dummy class with a boolean field.
            class Dummy {
                public boolean value;
            }
            Field field = Dummy.class.getDeclaredField("value");
            // Instantiate UnsafeBooleanFieldAccessorImpl with the field
            jdk.internal.reflect.UnsafeBooleanFieldAccessorImpl accessor =
                new jdk.internal.reflect.UnsafeBooleanFieldAccessorImpl(field);
            // Call getDouble, expecting IllegalArgumentException
            accessor.getDouble(new Dummy());
            // If no exception, fail the test
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Failed to access dummy field", e);
        }
    }
}
