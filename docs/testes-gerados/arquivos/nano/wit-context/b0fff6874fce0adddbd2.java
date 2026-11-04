/*
 * @test
 * @summary Test for java.lang.reflect.Parameter methods, focusing on isVarArgs behavior.
 * @run main ParameterWitupTest
 */

import java.lang.reflect.Parameter;

public class ParameterWitupTest {

    public static void main(String[] args) {
        testIsVarArgs();
    }

    private static void testIsVarArgs() {
        try {
            // Reflection to get a method with varargs
            java.lang.reflect.Method method = SampleClass.class.getMethod("varArgsMethod", String[].class);
            Parameter[] params = method.getParameters();
            if (params.length > 0) {
                Parameter param = params[0];
                boolean result = param.isVarArgs();
                System.out.println("isVarArgs: " + result);
                // Expect true for varargs method
                if (!result) {
                    throw new AssertionError("Expected isVarArgs to be true for varargs method.");
                }
            } else {
                throw new AssertionError("No parameters found.");
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static class SampleClass {
        public void varArgsMethod(String... args) {
            // sample method with varargs
        }
    }
}
