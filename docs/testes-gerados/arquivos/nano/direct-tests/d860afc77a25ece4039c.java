/*
 * @test
 * @summary Test unreflectSpecial method behavior in MethodHandles
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.lang.invoke.MethodHandles;
import static java.lang.System.out;

public class MethodHandlesWitupTest {

    public static void main(String[] args) throws Throwable {
        testUnreflectSpecialWithAccessibleMethod();
        testUnreflectSpecialWithInaccessibleMethod();
    }

    private static void testUnreflectSpecialWithAccessibleMethod() throws Throwable {
        MethodHandles mh = MethodHandles.lookup();
        Method method = SampleClass.class.getDeclaredMethod("privateMethod");
        method.setAccessible(true); // make method accessible
        MethodHandle handle = mh.unreflectSpecial(method, SampleClass.class);
        Object result = handle.invoke(new SampleClass());
        assert "Private method called".equals(result) : "Expected 'Private method called', got: " + result;
        out.println("Accessible method test passed.");
    }

    private static void testUnreflectSpecialWithInaccessibleMethod() throws Throwable {
        MethodHandles mh = MethodHandles.lookup();
        Method method = SampleClass.class.getDeclaredMethod("privateMethod");
        // do not set accessible, expect IllegalAccessException
        try {
            MethodHandle handle = mh.unreflectSpecial(method, SampleClass.class);
            handle.invoke(new SampleClass());
            assert false : "Expected IllegalAccessException";
        } catch (IllegalAccessException e) {
            out.println("Inaccessible method test passed.");
        }
    }

    static class SampleClass {
        private String privateMethod() {
            return "Private method called";
        }
    }
}
