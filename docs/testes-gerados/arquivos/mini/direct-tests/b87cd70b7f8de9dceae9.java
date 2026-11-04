/*
 * @test
 * @summary Test that UnsafeStaticShortFieldAccessorImpl.getChar throws IllegalArgumentException
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
            accessor.getChar(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        static short dummyShort = 1;
    }
}
