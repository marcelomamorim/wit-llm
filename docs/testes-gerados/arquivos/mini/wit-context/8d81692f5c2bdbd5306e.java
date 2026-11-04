/*
 * @test
 * @summary Test UnsafeQualifiedBooleanFieldAccessorImpl.getBoolean(Object) for correct behavior and IllegalArgumentException on invalid object
 * @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeQualifiedBooleanFieldAccessorImpl;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    static class TestClass {
        public volatile boolean testField = true;
    }

    public static void main(String[] args) throws Exception {
        // Obtain Field instance for TestClass.testField
        Field field = TestClass.class.getDeclaredField("testField");

        // Create UnsafeQualifiedBooleanFieldAccessorImpl instance via reflection
        // The constructor is package-private, so we use reflection
        UnsafeQualifiedBooleanFieldAccessorImpl accessor = createAccessor(field, false);

        TestClass obj = new TestClass();

        // Test getBoolean returns the correct value
        boolean val = accessor.getBoolean(obj);
        if (val != obj.testField) {
            throw new AssertionError("getBoolean returned wrong value: expected " + obj.testField + " but got " + val);
        }

        // Test getBoolean throws IllegalArgumentException when passed an object of wrong type
        try {
            accessor.getBoolean(new Object());
            throw new AssertionError("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // Test getBoolean throws IllegalArgumentException when passed null
        try {
            accessor.getBoolean(null);
            throw new AssertionError("Expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static UnsafeQualifiedBooleanFieldAccessorImpl createAccessor(Field field, boolean isReadOnly) throws Exception {
        java.lang.reflect.Constructor<UnsafeQualifiedBooleanFieldAccessorImpl> ctor = UnsafeQualifiedBooleanFieldAccessorImpl.class.getDeclaredConstructor(Field.class, boolean.class);
        ctor.setAccessible(true);
        return ctor.newInstance(field, isReadOnly);
    }
}
