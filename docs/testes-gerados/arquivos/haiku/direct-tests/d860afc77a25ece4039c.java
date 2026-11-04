/*
 * @test
 * @summary Checks Lookup.unreflectSpecial returns handles with the expected type and behavior for private and public methods of the lookup class
 * @run main MethodHandlesWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

public class MethodHandlesWitupTest {

    private String secret(String s) {
        return "secret:" + s;
    }

    private int twice(int x) {
        return x * 2;
    }

    public String pub(String s) {
        return "pub:" + s;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandlesWitupTest obj = new MethodHandlesWitupTest();

        Method secretM = MethodHandlesWitupTest.class.getDeclaredMethod("secret", String.class);
        MethodHandle mh1 = lookup.unreflectSpecial(secretM, MethodHandlesWitupTest.class);
        check(mh1.type().equals(MethodType.methodType(String.class, MethodHandlesWitupTest.class, String.class)),
              "unexpected type for private String method handle: " + mh1.type());
        String r1 = (String) mh1.invoke(obj, "x");
        check("secret:x".equals(r1), "unexpected result for private String method: " + r1);

        Method twiceM = MethodHandlesWitupTest.class.getDeclaredMethod("twice", int.class);
        MethodHandle mh2 = lookup.unreflectSpecial(twiceM, MethodHandlesWitupTest.class);
        check(mh2.type().equals(MethodType.methodType(int.class, MethodHandlesWitupTest.class, int.class)),
              "unexpected type for private int method handle: " + mh2.type());
        int r2 = (int) mh2.invoke(obj, 21);
        check(r2 == 42, "unexpected result for private int method: " + r2);

        Method pubM = MethodHandlesWitupTest.class.getDeclaredMethod("pub", String.class);
        MethodHandle mh3 = lookup.unreflectSpecial(pubM, MethodHandlesWitupTest.class);
        String r3 = (String) mh3.invoke(obj, "y");
        check("pub:y".equals(r3), "unexpected result for public method: " + r3);
    }
}
