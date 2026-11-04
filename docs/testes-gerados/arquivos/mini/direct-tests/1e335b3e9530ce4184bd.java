/*
 * @test
 * @summary Test that UnsafeStaticDoubleFieldAccessorImpl.getFloat(Object) always throws IllegalArgumentException
 * @run main UnsafeStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticDoubleFieldAccessorImpl;

public class UnsafeStaticDoubleFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy static double field to pass to the constructor
        Field field = Dummy.class.getDeclaredField("value");
        UnsafeStaticDoubleFieldAccessorImpl accessor = new UnsafeStaticDoubleFieldAccessorImpl(field);

        try {
            accessor.getFloat(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        try {
            accessor.getFloat(new Object());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static class Dummy {
        static double value = 0.0;
    }
}
