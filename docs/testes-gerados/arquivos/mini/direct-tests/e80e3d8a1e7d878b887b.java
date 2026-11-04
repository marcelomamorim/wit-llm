/*
 * @test
 * @summary Test that UnsafeStaticDoubleFieldAccessorImpl.getInt(Object) throws IllegalArgumentException
 * @run main UnsafeStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticDoubleFieldAccessorImpl;

public class UnsafeStaticDoubleFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy static double field to pass to the constructor
        Field field = Dummy.class.getDeclaredField("staticDoubleField");
        UnsafeStaticDoubleFieldAccessorImpl accessor = new UnsafeStaticDoubleFieldAccessorImpl(field);

        try {
            accessor.getInt(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // Test passed
        }
    }

    static class Dummy {
        static double staticDoubleField = 0.0;
    }
}
