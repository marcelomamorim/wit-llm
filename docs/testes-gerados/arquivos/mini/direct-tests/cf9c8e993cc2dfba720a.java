/*
 * @test
 * @summary Test that getBoolean(Object) throws IllegalArgumentException as expected
 * @run main UnsafeQualifiedStaticByteFieldAccessorImplWitupTest
 */

import jdk.internal.reflect.UnsafeQualifiedStaticByteFieldAccessorImpl;
import java.lang.reflect.Field;

public class UnsafeQualifiedStaticByteFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance for constructor (using reflection on a known field)
        Field field = Dummy.class.getDeclaredField("dummyByte");

        // Create instance with isReadOnly = false
        UnsafeQualifiedStaticByteFieldAccessorImpl accessor = new UnsafeQualifiedStaticByteFieldAccessorImpl(field, false);

        // Test that getBoolean throws IllegalArgumentException
        try {
            accessor.getBoolean(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static class Dummy {
        static byte dummyByte = 0;
    }
}
