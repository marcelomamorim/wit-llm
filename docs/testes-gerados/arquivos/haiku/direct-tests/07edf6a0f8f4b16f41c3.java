/*
 * @test
 * @summary Verifies MethodHandles.Lookup.unreflectSetter for instance, static and static final fields
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

public class MethodHandlesWitupTest {
    public int x;
    public static int t;
    public static final int S = 1;

    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        Field fx = MethodHandlesWitupTest.class.getField("x");
        MethodHandle setterX = lookup.unreflectSetter(fx);
        if (setterX == null) {
            throw new RuntimeException("unreflectSetter returned null for instance field");
        }
        MethodType expected = MethodType.methodType(void.class, MethodHandlesWitupTest.class, int.class);
        if (!setterX.type().equals(expected)) {
            throw new RuntimeException("Unexpected setter type: " + setterX.type());
        }
        MethodHandlesWitupTest obj = new MethodHandlesWitupTest();
        setterX.invokeExact(obj, 42);
        if (obj.x != 42) {
            throw new RuntimeException("Instance field not set: " + obj.x);
        }

        Field ft = MethodHandlesWitupTest.class.getField("t");
        MethodHandle setterT = lookup.unreflectSetter(ft);
        MethodType expectedStatic = MethodType.methodType(void.class, int.class);
        if (!setterT.type().equals(expectedStatic)) {
            throw new RuntimeException("Unexpected static setter type: " + setterT.type());
        }
        setterT.invokeExact(7);
        if (MethodHandlesWitupTest.t != 7) {
            throw new RuntimeException("Static field not set: " + MethodHandlesWitupTest.t);
        }

        Field fs = MethodHandlesWitupTest.class.getField("S");
        try {
            lookup.unreflectSetter(fs);
            throw new RuntimeException("Expected IllegalAccessException for static final field");
        } catch (IllegalAccessException expectedException) {
            // expected
        }
    }
}
