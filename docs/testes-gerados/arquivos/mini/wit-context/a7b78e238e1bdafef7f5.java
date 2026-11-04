/*
 * @test
 * @summary Test MethodHandles.unreflectGetter(Field) for null argument and normal usage
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

public class MethodHandlesWitupTest {
    public static void main(String[] args) throws Throwable {
        testUnreflectGetterNullField();
        testUnreflectGetterValidField();
    }

    private static void testUnreflectGetterNullField() {
        try {
            MethodHandles.unreflectGetter(null);
            throw new Error("Expected NullPointerException for null Field argument");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (IllegalAccessException e) {
            throw new Error("Unexpected IllegalAccessException", e);
        }
    }

    private static void testUnreflectGetterValidField() throws Throwable {
        Field field = Sample.class.getDeclaredField("value");
        MethodHandle mh = MethodHandles.unreflectGetter(field);
        Sample sample = new Sample(42);
        Object result = mh.invoke(sample);
        if (!(result instanceof Integer) || ((Integer) result).intValue() != 42) {
            throw new Error("MethodHandle did not return expected field value");
        }
    }

    static class Sample {
        private final int value;

        Sample(int value) {
            this.value = value;
        }
    }
}
