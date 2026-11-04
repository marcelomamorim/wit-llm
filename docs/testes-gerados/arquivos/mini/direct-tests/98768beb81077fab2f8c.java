/*
 * @test
 * @summary Test UnsafeBooleanFieldAccessorImpl.getBoolean(Object) method
 * @run main UnsafeBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeBooleanFieldAccessorImpl;

public class UnsafeBooleanFieldAccessorImplWitupTest {

    static class TestClass {
        public boolean flag = true;
    }

    public static void main(String[] args) throws Exception {
        TestClass obj = new TestClass();
        Field field = TestClass.class.getField("flag");
        UnsafeBooleanFieldAccessorImpl accessor = new UnsafeBooleanFieldAccessorImpl(field);

        // Test getBoolean returns the correct boolean value
        boolean value = accessor.getBoolean(obj);
        if (value != obj.flag) {
            throw new AssertionError("Expected " + obj.flag + " but got " + value);
        }

        // Test getBoolean throws IllegalArgumentException when null is passed
        try {
            accessor.getBoolean(null);
            throw new AssertionError("Expected IllegalArgumentException for null argument");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test getBoolean throws IllegalArgumentException when wrong object type is passed
        try {
            accessor.getBoolean(new Object());
            throw new AssertionError("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
