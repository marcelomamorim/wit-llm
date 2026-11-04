/*
 * @test
 * @summary Test MethodHandles.unreflectSetter(Field) for null argument and normal usage
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;

public class MethodHandlesWitupTest {
    public static void main(String[] args) throws Throwable {
        testUnreflectSetterNullField();
        testUnreflectSetterValidField();
    }

    private static void testUnreflectSetterNullField() {
        try {
            MethodHandles.unreflectSetter(null);
            throw new AssertionError("Expected NullPointerException for null Field argument");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (IllegalAccessException e) {
            throw new AssertionError("Unexpected IllegalAccessException for null Field", e);
        }
    }

    private static void testUnreflectSetterValidField() throws Throwable {
        Field field = Sample.class.getDeclaredField("value");
        MethodHandle setter = MethodHandles.unreflectSetter(field);
        Sample sample = new Sample();
        setter.invokeExact(sample, 42);
        if (sample.value != 42) {
            throw new AssertionError("Setter did not set the field correctly");
        }
    }

    static class Sample {
        int value = 0;
    }
}
