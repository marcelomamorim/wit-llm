/*
 * @test
 * @summary Tests MethodHandles.Lookup.unreflectVarHandle with a null field and a valid static field
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;

public class MethodHandlesWitupTest {

    static int counter = 5;

    public static void main(String[] args) throws Exception {
        testNullFieldThrowsNpe();
        testStaticFieldAccess();
    }

    static void testNullFieldThrowsNpe() {
        try {
            MethodHandles.lookup().unreflectVarHandle(null);
            throw new AssertionError("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        }
    }

    static void testStaticFieldAccess() throws Exception {
        Field f = MethodHandlesWitupTest.class.getDeclaredField("counter");
        VarHandle vh = MethodHandles.lookup().unreflectVarHandle(f);
        int v = (int) vh.get();
        if (v != 5) {
            throw new AssertionError("expected 5 but was " + v);
        }
        vh.set(9);
        int w = (int) vh.get();
        if (w != 9) {
            throw new AssertionError("expected 9 but was " + w);
        }
    }
}
