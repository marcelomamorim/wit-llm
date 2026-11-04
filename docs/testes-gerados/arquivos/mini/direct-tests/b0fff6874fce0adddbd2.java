/*
 * @test
 * @summary Test java.lang.reflect.Parameter.isVarArgs() method
 * @run main ParameterWitupTest
 */

import java.lang.reflect.*;

public class ParameterWitupTest {
    public static void main(String[] args) throws Exception {
        testIsVarArgsTrue();
        testIsVarArgsFalse();
    }

    static void testIsVarArgsTrue() throws Exception {
        // Use a method with varargs
        Method m = VarArgsClass.class.getMethod("varArgsMethod", String[].class);
        Parameter[] params = m.getParameters();
        // The last parameter should be varargs
        Parameter lastParam = params[params.length - 1];
        if (!lastParam.isVarArgs()) {
            throw new RuntimeException("Expected isVarArgs() to be true for last parameter of varArgsMethod");
        }
    }

    static void testIsVarArgsFalse() throws Exception {
        // Use a method without varargs
        Method m = VarArgsClass.class.getMethod("normalMethod", String.class, int.class);
        Parameter[] params = m.getParameters();
        for (Parameter p : params) {
            if (p.isVarArgs()) {
                throw new RuntimeException("Expected isVarArgs() to be false for normalMethod parameters");
            }
        }
    }

    static class VarArgsClass {
        public void varArgsMethod(String... args) {}
        public void normalMethod(String s, int i) {}
    }
}
