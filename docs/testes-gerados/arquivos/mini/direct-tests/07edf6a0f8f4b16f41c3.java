/*
 * @test
 * @summary Test MethodHandles.unreflectSetter(Field) behavior
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

public class MethodHandlesWitupTest {
    public static void main(String[] args) throws Throwable {
        testUnreflectSetterOnPublicField();
        testUnreflectSetterOnFinalField();
    }

    static void testUnreflectSetterOnPublicField() throws Throwable {
        Field field = Sample.class.getField("value");
        MethodHandle mh = MethodHandles.lookup().unreflectSetter(field);
        Sample sample = new Sample();
        mh.invokeExact(sample, 42);
        if (sample.value != 42) {
            throw new AssertionError("Setter did not update the field value");
        }
    }

    static void testUnreflectSetterOnFinalField() {
        try {
            Field field = Sample.class.getField("finalValue");
            MethodHandles.lookup().unreflectSetter(field);
            throw new AssertionError("Expected IllegalAccessException for final field setter");
        } catch (IllegalAccessException e) {
            // Expected: cannot create setter for final field
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    public static class Sample {
        public int value = 0;
        public final int finalValue = 10;
    }
}
