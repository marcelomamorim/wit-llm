/*
 * @test
 * @summary Verifies Lookup.unreflectSetter rejects null fields and returns a working setter for a public field
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

public class MethodHandlesWitupTest {

    public static class Holder {
        public int value;
    }

    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        boolean npeThrown = false;
        try {
            lookup.unreflectSetter(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("expected NullPointerException for null Field");
        }

        Field f = Holder.class.getField("value");
        MethodHandle setter = lookup.unreflectSetter(f);

        MethodType expected = MethodType.methodType(void.class, Holder.class, int.class);
        if (!expected.equals(setter.type())) {
            throw new AssertionError("unexpected setter type: " + setter.type());
        }

        Holder h = new Holder();
        setter.invokeExact(h, 42);
        if (h.value != 42) {
            throw new AssertionError("setter did not update field, value=" + h.value);
        }
    }
}
