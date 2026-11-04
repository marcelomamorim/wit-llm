/*
 * @test
 * @summary Test getChar method throws IllegalArgumentException as expected
 * @run main UnsafeQualifiedStaticShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedStaticShortFieldAccessorImpl;

public class UnsafeQualifiedStaticShortFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        // Create a dummy static short field to pass to constructor
        Field field = Dummy.class.getDeclaredField("dummyShort");
        UnsafeQualifiedStaticShortFieldAccessorImpl accessor = new UnsafeQualifiedStaticShortFieldAccessorImpl(field, false);

        try {
            accessor.getChar(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        static short dummyShort = 0;
    }
}
