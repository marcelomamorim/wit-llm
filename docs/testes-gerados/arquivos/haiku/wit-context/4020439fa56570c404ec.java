/*
 * @test
 * @summary Exercises public MethodType factories that reach the intern set backing ConcurrentWeakInternSet
 * @run main MethodTypeWitupTest
 */

import java.lang.invoke.MethodType;

public class MethodTypeWitupTest {
    public static void main(String[] args) {
        MethodType a = MethodType.methodType(void.class, int.class);
        MethodType b = MethodType.methodType(void.class, int.class);
        check(a.equals(b), "equal method types must compare equal");
        check(a.toString().equals("(int)void"), "unexpected toString: " + a);

        MethodType c = MethodType.methodType(String.class, String.class, Object.class);
        check(c.parameterCount() == 2, "unexpected parameter count");
        check(c.returnType() == String.class, "unexpected return type");
        check(!a.equals(c), "different method types must not compare equal");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
