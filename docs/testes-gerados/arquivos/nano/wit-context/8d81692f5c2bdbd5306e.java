/*
 * @test
 * @summary Test for getBoolean method in UnsafeQualifiedBooleanFieldAccessorImpl
 * @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    public static void main(String[] args) throws Exception {
        testGetBooleanWithValidObject();
        testGetBooleanWithInvalidObject();
    }

    static void testGetBooleanWithValidObject() throws Exception {
        class Dummy {
            public boolean value = true;
        }
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getDeclaredField("value");
        field.setAccessible(true);
        // Simulate the internal behavior
        jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl accessor =
            new jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl(field, false);
        boolean result = accessor.getBoolean(dummy);
        if (result != true) {
            throw new AssertionError("Expected true, got " + result);
        }
    }

    static void testGetBooleanWithInvalidObject() throws Exception {
        class Dummy {
            public boolean value = false;
        }
        Dummy dummy = new Dummy();
        Field field = Dummy.class.getDeclaredField("value");
        field.setAccessible(true);
        jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl accessor =
            new jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl(field, false);
        try {
            // Passing an object of incompatible class
            accessor.getBoolean("not a dummy");
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
