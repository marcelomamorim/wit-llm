/*
 * @test
 * @summary Test UnsafeStaticIntegerFieldAccessorImpl.getBoolean throws IllegalArgumentException
 * @run main UnsafeStaticIntegerFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticIntegerFieldAccessorImpl;

public class UnsafeStaticIntegerFieldAccessorImplWitupTest {
    static class Dummy {
        static int value = 42;
    }

    public static void main(String[] args) throws Exception {
        Field f = Dummy.class.getDeclaredField("value");
        UnsafeStaticIntegerFieldAccessorImpl accessor = new UnsafeStaticIntegerFieldAccessorImpl(f);

        try {
            accessor.getBoolean(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
