/*
 * @test
 * @summary Verifies that MethodType.generic() replaces all parameter and return types with Object while preserving the parameter count
 * @run main MethodTypeWitupTest
 */

import java.lang.invoke.MethodType;

public class MethodTypeWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testMixedParameters() {
        MethodType mt = MethodType.methodType(void.class, int.class, String.class);
        MethodType g = mt.generic();
        check(g.parameterCount() == 2, "parameter count must be preserved");
        check(g.parameterType(0) == Object.class, "first parameter must be Object");
        check(g.parameterType(1) == Object.class, "second parameter must be Object");
        check(g.returnType() == Object.class, "return type must be Object");
        check(g.equals(MethodType.genericMethodType(2)), "generic() must equal genericMethodType(parameterCount())");
    }

    static void testNoParameters() {
        MethodType mt = MethodType.methodType(int.class);
        MethodType g = mt.generic();
        check(g.parameterCount() == 0, "no-arg type must stay no-arg");
        check(g.returnType() == Object.class, "return type must be Object");
        check(g.equals(MethodType.genericMethodType(0)), "no-arg generic must equal genericMethodType(0)");
    }

    static void testVoidReturnBecomesObject() {
        MethodType mt = MethodType.methodType(void.class, long.class);
        MethodType g = mt.generic();
        check(g.returnType() == Object.class, "void return must become Object");
        check(g.parameterType(0) == Object.class, "long parameter must become Object");
    }

    static void testAlreadyGenericIsStable() {
        MethodType mt = MethodType.genericMethodType(3);
        MethodType g = mt.generic();
        check(g.equals(mt), "generic type must be unchanged when already generic");
        check(g.generic().equals(g), "generic() must be idempotent");
    }

    static void testResultIsEquivalentForEqualInputs() {
        MethodType a = MethodType.methodType(String.class, Integer.class).generic();
        MethodType b = MethodType.methodType(Long.class, Double.class).generic();
        check(a.equals(b), "generic types with the same arity must be equal");
    }

    public static void main(String[] args) {
        testMixedParameters();
        testNoParameters();
        testVoidReturnBecomesObject();
        testAlreadyGenericIsStable();
        testResultIsEquivalentForEqualInputs();
    }
}
