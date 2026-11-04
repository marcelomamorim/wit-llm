/*
 * @test
 * @summary Test MethodHandles.unreflectGetter(Field) behavior
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

public class MethodHandlesWitupTest {
    public static void main(String[] args) throws Throwable {
        MethodHandles mh = new MethodHandles(); // Actually MethodHandles has private constructor, so use MethodHandles.lookup()
        java.lang.invoke.MethodHandles.Lookup lookup = MethodHandles.lookup();

        // Test 1: unreflectGetter on a public field
        Field pubField = TestClass.class.getField("pubField");
        MethodHandle mhPub = lookup.unreflectGetter(pubField);
        Object val = mhPub.invoke(new TestClass());
        if (!"publicValue".equals(val)) {
            throw new AssertionError("Expected publicValue, got " + val);
        }

        // Test 2: unreflectGetter on a private field - should throw IllegalAccessException
        Field privField = TestClass.class.getDeclaredField("privField");
        boolean threw = false;
        try {
            lookup.unreflectGetter(privField);
        } catch (IllegalAccessException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalAccessException for private field");
        }

        // Test 3: unreflectGetter on a static field
        Field staticField = TestClass.class.getField("staticField");
        MethodHandle mhStatic = lookup.unreflectGetter(staticField);
        Object staticVal = mhStatic.invoke();
        if (!"staticValue".equals(staticVal)) {
            throw new AssertionError("Expected staticValue, got " + staticVal);
        }
    }

    public static class TestClass {
        public String pubField = "publicValue";
        private String privField = "privateValue";
        public static String staticField = "staticValue";
    }
}
