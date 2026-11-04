/*
 * @test
 * @summary Test UnsafeByteFieldAccessorImpl.getChar throws IllegalArgumentException
 * @run main UnsafeByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeByteFieldAccessorImpl;

public class UnsafeByteFieldAccessorImplWitupTest {
    public static void main(String[] args) {
        try {
            // Create a dummy field to pass to constructor
            Field dummyField = Dummy.class.getDeclaredField("dummyByte");
            UnsafeByteFieldAccessorImpl accessor = new UnsafeByteFieldAccessorImpl(dummyField);

            // Call getChar and expect IllegalArgumentException
            try {
                accessor.getChar(new Dummy());
                throw new RuntimeException("Expected IllegalArgumentException not thrown");
            } catch (IllegalArgumentException e) {
                // Expected exception
            }

            System.out.println("Test passed");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed", e);
        }
    }

    static class Dummy {
        public byte dummyByte = 0;
    }
}
