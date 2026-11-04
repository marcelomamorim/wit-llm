/*
 * @test
 * @summary Verifies MethodHandles.Lookup.unreflectGetter returns getter handles with the expected type and observable values for instance and static fields
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

public class MethodHandlesWitupTest {

    static class Holder {
        public int x = 42;
        public static String s = "st";
    }

    public static void main(String[] args) throws Exception {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        Field fx = Holder.class.getField("x");
        MethodHandle mh = lookup.unreflectGetter(fx);
        check(mh.type().equals(MethodType.methodType(int.class, Holder.class)), "instance getter type");

        Holder h = new Holder();
        int v = (int) mh.invoke(h);
        check(v == 42, "initial instance field value");

        h.x = 7;
        v = (int) mh.invoke(h);
        check(v == 7, "getter reflects updated field value");

        Field fs = Holder.class.getField("s");
        MethodHandle ms = lookup.unreflectGetter(fs);
        check(ms.type().equals(MethodType.methodType(String.class)), "static getter type");

        String sv = (String) ms.invoke();
        check("st".equals(sv), "static field value");
    }

    static void check(boolean condition, String what) {
        if (!condition) {
            throw new AssertionError("Failed: " + what);
        }
    }
}
