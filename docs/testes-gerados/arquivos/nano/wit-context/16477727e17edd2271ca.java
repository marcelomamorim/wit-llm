/*
 * @test
 * @summary Test for UnsafeByteFieldAccessorImpl.getLong method, focusing on exception path.
 * @run main UnsafeByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeByteFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetLongWithInvalidObject();
    }

    private static void testGetLongWithInvalidObject() throws Exception {
        // Reflection to obtain the field; assuming a dummy class with a byte field
        class Dummy {
            public byte value;
        }
        Dummy dummyInstance = new Dummy();
        Field field = Dummy.class.getDeclaredField("value");
        // Create an instance of UnsafeByteFieldAccessorImpl with the field
        jdk.internal.reflect.UnsafeByteFieldAccessorImpl accessor = new jdk.internal.reflect.UnsafeByteFieldAccessorImpl(field);
        Object invalidObject = new Object();
        try {
            accessor.getLong(invalidObject);
            // If no exception, test fails
            System.err.println("Expected IllegalArgumentException was not thrown.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception; test passes
            System.out.println("Caught expected IllegalArgumentException.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
