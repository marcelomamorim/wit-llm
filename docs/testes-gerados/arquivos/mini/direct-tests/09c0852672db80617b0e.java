/*
 * @test
 * @summary Test UnsafeStaticShortFieldAccessorImpl.getByte throws IllegalArgumentException
 * @run main UnsafeStaticShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticShortFieldAccessorImpl;

public class UnsafeStaticShortFieldAccessorImplWitupTest {
    public static void main(String[] args) {
        try {
            // Create a dummy Field instance via reflection to pass to constructor
            Field field = UnsafeStaticShortFieldAccessorImplWitupTest.class.getDeclaredField("dummyField");
            UnsafeStaticShortFieldAccessorImpl accessor = new UnsafeStaticShortFieldAccessorImpl(field);

            try {
                accessor.getByte(null);
                throw new RuntimeException("Expected IllegalArgumentException not thrown");
            } catch (IllegalArgumentException e) {
                // Expected exception
            }

            System.out.println("Test passed");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Test setup failed: dummyField not found", e);
        }
    }

    @SuppressWarnings("unused")
    private static short dummyField = 0;
}
