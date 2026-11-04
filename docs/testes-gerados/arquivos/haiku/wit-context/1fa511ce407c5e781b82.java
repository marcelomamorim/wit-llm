/*
 * @test
 * @summary Checks that MethodHandleInfo.getMethodType() (implemented by InfoFromMemberName) returns the expected method or field type for direct method handles revealed through the public API.
 * @run main InfoFromMemberNameWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandleInfo;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class InfoFromMemberNameWitupTest {

    private static String field = "value";

    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        MethodHandle length = lookup.findVirtual(String.class, "length",
                MethodType.methodType(int.class));
        MethodHandleInfo lengthInfo = lookup.revealDirect(length);
        check(lengthInfo.getMethodType() != null, "virtual method type must not be null");
        check(lengthInfo.getMethodType().equals(MethodType.methodType(int.class, String.class)),
                "String.length type mismatch: " + lengthInfo.getMethodType());
        check(lengthInfo.getMethodType().equals(length.type()),
                "virtual method type must match handle type");

        MethodHandle parseInt = lookup.findStatic(Integer.class, "parseInt",
                MethodType.methodType(int.class, String.class));
        MethodHandleInfo parseInfo = lookup.revealDirect(parseInt);
        check(parseInfo.getMethodType().equals(MethodType.methodType(int.class, String.class)),
                "Integer.parseInt type mismatch: " + parseInfo.getMethodType());
        check(parseInfo.getMethodType().equals(parseInt.type()),
                "static method type must match handle type");

        MethodHandle getter = lookup.findStaticGetter(InfoFromMemberNameWitupTest.class,
                "field", String.class);
        MethodHandleInfo getterInfo = lookup.revealDirect(getter);
        check(getterInfo.getMethodType().equals(MethodType.methodType(String.class)),
                "static field getter type mismatch: " + getterInfo.getMethodType());
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
