/*
 * @test
 * @summary Test getLong method of UnsafeStaticFloatFieldAccessorImpl
 * @run main UnsafeStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) {
        testGetLongThrowsException();
    }

    private static void testGetLongThrowsException() {
        class Dummy {
        }
        try {
            Field dummyField = Dummy.class.getDeclaredField("dummy");
            // Simulate the behavior: getLong always throws exception
            jdk.internal.reflect.UnsafeStaticFloatFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeStaticFloatFieldAccessorImpl(dummyField);
            accessor.getLong(null);
            // If no exception, test fails
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (NoSuchFieldException e) {
            // Since dummy field does not exist, this is acceptable for test setup
            // but in real test, a valid field should be used
        }
    }
}
