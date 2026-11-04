/*
 * @test
 * @summary Checks MethodHandles.spreadInvoker argument count validation and valid invoker creation via reflection
 * @run main/othervm --add-opens java.base/java.lang.invoke=ALL-UNNAMED MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodHandlesWitupTest {

    private static Method spreadInvokerMethod() throws NoSuchMethodException {
        Method m = java.lang.invoke.MethodHandles.class.getDeclaredMethod("spreadInvoker", MethodType.class, int.class);
        m.setAccessible(true);
        return m;
    }

    private static void checkBadCount(int leadingArgCount) throws Exception {
        MethodType type = MethodType.methodType(void.class, String.class, int.class);
        try {
            spreadInvokerMethod().invoke(null, type, leadingArgCount);
            throw new AssertionError("expected IllegalArgumentException for leadingArgCount=" + leadingArgCount);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof IllegalArgumentException)) {
                throw new AssertionError("unexpected cause: " + cause, cause);
            }
        }
    }

    private static void testNegativeLeadingArgCount() throws Exception {
        checkBadCount(-1);
    }

    private static void testLeadingArgCountTooLarge() throws Exception {
        checkBadCount(3);
    }

    private static void testValidLeadingArgCount() throws Exception {
        MethodType type = MethodType.methodType(void.class, String.class, int.class);
        Object result = spreadInvokerMethod().invoke(null, type, 0);
        if (!(result instanceof MethodHandle)) {
            throw new AssertionError("expected MethodHandle but got " + result);
        }
    }

    public static void main(String[] args) throws Exception {
        testNegativeLeadingArgCount();
        testLeadingArgCountTooLarge();
        testValidLeadingArgCount();
    }
}
