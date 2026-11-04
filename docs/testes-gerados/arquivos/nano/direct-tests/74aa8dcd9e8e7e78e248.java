/* @test    @summary Test getInt method of UnsafeFloatFieldAccessorImpl for exception throwing    @run main UnsafeFloatFieldAccessorImplWitupTest */

import java.lang.reflect.Field;

public class UnsafeFloatFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetIntThrowsException();
    }

    public static void testGetIntThrowsException() throws Exception {
        class Dummy {
        }
        Field dummyField = Dummy.class.getDeclaredField("dummy");
        dummyField.setAccessible(true);
        jdk.internal.reflect.UnsafeFloatFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeFloatFieldAccessorImpl(dummyField);
        try {
            accessor.getInt(new Object());
            throw new AssertionError("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
