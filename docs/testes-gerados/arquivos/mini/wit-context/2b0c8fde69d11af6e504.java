/*
 * @test
 * @summary Test UnsafeQualifiedStaticBooleanFieldAccessorImpl.getByte throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticBooleanFieldAccessorImpl;

public class UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Use reflection to get a boolean static field to create the accessor
        Field field = Boolean.class.getDeclaredField("TRUE"); // static final Boolean TRUE
        // The field is final and static, but we only need to test getByte throws

        UnsafeQualifiedStaticBooleanFieldAccessorImpl accessor = new UnsafeQualifiedStaticBooleanFieldAccessorImpl(field, false);

        try {
            accessor.getByte(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getByte");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
