/*
 * @test
 * @summary Test the isVarArgs() method of java.lang.reflect.Parameter
 * @run main ParameterWitupTest
 */

import java.lang.reflect.Parameter;

public class ParameterWitupTest {

    public static void main(String[] args) {
        testParameterWithVarArgs();
        testParameterWithoutVarArgs();
    }

    private static void testParameterWithVarArgs() {
        // Create a mock Executable with isVarArgs() returning true and getParameterCount() returning 3
        ExecutableMock executable = new ExecutableMock(true, 3);
        // Create a Parameter at index 2 (last parameter)
        Parameter param = new Parameter("param", 0, executable, 2);
        // The parameter should be varargs because executable.isVarArgs() is true and index is last
        boolean result = param.isVarArgs();
        if (!result) {
            throw new AssertionError("Expected isVarArgs() to return true for last parameter with varargs.");
        }
    }

    private static void testParameterWithoutVarArgs() {
        // Create a mock Executable with isVarArgs() returning false
        ExecutableMock executable = new ExecutableMock(false, 2);
        // Create a Parameter at index 1
        Parameter param = new Parameter("param", 0, executable, 1);
        // The parameter should not be varargs because executable.isVarArgs() is false
        boolean result = param.isVarArgs();
        if (result) {
            throw new AssertionError("Expected isVarArgs() to return false when executable.isVarArgs() is false.");
        }
    }

    // Mock class to simulate Executable behavior
    private static class ExecutableMock {
        private final boolean isVarArgs;
        private final int parameterCount;

        ExecutableMock(boolean isVarArgs, int parameterCount) {
            this.isVarArgs = isVarArgs;
            this.parameterCount = parameterCount;
        }

        public boolean isVarArgs() {
            return isVarArgs;
        }

        public int getParameterCount() {
            return parameterCount;
        }
    }
}
