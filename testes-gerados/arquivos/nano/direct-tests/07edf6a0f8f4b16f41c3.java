/*
 * @test
 * @summary Test unreflectSetter method in MethodHandles class.
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;
import java.lang.IllegalAccessException;
import java.lang.invoke.MethodHandles;

public class MethodHandlesWitupTest {

    public static void main(String[] args) throws Exception {
        testUnreflectSetterWithAccessibleField();
        testUnreflectSetterWithPrivateField();
        testUnreflectSetterWithFinalField();
    }

    private static void testUnreflectSetterWithAccessibleField() throws Exception {
        class TestClass {
            public int value;
        }
        Field field = TestClass.class.getField("value");
        MethodHandle setter = MethodHandles.unreflectSetter(field);
        TestClass obj = new TestClass();
        setter.invoke(obj, 42);
        assert obj.value == 42 : "Setter did not set the value correctly";
    }

    private static void testUnreflectSetterWithPrivateField() throws Exception {
        class TestClass {
            private int value;
        }
        Field field = TestClass.class.getDeclaredField("value");
        try {
            MethodHandle setter = MethodHandles.unreflectSetter(field);
            TestClass obj = new TestClass();
            setter.invoke(obj, 99);
            assert false : "Expected IllegalAccessException for private field";
        } catch (IllegalAccessException e) {
            // Expected exception
        }
    }

    private static void testUnreflectSetterWithFinalField() throws Exception {
        class TestClass {
            public final int value = 0;
        }
        Field field = TestClass.class.getField("value");
        try {
            MethodHandle setter = MethodHandles.unreflectSetter(field);
            TestClass obj = new TestClass();
            setter.invoke(obj, 123);
            assert false : "Expected IllegalAccessException for final field";
        } catch (IllegalAccessException e) {
            // Expected exception
        }
    }
}
