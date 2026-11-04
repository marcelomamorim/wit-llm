/* @test    @summary Test the generic() method of MethodType for correct return of a generic MethodType instance.    @run main MethodTypeWitupTest */

import java.lang.invoke.MethodType;

public class MethodTypeWitupTest {

    public static void main(String[] args) {
        testGenericReturnsCorrectType();
    }

    private static void testGenericReturnsCorrectType() {
        MethodType mt = MethodType.generic();
        if (mt == null) {
            throw new AssertionError("MethodType.generic() returned null");
        }
        // Verify that the returned MethodType is indeed a MethodType instance
        if (!(mt instanceof MethodType)) {
            throw new AssertionError("Returned object is not an instance of MethodType");
        }
        // Verify that the returned MethodType is the same as the one returned by the static method
        MethodType expected = genericMethodType(mt.parameterCount());
        if (!mt.equals(expected)) {
            throw new AssertionError("MethodType.generic() did not return the expected generic MethodType instance");
        }
    }

    private static MethodType genericMethodType(int parameterCount) {
        // Create a generic MethodType with the specified parameter count
        Class<?>[] params = new Class<?>[parameterCount];
        for (int i = 0; i < parameterCount; i++) {
            params[i] = Object.class;
        }
        return MethodType.methodType(Object.class, params);
    }
}
