/*
 * @test
 * @summary Test UnsafeQualifiedStaticShortFieldAccessorImpl.getByte throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticShortFieldAccessorImpl;

public class UnsafeQualifiedStaticShortFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // We cannot instantiate UnsafeQualifiedStaticShortFieldAccessorImpl directly because constructor is package-private and requires Field and boolean
        // Instead, use reflection to get a Field instance from a known class and create the accessor
        Field field = Dummy.class.getDeclaredField("dummyShort");
        UnsafeQualifiedStaticShortFieldAccessorImpl accessor = new UnsafeQualifiedStaticShortFieldAccessorImpl(field, false);

        // Test getByte(Object) throws IllegalArgumentException as per current implementation
        try {
            accessor.getByte(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getByte");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }

    // Dummy class with a static short field to obtain a Field instance
    static class Dummy {
        static short dummyShort = 123;
    }
}
