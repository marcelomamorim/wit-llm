/*
 * @test
 * @summary Test getDouble method throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest
 */

import jdk.internal.reflect.UnsafeQualifiedStaticBooleanFieldAccessorImpl;
import java.lang.reflect.Field;

public class UnsafeQualifiedStaticBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a dummy static boolean field to pass to the constructor
        Field field = Dummy.class.getDeclaredField("flag");
        UnsafeQualifiedStaticBooleanFieldAccessorImpl accessor = new UnsafeQualifiedStaticBooleanFieldAccessorImpl(field, false);

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
