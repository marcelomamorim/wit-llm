/*
 * @test
 * @summary Test unreflectGetter method in MethodHandles class.
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;
import java.lang.IllegalAccessException;
import java.util.Objects;

public class MethodHandlesWitupTest {

    public static void main(String[] args) throws Exception {
        testUnreflectGetterWithAccessibleField();
        testUnreflectGetterWithInaccessibleField();
    }

    private static void testUnreflectGetterWithAccessibleField() throws Exception {
        class TestClass {
            public int value = 42;
        }
        Field field = TestClass.class.getField("value");
        MethodHandle handle = MethodHandles.unreflectGetter(field);
        TestClass obj = new TestClass();
        Object result = handle.invoke(obj);
        if (!Objects.equals(result, 42)) {
            throw new AssertionError("Expected 42, got " + result);
        }
    }

    private static void testUnreflectGetterWithInaccessibleField() throws Exception {
        class TestClass {
            private int secret = 99;
        }
        Field field = TestClass.class.getDeclaredField("secret");
        try {
            MethodHandle handle = MethodHandles.unreflectGetter(field);
            throw new AssertionError("Expected IllegalAccessException to be thrown");
        } catch (IllegalAccessException e) {
            // expected
        }
    }
}
