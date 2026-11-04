/* @test    @summary Test getFloat method throws IllegalArgumentException as expected    @run main UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetFloatThrowsException();
    }

    private static void testGetFloatThrowsException() {
        try {
            // Create an anonymous subclass to access the method
            jdk.internal.reflect.UnsafeQualifiedStaticBooleanFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeQualifiedStaticBooleanFieldAccessorImpl(null, false) {
                @Override
                public float getFloat(Object obj) {
                    return super.getFloat(obj);
                }
            };
            // Call getFloat, expecting IllegalArgumentException
            accessor.getFloat(null);
            // If no exception, test fails
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t, t);
        }
    }
}
