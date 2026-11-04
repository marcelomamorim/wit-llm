/*
 * @test
 * @summary Test MethodType.generic() for parameter count boundary and normal behavior
 * @run main MethodTypeWitupTest
 */

import java.lang.invoke.MethodType;

public class MethodTypeWitupTest {
    public static void main(String[] args) {
        testGenericNormal();
        testGenericParameterCountBoundary();
    }

    private static void testGenericNormal() {
        // Create a MethodType with a small parameter count (e.g., 0) and call generic()
        MethodType mt = MethodType.methodType(void.class);
        MethodType genericMt = mt.generic();
        // generic() returns a MethodType with the same parameter count as mt
        if (genericMt.parameterCount() != mt.parameterCount()) {
            throw new AssertionError("generic() returned MethodType with different parameter count");
        }
    }

    private static void testGenericParameterCountBoundary() {
        // The method generic() calls genericMethodType(parameterCount())
        // The expath suggests an IllegalArgumentException if (parameterCount() & 255) != parameterCount()
        // We cannot create a MethodType with parameterCount > 255 via public API,
        // so we test that for normal parameter counts no exception is thrown.
        // This test documents that no exception is thrown for parameterCount <= 255.

        // Test with parameterCount = 255 (max allowed without exception)
        MethodType mt = MethodType.genericMethodType(255);
        MethodType genericMt = mt.generic();
        if (genericMt.parameterCount() != 255) {
            throw new AssertionError("generic() with 255 parameters returned wrong parameter count");
        }

        // We cannot test parameterCount > 255 because no public constructor/factory allows it.
        // Thus, the expath for IllegalArgumentException is noted but not tested here.
    }
}
