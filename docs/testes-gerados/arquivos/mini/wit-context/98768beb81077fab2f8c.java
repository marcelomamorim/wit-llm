/*
 * @test
 * @summary Test UnsafeBooleanFieldAccessorImpl.getBoolean(Object) for correct behavior and exception
 * @run main UnsafeBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeBooleanFieldAccessorImpl;

public class UnsafeBooleanFieldAccessorImplWitupTest {

    static class TestClass {
        public boolean flag = true;
    }

    public static void main(String[] args) throws Exception {
        // Setup: get Field for boolean 'flag' in TestClass
        Field field = TestClass.class.getDeclaredField("flag");
        UnsafeBooleanFieldAccessorImpl accessor = new UnsafeBooleanFieldAccessorImpl(field);

        TestClass obj = new TestClass();

        // Test normal getBoolean returns the correct value
        boolean val = accessor.getBoolean(obj);
        if (val != obj.flag) {
            throw new AssertionError("getBoolean returned " + val + ", expected " + obj.flag);
        }

        // Test getBoolean throws IllegalArgumentException if obj is not instance of declaring class
        try {
            accessor.getBoolean(new Object());
            throw new AssertionError("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test getBoolean throws IllegalArgumentException if obj is null
        try {
            accessor.getBoolean(null);
            throw new AssertionError("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
