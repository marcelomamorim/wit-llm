/*
 * @test
 * @summary Verifies that MethodType factory results are interned and compare equal through the public API, which exercises MethodType.add
 * @run main MethodTypeWitupTest
 */

import java.lang.invoke.MethodType;

public class MethodTypeWitupTest {

    public static void main(String[] args) {
        MethodType a = MethodType.methodType(int.class, String.class, long.class);
        MethodType b = MethodType.methodType(int.class, String.class, long.class);
        check(a.equals(b), "equal method types must compare equal");
        check(a == b, "identical method types must be interned to the same instance");

        MethodType c = MethodType.methodType(void.class);
        check(c.returnType() == void.class, "return type of void method type");
        check(c.parameterCount() == 0, "void method type must have no parameters");
        check(c == MethodType.methodType(void.class), "no-arg method type must be interned");

        MethodType d = MethodType.methodType(int.class, String.class);
        check(!a.equals(d), "method types with different parameters must not be equal");
        check(a != d, "distinct method types must not be the same instance");

        MethodType e = MethodType.methodType(Object.class, Object.class);
        MethodType f = MethodType.methodType(Object.class, Object.class);
        check(e == f, "reference method types must be interned");

        for (int i = 0; i < 1000; i++) {
            MethodType x = MethodType.methodType(long.class, int.class, double.class);
            MethodType y = MethodType.methodType(long.class, int.class, double.class);
            check(x == y, "repeated interning must yield the same instance at iteration " + i);
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
