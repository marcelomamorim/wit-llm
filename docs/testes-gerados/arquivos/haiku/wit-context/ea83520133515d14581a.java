/*
 * @test
 * @summary Verify MethodType.genericMethodType(int) accepts valid arity and rejects arity outside the 0..255 mask range
 * @modules java.base/java.lang.invoke
 * @run main/othervm --add-opens java.base/java.lang.invoke=ALL-UNNAMED MethodTypeWitupTest
 */

import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodTypeWitupTest {

    private static Method genericMethod;

    public static void main(String[] args) throws Exception {
        genericMethod = MethodType.class.getDeclaredMethod("genericMethodType", int.class);
        genericMethod.setAccessible(true);

        checkValid(0);
        checkValid(3);
        checkValid(255);

        expectIllegalArgument(256);
        expectIllegalArgument(-1);
    }

    private static MethodType generic(int n) throws Exception {
        try {
            return (MethodType) genericMethod.invoke(null, n);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof Exception) {
                throw (Exception) cause;
            }
            throw e;
        }
    }

    private static void checkValid(int n) throws Exception {
        MethodType mt = generic(n);
        if (mt.parameterCount() != n) {
            throw new RuntimeException("parameterCount mismatch for " + n + ": " + mt.parameterCount());
        }
        if (mt.returnType() != Object.class) {
            throw new RuntimeException("return type mismatch for " + n + ": " + mt.returnType());
        }
        for (int i = 0; i < n; i++) {
            if (mt.parameterType(i) != Object.class) {
                throw new RuntimeException("parameter " + i + " is not Object for arity " + n);
            }
        }
    }

    private static void expectIllegalArgument(int n) throws Exception {
        try {
            generic(n);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for arity " + n);
    }
}
