/*
 * @test
 * @summary Tests MethodHandles.Lookup.unreflectGetter for public fields and null input
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

public class MethodHandlesWitupTest {
    public static int staticValue = 42;
    public String name = "witup";

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        Field staticField = MethodHandlesWitupTest.class.getField("staticValue");
        MethodHandle staticGetter = lookup.unreflectGetter(staticField);
        int value = (int) staticGetter.invokeExact();
        check(value == 42, "static getter returned " + value);

        Field instanceField = MethodHandlesWitupTest.class.getField("name");
        MethodHandle instanceGetter = lookup.unreflectGetter(instanceField);
        String name = (String) instanceGetter.invoke(new MethodHandlesWitupTest());
        check("witup".equals(name), "instance getter returned " + name);

        boolean npeThrown = false;
        try {
            lookup.unreflectGetter(null);
        } catch (NullPointerException expected) {
            npeThrown = true;
        }
        check(npeThrown, "unreflectGetter(null) did not throw NullPointerException");
    }
}
