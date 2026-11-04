/*
 * @test
 * @summary Verifies that MethodType factory methods return interned, equal instances (observable path through the weak intern set used by get)
 * @run main MethodTypeWitupTest
 */

import java.lang.invoke.MethodType;

public class MethodTypeWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        testInterningReturnsSameInstance();
        testEqualTypesShareHashCode();
        testReturnAndParameterTypes();
        testDistinctTypesAreNotEqual();
    }

    static void testInterningReturnsSameInstance() {
        MethodType first = MethodType.methodType(String.class, int.class);
        MethodType second = MethodType.methodType(String.class, int.class);
        check(first == second, "expected interned instance for identical method types");
    }

    static void testEqualTypesShareHashCode() {
        MethodType a = MethodType.methodType(void.class, long.class, Object.class);
        MethodType b = MethodType.methodType(void.class, new Class<?>[] { long.class, Object.class });
        check(a.equals(b), "equal method types must compare equal");
        check(a.hashCode() == b.hashCode(), "equal method types must have equal hash codes");
    }

    static void testReturnAndParameterTypes() {
        MethodType mt = MethodType.methodType(boolean.class, char.class, String.class);
        check(mt.returnType() == boolean.class, "wrong return type");
        check(mt.parameterCount() == 2, "wrong parameter count");
        check(mt.parameterType(0) == char.class, "wrong parameter 0");
        check(mt.parameterType(1) == String.class, "wrong parameter 1");
    }

    static void testDistinctTypesAreNotEqual() {
        MethodType a = MethodType.methodType(int.class, int.class);
        MethodType b = MethodType.methodType(long.class, int.class);
        check(!a.equals(b), "types with different return types must differ");
        check(a != b, "distinct method types must not be the same instance");
    }
}
