/*
 * @test
 * @summary Test getDouble method of UnsafeByteFieldAccessorImpl for exception path
 * @run main UnsafeByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeByteFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetDoubleWithInvalidObject();
    }

    private static void testGetDoubleWithInvalidObject() throws Exception {
        // Setup: create a dummy field and accessor
        class Dummy {
            public byte value;
        }
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getDeclaredField("value");
        // Simulate an accessor that would throw IllegalArgumentException
        // when the object is not compatible
        jdk.internal.reflect.UnsafeByteFieldAccessorImpl accessor =
            new jdk.internal.reflect.UnsafeByteFieldAccessorImpl(field);
        // Test: pass an incompatible object to trigger exception
        try {
            accessor.getDouble(new Object());
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("Caught expected IllegalArgumentException.");
        }
    }
}
