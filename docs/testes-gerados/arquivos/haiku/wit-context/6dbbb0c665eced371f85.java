/*
 * @test
 * @summary Checks explicitCastArguments identity and arity-mismatch behavior via reflection
 * @run main/othervm --add-opens java.base/java.lang.invoke=ALL-UNNAMED MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.WrongMethodTypeException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodHandlesWitupTest {

    static Method explicitCast;

    static Object invokeExplicitCast(MethodHandle target, MethodType newType) throws Exception {
        return explicitCast.invoke(null, target, newType);
    }

    public static void main(String[] args) throws Exception {
        explicitCast = MethodHandles.class.getDeclaredMethod("explicitCastArguments", MethodHandle.class, MethodType.class);
        explicitCast.setAccessible(true);

        MethodHandle target = MethodHandles.identity(int.class);

        Object same = invokeExplicitCast(target, MethodType.methodType(int.class, int.class));
        if (same != target) {
            throw new AssertionError("expected the same target when types are identical");
        }

        MethodType wrongArity = MethodType.methodType(int.class, int.class, int.class);
        try {
            invokeExplicitCast(target, wrongArity);
            throw new AssertionError("expected WrongMethodTypeException for parameter count mismatch");
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof WrongMethodTypeException)) {
                throw new AssertionError("unexpected cause: " + e.getCause());
            }
        }
    }
}
