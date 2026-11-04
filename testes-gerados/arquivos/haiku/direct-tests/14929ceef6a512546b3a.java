/*
 * @test
 * @summary Verifies MethodHandles.Lookup.unreflectVarHandle for public static and instance fields
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;

public class MethodHandlesWitupTest {
    public static int sValue = 1;
    public int iValue = 2;

    public static void main(String[] args) throws Exception {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        Field sf = MethodHandlesWitupTest.class.getDeclaredField("sValue");
        VarHandle svh = lookup.unreflectVarHandle(sf);
        check(svh != null, "null VarHandle for static field");
        int sGot = (int) svh.get();
        check(sGot == 1, "static get returned " + sGot);
        svh.set(7);
        check(sValue == 7, "static set not observed, sValue=" + sValue);

        Field f = MethodHandlesWitupTest.class.getDeclaredField("iValue");
        VarHandle ivh = lookup.unreflectVarHandle(f);
        check(ivh != null, "null VarHandle for instance field");
        MethodHandlesWitupTest t = new MethodHandlesWitupTest();
        int iGot = (int) ivh.get(t);
        check(iGot == 2, "instance get returned " + iGot);
        ivh.set(t, 9);
        check(t.iValue == 9, "instance set not observed, iValue=" + t.iValue);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
