/*
 * @test
 * @summary Test UnsafeQualifiedStaticBooleanFieldAccessorImpl.getInt throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticBooleanFieldAccessorImpl;

public class UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Use reflection to get a boolean static field for test
        Field field = Boolean.class.getDeclaredField("TRUE"); // static final Boolean TRUE

        // Construct UnsafeQualifiedStaticBooleanFieldAccessorImpl instance
        UnsafeQualifiedStaticBooleanFieldAccessorImpl accessor = new UnsafeQualifiedStaticBooleanFieldAccessorImpl(field, false);

        // Test getInt throws IllegalArgumentException
        try {
            accessor.getInt(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getInt");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
