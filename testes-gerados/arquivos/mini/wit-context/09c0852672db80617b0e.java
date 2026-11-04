/*
 * @test
 * @summary Test UnsafeStaticShortFieldAccessorImpl.getByte throws IllegalArgumentException
 * @run main UnsafeStaticShortFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeStaticShortFieldAccessorImpl;

public class UnsafeStaticShortFieldAccessorImplWitupTest {
    public static void main(String[] args) throws Exception {
        // Use reflection to get a static short field for testing
        Field testField = TestClass.class.getDeclaredField("staticShortField");
        testField.setAccessible(true);

        UnsafeStaticShortFieldAccessorImpl accessor = new UnsafeStaticShortFieldAccessorImpl(testField);

        // Test that getByte throws IllegalArgumentException as expected
        try {
            accessor.getByte(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown by getByte");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static class TestClass {
        static short staticShortField = 123;
    }
}
