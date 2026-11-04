/*
 * @test
 * @summary Test UnsafeByteFieldAccessorImpl.getInt(Object) for correct behavior and exception
 * @run main UnsafeByteFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeByteFieldAccessorImpl;

public class UnsafeByteFieldAccessorImplWitupTest {

    static class TestClass {
        public byte b = 42;
    }

    public static void main(String[] args) throws Exception {
        TestClass testObj = new TestClass();
        Field field = TestClass.class.getField("b");
        UnsafeByteFieldAccessorImpl accessor = new UnsafeByteFieldAccessorImpl(field);

        // Test getInt returns the byte value as int
        int intValue = accessor.getInt(testObj);
        if (intValue != 42) {
            throw new RuntimeException("getInt returned " + intValue + ", expected 42");
        }

        // Test getInt throws IllegalArgumentException if object is not instance of declaring class
        boolean threwIAE = false;
        try {
            accessor.getInt(new Object());
        } catch (IllegalArgumentException e) {
            threwIAE = true;
        }
        if (!threwIAE) {
            throw new RuntimeException("getInt did not throw IllegalArgumentException for wrong object type");
        }
    }
}
