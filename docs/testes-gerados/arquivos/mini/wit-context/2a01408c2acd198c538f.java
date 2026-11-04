/*
 * @test
 * @summary Test UnsafeStaticObjectFieldAccessorImpl.getDouble method throws expected IllegalArgumentException
 * @run main UnsafeStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticObjectFieldAccessorImpl;

public class UnsafeStaticObjectFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy static field to pass to the constructor
        Field dummyField = Dummy.class.getDeclaredField("staticField");
        UnsafeStaticObjectFieldAccessorImpl accessor = new UnsafeStaticObjectFieldAccessorImpl(dummyField);

        // The getDouble method always throws IllegalArgumentException
        try {
            accessor.getDouble(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getDouble");
        } catch (IllegalArgumentException expected) {
            // Expected exception caught, test passes
        }
    }

    static class Dummy {
        static Object staticField = new Object();
    }
}
