/*
 * @test
 * @summary Test UnsafeStaticByteFieldAccessorImpl.getBoolean throws expected IllegalArgumentException
 * @run main UnsafeStaticByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticByteFieldAccessorImpl;

public class UnsafeStaticByteFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy static byte field to pass to constructor
        Field field = Dummy.class.getDeclaredField("dummyByte");
        UnsafeStaticByteFieldAccessorImpl accessor = new UnsafeStaticByteFieldAccessorImpl(field);

        boolean thrown = false;
        try {
            accessor.getBoolean(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getBoolean");
        }
    }

    static class Dummy {
        static byte dummyByte = 0;
    }
}
