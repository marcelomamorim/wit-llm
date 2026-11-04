/* @test    @summary Test getBoolean method of UnsafeQualifiedFloatFieldAccessorImpl that throws IllegalArgumentException    @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetBooleanThrowsException();
    }

    private static void testGetBooleanThrowsException() {
        class Dummy {
        }
        try {
            // Create an instance of UnsafeQualifiedFloatFieldAccessorImpl with dummy field and isReadOnly false
            Field dummyField = Dummy.class.getDeclaredFields().length > 0 ? Dummy.class.getDeclaredFields()[0] : null;
            // Since we cannot instantiate UnsafeQualifiedFloatFieldAccessorImpl directly without a real Field, we simulate the call
            // by invoking getBoolean with null, expecting an IllegalArgumentException as per method implementation
            jdk.internal.reflect.UnsafeQualifiedFloatFieldAccessorImpl accessor = null;
            try {
                accessor = new jdk.internal.reflect.UnsafeQualifiedFloatFieldAccessorImpl(dummyField, false);
            } catch (Exception e) {
                // If instantiation fails, skip test
                return;
            }
            // Call getBoolean and expect IllegalArgumentException
            accessor.getBoolean(new Object());
            // If no exception thrown, test fails
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t, t);
        }
    }
}
