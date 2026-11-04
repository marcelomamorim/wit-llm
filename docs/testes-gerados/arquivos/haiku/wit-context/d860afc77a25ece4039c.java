/*
 * @test
 * @summary WITUP-derived checks for MethodHandles.Lookup.unreflectSpecial with public API only
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

public class MethodHandlesWitupTest {

    public String hello() {
        return "hello";
    }

    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        Method m = MethodHandlesWitupTest.class.getMethod("hello");

        MethodHandle mh = lookup.unreflectSpecial(m, MethodHandlesWitupTest.class);
        String result = (String) mh.invoke(new MethodHandlesWitupTest());
        if (!"hello".equals(result)) {
            throw new AssertionError("unexpected result: " + result);
        }

        try {
            lookup.unreflectSpecial(m, String.class);
            throw new AssertionError("expected IllegalAccessException for foreign specialCaller");
        } catch (IllegalAccessException expected) {
        }
    }
}
