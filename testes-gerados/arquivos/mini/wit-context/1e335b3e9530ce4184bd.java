/*
 * @test
 * @summary Test that UnsafeStaticDoubleFieldAccessorImpl.getFloat(Object) always throws IllegalArgumentException
 * @run main UnsafeStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticDoubleFieldAccessorImpl;

public class UnsafeStaticDoubleFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Use reflection to get a static double field for testing
        Field field = TestClass.class.getDeclaredField("staticDoubleField");
        UnsafeStaticDoubleFieldAccessorImpl accessor = new UnsafeStaticDoubleFieldAccessorImpl(field);

        // The getFloat method should always throw IllegalArgumentException
        try {
            accessor.getFloat(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getFloat(null)");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        try {
            accessor.getFloat(new Object());
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getFloat(non-null)");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    // A dummy class with a static double field for testing
    static class TestClass {
        static double staticDoubleField = 123.456;
    }
}
