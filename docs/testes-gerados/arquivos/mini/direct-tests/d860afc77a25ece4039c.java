/*
 * @test
 * @summary Test MethodHandles.unreflectSpecial behavior
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

public class MethodHandlesWitupTest {
    public static void main(String[] args) throws Throwable {
        MethodHandles mh = new MethodHandles();

        // Test unreflectSpecial with a valid method and specialCaller
        Method method = String.class.getDeclaredMethod("toString");
        Class<?> specialCaller = String.class;
        MethodHandle mhandle = mh.unreflectSpecial(method, specialCaller);
        if (mhandle == null) {
            throw new AssertionError("unreflectSpecial returned null MethodHandle");
        }

        // Test that the returned MethodHandle is invocable and returns expected result
        String testStr = "test";
        Object result = mhandle.bindTo(testStr).invokeWithArguments();
        if (!testStr.equals(result)) {
            throw new AssertionError("MethodHandle invocation returned unexpected result: " + result);
        }
    }
}
