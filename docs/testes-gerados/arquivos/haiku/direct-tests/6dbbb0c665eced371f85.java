/*
 * @test
 * @summary Checks public MethodHandles.explicitCastArguments behavior for identity, reference and primitive casts
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlesWitupTest {

    public static void main(String[] args) throws Throwable {
        sameTypeReturnsSameHandle();
        referenceCastChangesTypeAndWorks();
        primitiveNarrowingArgumentCast();
        System.out.println("PASSED");
    }

    static void sameTypeReturnsSameHandle() {
        MethodHandle id = MethodHandles.identity(Object.class);
        MethodHandle result = MethodHandles.explicitCastArguments(id, id.type());
        if (result != id) {
            throw new RuntimeException("expected same handle when types are equal");
        }
    }

    static void referenceCastChangesTypeAndWorks() throws Throwable {
        MethodHandle id = MethodHandles.identity(Object.class);
        MethodType newType = MethodType.methodType(String.class, String.class);
        MethodHandle result = MethodHandles.explicitCastArguments(id, newType);
        if (!result.type().equals(newType)) {
            throw new RuntimeException("unexpected result type: " + result.type());
        }
        String out = (String) result.invokeExact("abc");
        if (!"abc".equals(out)) {
            throw new RuntimeException("unexpected result: " + out);
        }
    }

    static void primitiveNarrowingArgumentCast() throws Throwable {
        MethodHandle id = MethodHandles.identity(int.class);
        MethodType newType = MethodType.methodType(int.class, long.class);
        MethodHandle result = MethodHandles.explicitCastArguments(id, newType);
        if (!result.type().equals(newType)) {
            throw new RuntimeException("unexpected result type: " + result.type());
        }
        int out = (int) result.invokeExact(0x100000001L);
        if (out != 1) {
            throw new RuntimeException("expected 1 but was " + out);
        }
    }
}
