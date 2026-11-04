/*
 * @test
 * @summary Test UnsafeStaticObjectFieldAccessorImpl.getChar method throws IllegalArgumentException
 * @run main UnsafeStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticObjectFieldAccessorImpl;

public class UnsafeStaticObjectFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy static field to pass to the constructor
        Field dummyField = Dummy.class.getDeclaredField("staticField");
        UnsafeStaticObjectFieldAccessorImpl accessor = new UnsafeStaticObjectFieldAccessorImpl(dummyField);

        try {
            accessor.getChar(null);
            throw new RuntimeException("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class Dummy {
        static Object staticField = new Object();
    }
}
