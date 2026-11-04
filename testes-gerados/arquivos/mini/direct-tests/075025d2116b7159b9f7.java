/*
 * @test
 * @summary Test that getShort(Object) throws IllegalArgumentException as expected
 * @run main UnsafeStaticBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticBooleanFieldAccessorImpl;

public class UnsafeStaticBooleanFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy static boolean field to pass to constructor
        Field field = Dummy.class.getDeclaredField("flag");
        UnsafeStaticBooleanFieldAccessorImpl accessor = new UnsafeStaticBooleanFieldAccessorImpl(field);

        try {
            accessor.getShort(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        static boolean flag = true;
    }
}
