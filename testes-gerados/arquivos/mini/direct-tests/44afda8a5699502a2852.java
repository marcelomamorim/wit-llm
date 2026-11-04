/*
 * @test
 * @summary Test that getBoolean throws IllegalArgumentException as expected
 * @run main UnsafeStaticShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticShortFieldAccessorImpl;

public class UnsafeStaticShortFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy static short field to pass to the constructor
        Field field = Dummy.class.getDeclaredField("dummyShort");
        UnsafeStaticShortFieldAccessorImpl accessor = new UnsafeStaticShortFieldAccessorImpl(field);

        try {
            accessor.getBoolean(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // Test passed
        }
    }

    static class Dummy {
        static short dummyShort = 0;
    }
}
