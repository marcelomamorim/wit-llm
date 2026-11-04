/*
 * @test
 * @summary Test that UnsafeDoubleFieldAccessorImpl.getShort always throws IllegalArgumentException
 * @run main UnsafeDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeDoubleFieldAccessorImpl;

public class UnsafeDoubleFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy Field instance via reflection to pass to constructor
        Field dummyField = Dummy.class.getDeclaredField("dummyDouble");
        UnsafeDoubleFieldAccessorImpl accessor = new UnsafeDoubleFieldAccessorImpl(dummyField);

        try {
            accessor.getShort(new Dummy());
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // Test passed
        }
    }

    static class Dummy {
        double dummyDouble = 0.0;
    }
}
