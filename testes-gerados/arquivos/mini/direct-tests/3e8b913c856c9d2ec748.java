/*
 * @test
 * @summary Test that UnsafeStaticDoubleFieldAccessorImpl.getShort always throws IllegalArgumentException
 * @run main UnsafeStaticDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticDoubleFieldAccessorImpl;

public class UnsafeStaticDoubleFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance for constructor (using reflection)
        Field dummyField = Dummy.class.getDeclaredField("dummy");
        UnsafeStaticDoubleFieldAccessorImpl accessor = new UnsafeStaticDoubleFieldAccessorImpl(dummyField);

        boolean threw = false;
        try {
            accessor.getShort(null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }

        if (!threw) {
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getShort");
        }
    }

    static class Dummy {
        static double dummy = 0.0;
    }
}
