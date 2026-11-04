/*
 * @test
 * @summary Test MethodHandles.unreflectVarHandle(Field) for basic correctness and exceptions
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;

public class MethodHandlesWitupTest {
    public static void main(String[] args) throws Throwable {
        testUnreflectVarHandleOnValidField();
        testUnreflectVarHandleOnStaticField();
        testUnreflectVarHandleOnPrivateField();
    }

    private static void testUnreflectVarHandleOnValidField() throws Exception {
        Field field = Sample.class.getDeclaredField("value");
        VarHandle vh = MethodHandles.unreflectVarHandle(field);
        if (vh == null) {
            throw new AssertionError("VarHandle should not be null for valid field");
        }
        // Test VarHandle get and set
        Sample sample = new Sample();
        vh.set(sample, 42);
        int val = (int) vh.get(sample);
        if (val != 42) {
            throw new AssertionError("VarHandle get/set failed, expected 42 but got " + val);
        }
    }

    private static void testUnreflectVarHandleOnStaticField() throws Exception {
        Field field = Sample.class.getDeclaredField("staticValue");
        VarHandle vh = MethodHandles.unreflectVarHandle(field);
        if (vh == null) {
            throw new AssertionError("VarHandle should not be null for static field");
        }
        // Test VarHandle get and set on static field
        vh.set(84);
        int val = (int) vh.get();
        if (val != 84) {
            throw new AssertionError("VarHandle get/set failed on static field, expected 84 but got " + val);
        }
    }

    private static void testUnreflectVarHandleOnPrivateField() throws Exception {
        Field field = Sample.class.getDeclaredField("privateValue");
        VarHandle vh = MethodHandles.unreflectVarHandle(field);
        if (vh == null) {
            throw new AssertionError("VarHandle should not be null for private field");
        }
        Sample sample = new Sample();
        vh.set(sample, 123);
        int val = (int) vh.get(sample);
        if (val != 123) {
            throw new AssertionError("VarHandle get/set failed on private field, expected 123 but got " + val);
        }
    }

    static class Sample {
        int value = 0;
        private int privateValue = 0;
        static int staticValue = 0;
    }
}
