/*
 * @test
 * @summary Verifies MethodType.generic() replaces all parameter and return types with Object and preserves the parameter count
 * @run main MethodTypeWitupTest
 */

import java.lang.invoke.MethodType;

public class MethodTypeWitupTest {

    public static void main(String[] args) {
        MethodType noArgs = MethodType.methodType(void.class);
        check(noArgs.generic().equals(MethodType.methodType(Object.class)), "no-arg generic");
        check(noArgs.generic().parameterCount() == 0, "no-arg parameter count");

        MethodType mt = MethodType.methodType(void.class, int.class, String.class);
        MethodType g = mt.generic();
        check(g.parameterCount() == 2, "parameter count preserved");
        check(g.returnType() == Object.class, "return type generalized to Object");
        check(g.parameterType(0) == Object.class, "first parameter generalized");
        check(g.parameterType(1) == Object.class, "second parameter generalized");
        check(g.equals(MethodType.methodType(Object.class, Object.class, Object.class)), "generic equals expected type");
        check(g.generic().equals(g), "generic is idempotent");

        check(mt.returnType() == void.class, "original return type unchanged");
        check(mt.parameterType(0) == int.class, "original first parameter unchanged");
        check(mt.parameterType(1) == String.class, "original second parameter unchanged");

        MethodType prim = MethodType.methodType(int.class, long.class, double.class, boolean.class);
        MethodType pg = prim.generic();
        check(pg.returnType() == Object.class, "primitive return generalized");
        check(pg.parameterCount() == 3, "primitive parameter count preserved");
        check(pg.equals(MethodType.methodType(Object.class, Object.class, Object.class, Object.class)), "primitive generic equals expected type");

        Class<?>[] many = new Class<?>[100];
        for (int i = 0; i < many.length; i++) {
            many[i] = long.class;
        }
        MethodType big = MethodType.methodType(void.class, many);
        MethodType bg = big.generic();
        check(bg.parameterCount() == 100, "large parameter count preserved");
        for (int i = 0; i < 100; i++) {
            check(bg.parameterType(i) == Object.class, "large parameter generalized at " + i);
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
