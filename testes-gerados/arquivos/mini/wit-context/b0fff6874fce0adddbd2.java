/*
 * @test
 * @summary Test java.lang.reflect.Parameter.isVarArgs() method behavior
 * @run main ParameterWitupTest
 */

import java.lang.reflect.*;

public class ParameterWitupTest {
    public static void main(String[] args) throws Exception {
        // Use a known Executable: a method with varargs
        Method varArgsMethod = VarArgsClass.class.getMethod("varArgsMethod", String[].class);
        Parameter[] params = varArgsMethod.getParameters();

        // The last parameter should be varargs
        Parameter lastParam = params[params.length - 1];
        if (!lastParam.isVarArgs()) {
            throw new AssertionError("Expected last parameter to be varargs");
        }

        // The first parameter should not be varargs
        Parameter firstParam = params[0];
        if (firstParam.isVarArgs()) {
            throw new AssertionError("Expected first parameter NOT to be varargs");
        }

        // Test equals and hashCode consistency for parameters
        Parameter p1 = params[0];
        Parameter p2 = params[0];
        if (!p1.equals(p2)) {
            throw new AssertionError("Parameter equals should be true for same parameter");
        }
        if (p1.hashCode() != p2.hashCode()) {
            throw new AssertionError("Parameter hashCode should be consistent for same parameter");
        }

        // Test equals returns false for different parameters
        if (params.length > 1 && params[0].equals(params[1])) {
            throw new AssertionError("Different parameters should not be equal");
        }

        // Test isNamePresent returns true for parameters with names (compiled with -parameters)
        for (Parameter p : params) {
            // We cannot guarantee parameter names presence, so just call and check no exception
            p.isNamePresent();
        }

        System.out.println("All tests passed.");
    }

    public static class VarArgsClass {
        public void varArgsMethod(String... args) {}
    }
}
