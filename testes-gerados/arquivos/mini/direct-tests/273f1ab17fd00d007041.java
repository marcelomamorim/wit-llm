/*
 * @test
 * @summary Test UnsafeStaticBooleanFieldAccessorImpl.getDouble throws IllegalArgumentException
 * @run main UnsafeStaticBooleanFieldAccessorImplWitupTest
 */

import jdk.internal.reflect.UnsafeStaticBooleanFieldAccessorImpl;
import java.lang.reflect.Field;

public class UnsafeStaticBooleanFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy static boolean field to pass to the constructor
        Field field = Dummy.class.getDeclaredField("flag");
        UnsafeStaticBooleanFieldAccessorImpl accessor = new UnsafeStaticBooleanFieldAccessorImpl(field);

        try {
            accessor.getDouble(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        static boolean flag = true;
    }
}
