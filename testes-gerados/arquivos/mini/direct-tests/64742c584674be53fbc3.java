/*
 * @test
 * @summary Test that UnsafeStaticDoubleFieldAccessorImpl.getLong(Object) throws IllegalArgumentException
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
            accessor.getLong(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        static double value = 0.0;
    }
}
