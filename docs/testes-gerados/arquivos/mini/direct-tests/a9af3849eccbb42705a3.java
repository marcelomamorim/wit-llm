/*
 * @test
 * @summary Test MethodType.generic() returns a generic method type with same parameter count
 * @run main MethodTypeWitupTest
 */

import java.lang.invoke.MethodType;

public class MethodTypeWitupTest {
    public static void main(String[] args) throws Throwable {
        // Create a MethodType with known parameters
        MethodType mt = MethodType.methodType(void.class, int.class, String.class);
        int paramCount = mt.parameterCount();

        // Call generic() and verify it returns a MethodType with same parameter count
        MethodType genericMt = mt.generic();
        if (genericMt.parameterCount() != paramCount) {
            throw new AssertionError("generic() returned MethodType with different parameter count");
        }

        // Verify that the generic MethodType has all parameters as Object.class
        for (int i = 0; i < paramCount; i++) {
            if (genericMt.parameterType(i) != Object.class) {
                throw new AssertionError("generic() parameter " + i + " is not Object.class");
            }
        }

        // Verify that the return type of generic MethodType is Object.class
        if (genericMt.returnType() != Object.class) {
            throw new AssertionError("generic() return type is not Object.class");
        }

        // Also test generic() on a MethodType with zero parameters
        MethodType mt0 = MethodType.methodType(int.class);
        MethodType genericMt0 = mt0.generic();
        if (genericMt0.parameterCount() != 0) {
            throw new AssertionError("generic() on zero-parameter MethodType has non-zero parameter count");
        }
        if (genericMt0.returnType() != Object.class) {
            throw new AssertionError("generic() return type on zero-parameter MethodType is not Object.class");
        }
    }
}
