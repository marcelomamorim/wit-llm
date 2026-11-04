/*
 * @test
 * @summary Test MethodHandles.unreflectVarHandle(Field) for null argument and normal usage
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;

public class MethodHandlesWitupTest {
    public static void main(String[] args) throws Throwable {
        testNullField();
        testValidField();
    }

    private static void testNullField() {
        try {
            MethodHandles.unreflectVarHandle(null);
            throw new Error("Expected NullPointerException for null Field argument");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (IllegalAccessException e) {
            throw new Error("Unexpected IllegalAccessException for null Field", e);
        }
    }

    private static void testValidField() throws Exception {
        Field field = Sample.class.getDeclaredField("value");
        VarHandle vh = MethodHandles.unreflectVarHandle(field);
        if (vh == null) {
            throw new Error("VarHandle should not be null for valid Field");
        }
        // Test VarHandle get and set
        Sample sample = new Sample();
        int oldValue = (int) vh.get(sample);
        if (oldValue != 42) {
            throw new Error("Unexpected initial value from VarHandle get");
        }
        vh.set(sample, 100);
        int newValue = (int) vh.get(sample);
        if (newValue != 100) {
            throw new Error("VarHandle set did not update the field correctly");
        }
    }

    static class Sample {
        int value = 42;
    }
}
