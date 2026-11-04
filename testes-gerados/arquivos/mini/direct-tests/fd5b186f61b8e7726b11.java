/*
 * @test
 * @summary Test UnsafeObjectFieldAccessorImpl.get(Object) method behavior
 * @run main UnsafeObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;
import jdk.internal.reflect.UnsafeObjectFieldAccessorImpl;

public class UnsafeObjectFieldAccessorImplWitupTest {

    static class TestClass {
        Object value = "testValue";
    }

    public static void main(String[] args) throws Exception {
        TestClass testInstance = new TestClass();
        Field field = TestClass.class.getDeclaredField("value");
        UnsafeObjectFieldAccessorImpl accessor = new UnsafeObjectFieldAccessorImpl(field);

        // Test get returns the correct value
        Object result = accessor.get(testInstance);
        if (!"testValue".equals(result)) {
            throw new RuntimeException("get() did not return expected value");
        }

        // Test get throws IllegalArgumentException when passed null
        boolean threw = false;
        try {
            accessor.get(null);
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("get() did not throw IllegalArgumentException when passed null");
        }

        // Test get throws IllegalArgumentException when passed object of wrong type
        threw = false;
        try {
            accessor.get(new Object());
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("get() did not throw IllegalArgumentException when passed wrong object type");
        }
    }
}
