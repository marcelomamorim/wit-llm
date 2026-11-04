/*
 * @test
 * @summary Test UnsafeQualifiedBooleanFieldAccessorImpl.getBoolean(Object) method
 * @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    static class TestObj {
        volatile boolean flag = true;
    }

    public static void main(String[] args) throws Exception {
        TestObj obj = new TestObj();
        Field f = TestObj.class.getDeclaredField("flag");
        UnsafeQualifiedBooleanFieldAccessorImpl accessor = new UnsafeQualifiedBooleanFieldAccessorImpl(f, false);

        // Test getBoolean returns the correct boolean value
        boolean val = accessor.getBoolean(obj);
        if (val != true) {
            throw new AssertionError("Expected true but got " + val);
        }

        // Change the field and test again
        obj.flag = false;
        val = accessor.getBoolean(obj);
        if (val != false) {
            throw new AssertionError("Expected false but got " + val);
        }

        // Test that passing null throws IllegalArgumentException
        try {
            accessor.getBoolean(null);
            throw new AssertionError("Expected IllegalArgumentException for null argument");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test that passing an object of wrong type throws IllegalArgumentException
        try {
            accessor.getBoolean(new Object());
            throw new AssertionError("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
