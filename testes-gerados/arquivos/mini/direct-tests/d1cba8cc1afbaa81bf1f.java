/*
 * @test
 * @summary Test UnsafeStaticIntegerFieldAccessorImpl.getChar throws IllegalArgumentException
 * @run main UnsafeStaticIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticIntegerFieldAccessorImpl;

public class UnsafeStaticIntegerFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy static int field to pass to the constructor
        Field field = Dummy.class.getDeclaredField("dummyInt");
        UnsafeStaticIntegerFieldAccessorImpl accessor = new UnsafeStaticIntegerFieldAccessorImpl(field);

        try {
            accessor.getChar(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        static int dummyInt = 42;
    }
}
