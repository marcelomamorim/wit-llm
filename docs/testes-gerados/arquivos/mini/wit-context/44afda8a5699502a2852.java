/*
 * @test
 * @summary Test for jdk.internal.reflect.UnsafeStaticShortFieldAccessorImpl.getBoolean(Object) to verify it throws IllegalArgumentException
 * @run main UnsafeStaticShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticShortFieldAccessorImpl;

public class UnsafeStaticShortFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = Dummy.class.getDeclaredField("dummyShort");
        UnsafeStaticShortFieldAccessorImpl accessor = new UnsafeStaticShortFieldAccessorImpl(dummyField);

        boolean thrown = false;
        try {
            accessor.getBoolean(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException was not thrown by getBoolean");
        }
    }

    static class Dummy {
        public static short dummyShort = 0;
    }
}
