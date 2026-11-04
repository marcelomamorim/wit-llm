/*
 * @test
 * @summary Test getInt method of UnsafeQualifiedByteFieldAccessorImpl
 * @run main UnsafeQualifiedByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedByteFieldAccessorImpl;

public class UnsafeQualifiedByteFieldAccessorImplWitupTest {

    static class TestObj {
        volatile byte b = 42;
    }

    public static void main(String[] args) throws Exception {
        TestObj obj = new TestObj();
        Field field = TestObj.class.getDeclaredField("b");
        UnsafeQualifiedByteFieldAccessorImpl accessor = new UnsafeQualifiedByteFieldAccessorImpl(field, false);

        int val = accessor.getInt(obj);
        if (val != 42) {
            throw new RuntimeException("Expected 42 but got " + val);
        }

        // Test with null object to check IllegalArgumentException
        try {
            accessor.getInt(null);
            throw new RuntimeException("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }
}
