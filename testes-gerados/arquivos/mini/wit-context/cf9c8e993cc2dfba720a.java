/*
 * @test
 * @summary Test that UnsafeQualifiedStaticByteFieldAccessorImpl.getBoolean always throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticByteFieldAccessorImplWitupTest
 */

import jdk.internal.reflect.UnsafeQualifiedStaticByteFieldAccessorImpl;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class UnsafeQualifiedStaticByteFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy static byte field to pass to the constructor
        Field field = Dummy.class.getDeclaredField("dummyByte");
        UnsafeQualifiedStaticByteFieldAccessorImpl accessor = new UnsafeQualifiedStaticByteFieldAccessorImpl(field, false);

        // Test getBoolean throws IllegalArgumentException
        try {
            accessor.getBoolean(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getBoolean");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }

    static class Dummy {
        // static byte field to use for UnsafeQualifiedStaticByteFieldAccessorImpl
        static byte dummyByte = 0;
    }
}
