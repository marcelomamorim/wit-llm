/*
 * @test
 * @summary Test UnsafeStaticByteFieldAccessorImpl.getChar throws IllegalArgumentException
 * @run main UnsafeStaticByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticByteFieldAccessorImpl;

public class UnsafeStaticByteFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy static byte field to pass to the constructor
        Field field = Dummy.class.getDeclaredField("dummyByte");
        UnsafeStaticByteFieldAccessorImpl accessor = new UnsafeStaticByteFieldAccessorImpl(field);

        try {
            accessor.getChar(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        static byte dummyByte = 0;
    }
}
