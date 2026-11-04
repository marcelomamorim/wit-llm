/*
 * @test
 * @summary Observable interning behavior of MethodType factory methods
 * @run main MethodTypeWitupTest
 */

import java.lang.invoke.MethodType;

public class MethodTypeWitupTest {
    public static void main(String[] args) {
        MethodType a = MethodType.methodType(int.class, String.class);
        MethodType b = MethodType.methodType(int.class, String.class);
        if (a != b) {
            throw new RuntimeException("equal method types should be interned to the same instance");
        }
        if (!a.equals(b)) {
            throw new RuntimeException("equal method types should compare equal");
        }

        MethodType v1 = MethodType.methodType(void.class);
        MethodType v2 = MethodType.methodType(void.class);
        if (v1 != v2) {
            throw new RuntimeException("no-arg void method types should be interned to the same instance");
        }

        MethodType different = MethodType.methodType(int.class, Object.class);
        if (different == a || different.equals(a)) {
            throw new RuntimeException("different method types should not be equal");
        }
        if (different.parameterCount() != 1 || different.returnType() != int.class) {
            throw new RuntimeException("unexpected observable shape of method type");
        }
    }
}
