/*
 * @test
 * @summary InfoFromMemberName.getMethodType reports the method type of a direct method handle via MethodHandles.Lookup.revealDirect
 * @run main InfoFromMemberNameWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandleInfo;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class InfoFromMemberNameWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        MethodHandle valueOf = lookup.findStatic(String.class, "valueOf",
                MethodType.methodType(String.class, Object.class));
        MethodHandleInfo infoStatic = lookup.revealDirect(valueOf);
        check(MethodType.methodType(String.class, Object.class).equals(infoStatic.getMethodType()),
                "static method type mismatch: " + infoStatic.getMethodType());

        MethodHandle parseInt = lookup.findStatic(Integer.class, "parseInt",
                MethodType.methodType(int.class, String.class));
        MethodHandleInfo infoParse = lookup.revealDirect(parseInt);
        check(MethodType.methodType(int.class, String.class).equals(infoParse.getMethodType()),
                "parseInt method type mismatch: " + infoParse.getMethodType());

        MethodHandle length = lookup.findVirtual(String.class, "length",
                MethodType.methodType(int.class));
        MethodHandleInfo infoVirtual = lookup.revealDirect(length);
        check(MethodType.methodType(int.class).equals(infoVirtual.getMethodType()),
                "virtual method type mismatch: " + infoVirtual.getMethodType());

        check("length".equals(infoVirtual.getName()), "name mismatch");
    }
}
