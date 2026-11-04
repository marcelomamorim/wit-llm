/*
 * @test
 * @summary Tests for MethodHandles.unreflectGetter(Field f)
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

public class MethodHandlesWitupTest {

    public static void main(String[] args) throws Throwable {
        testUnreflectGetterWithNull();
        testUnreflectGetterWithValidField();
    }

    static void testUnreflectGetterWithNull() {
        try {
            MethodHandle mh = MethodHandles.unreflectGetter(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // expected
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }

    static void testUnreflectGetterWithValidField() throws Throwable {
        class TestClass {
            public int value = 42;
        }
        Field field = TestClass.class.getField("value");
        MethodHandle mh = MethodHandles.unreflectGetter(field);
        TestClass obj = new TestClass();
        Object result = mh.invoke(obj);
        if (result.equals(42)) {
            System.out.println("testUnreflectGetterWithValidField passed");
        } else {
            System.err.println("Unexpected result: " + result);
            System.exit(1);
        }
    }
}
