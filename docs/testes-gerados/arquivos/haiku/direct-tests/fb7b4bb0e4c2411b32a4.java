/*
 * @test
 * @summary Exercises public asSpreader behavior on MethodHandle, the public counterpart of the package-private spreadInvoker path
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandlesWitupTest {

    static String join(String a, Object b) {
        return a + b;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle target = lookup.findStatic(MethodHandlesWitupTest.class, "join",
                MethodType.methodType(String.class, String.class, Object.class));

        MethodHandle trailingSpread = target.asSpreader(Object[].class, 1);
        check(trailingSpread.type().equals(
                MethodType.methodType(String.class, String.class, Object[].class)),
                "unexpected type for trailing spread: " + trailingSpread.type());
        String r1 = (String) trailingSpread.invoke("x", new Object[]{"y"});
        check("xy".equals(r1), "expected xy but got " + r1);

        MethodHandle fullSpread = target.asSpreader(Object[].class, 2);
        check(fullSpread.type().equals(
                MethodType.methodType(String.class, Object[].class)),
                "unexpected type for full spread: " + fullSpread.type());
        String r2 = (String) fullSpread.invoke(new Object[]{"a", "b"});
        check("ab".equals(r2), "expected ab but got " + r2);
    }
}
