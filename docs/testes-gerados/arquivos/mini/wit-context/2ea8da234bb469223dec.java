/*
 * @test
 * @summary Test getInt(Object) method of UnsafeQualifiedByteFieldAccessorImpl
 * @run main UnsafeQualifiedByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedByteFieldAccessorImpl;

public class UnsafeQualifiedByteFieldAccessorImplWitupTest {

    static class TestObj {
        public byte b = 42;
    }

    public static void main(String[] args) throws Exception {
        TestObj to = new TestObj();
        Field f = TestObj.class.getField("b");

        UnsafeQualifiedByteFieldAccessorImpl accessor = new UnsafeQualifiedByteFieldAccessorImpl(f, false);

        // Test getInt returns the byte value as int
        int val = accessor.getInt(to);
        if (val != 42) {
            throw new RuntimeException("getInt returned " + val + ", expected 42");
        }

        // Test getInt throws IllegalArgumentException if object is not instance of declaring class
        boolean threwIAE = false;
        try {
            accessor.getInt(new Object());
        } catch (IllegalArgumentException iae) {
            threwIAE = true;
        }
        if (!threwIAE) {
            throw new RuntimeException("getInt did not throw IllegalArgumentException for wrong object type");
        }
    }
}
