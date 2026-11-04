/*
 * @test
 * @summary Verifies the observable behavior of MethodType.genericMethodType(int) for small argument counts
 * @run main MethodTypeWitupTest
 */

import java.lang.invoke.MethodType;

public class MethodTypeWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testZeroArgs() {
        MethodType mt = MethodType.genericMethodType(0);
        check(mt.parameterCount() == 0, "expected 0 parameters");
        check(mt.returnType() == Object.class, "expected Object return type");
        check(mt.equals(MethodType.methodType(Object.class)), "expected ()Object");
    }

    static void testThreeArgs() {
        MethodType mt = MethodType.genericMethodType(3);
        check(mt.parameterCount() == 3, "expected 3 parameters");
        check(mt.returnType() == Object.class, "expected Object return type");
        for (int i = 0; i < 3; i++) {
            check(mt.parameterType(i) == Object.class, "expected Object parameter at " + i);
        }
        check(mt.equals(MethodType.methodType(Object.class, Object.class, Object.class, Object.class)),
              "expected (Object,Object,Object)Object");
    }

    static void testEqualityAndInteroperability() {
        MethodType a = MethodType.genericMethodType(2);
        MethodType b = MethodType.genericMethodType(2);
        check(a.equals(b), "equal generic types expected");
        check(a.hashCode() == b.hashCode(), "hashCodes of equal types must match");
        check(!a.equals(MethodType.genericMethodType(1)), "different arity must not be equal");
    }

    public static void main(String[] args) {
        testZeroArgs();
        testThreeArgs();
        testEqualityAndInteroperability();
    }
}
