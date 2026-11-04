/* @test    @summary Test for MethodType.genericMethodType(int) method    @run main MethodTypeWitupTest */

import java.lang.invoke.MethodType;

public class MethodTypeWitupTest {

    public static void main(String[] args) {
        testGenericMethodTypeZero();
        testGenericMethodTypePositive();
    }

    static void testGenericMethodTypeZero() {
        MethodType mt = MethodType.genericMethodType(0);
        assert mt != null : "MethodType should not be null";
        assert mt.parameterCount() == 0 : "Parameter count should be zero";
        assert mt.returnType() == void.class : "Return type should be void";
    }

    static void testGenericMethodTypePositive() {
        int argCount = 3;
        MethodType mt = MethodType.genericMethodType(argCount);
        assert mt != null : "MethodType should not be null";
        assert mt.parameterCount() == argCount : "Parameter count should match input";
        assert mt.returnType() == void.class : "Return type should be void";
    }
}
