/*
 * @test
 * @summary Test UnsafeStaticBooleanFieldAccessorImpl.getInt throws IllegalArgumentException
 * @run main UnsafeStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticBooleanFieldAccessorImpl;

public class UnsafeStaticBooleanFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy static boolean field to pass to the constructor
        Field field = Dummy.class.getDeclaredField("flag");
        UnsafeStaticBooleanFieldAccessorImpl accessor = new UnsafeStaticBooleanFieldAccessorImpl(field);

        try {
            accessor.getInt(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        static boolean flag = true;
    }
}
