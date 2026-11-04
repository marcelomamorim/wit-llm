/*
 * @test
 * @summary Witup-derived checks for MethodHandle.asVarargsCollector null handling and basic varargs collector behavior
 * @run main MethodHandleWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

public class MethodHandleWitupTest {

    public static void main(String[] args) throws Exception {
        MethodHandle mh = MethodHandles.identity(Object.class);

        boolean npeThrown = false;
        try {
            mh.asVarargsCollector(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("asVarargsCollector(null) must throw NullPointerException");
        }

        MethodHandle varargs = mh.asVarargsCollector(Object[].class);
        if (varargs == null) {
            throw new AssertionError("asVarargsCollector(Object[].class) must not return null");
        }
        if (!varargs.isVarargsCollector()) {
            throw new AssertionError("result of asVarargsCollector must report isVarargsCollector() == true");
        }
    }
}
