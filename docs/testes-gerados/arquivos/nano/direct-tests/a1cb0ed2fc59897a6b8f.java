/* @test    @summary Test for XECPrivateKeySpec constructor with valid parameters    @run main XECPrivateKeySpecWitupTest */

import java.security.spec.XECPrivateKeySpec;
import java.security.spec.AlgorithmParameterSpec;

public class XECPrivateKeySpecWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidParams();
        testGetScalarReturnsCopy();
    }

    static void testConstructorWithValidParams() {
        AlgorithmParameterSpec params = new DummyAlgorithmParameterSpec();
        byte[] scalar = {1, 2, 3, 4};
        XECPrivateKeySpec spec = new XECPrivateKeySpec(params, scalar);
        assert spec.getParams() == params : "Params should be the same object passed in";
        byte[] returnedScalar = spec.getScalar();
        assert java.util.Arrays.equals(returnedScalar, scalar) : "Scalar bytes should match";
        // Ensure getScalar returns a copy
        returnedScalar[0] = 99;
        byte[] again = spec.getScalar();
        assert again[0] != 99 : "getScalar should return a copy";
    }

    static void testGetScalarReturnsCopy() {
        AlgorithmParameterSpec params = new DummyAlgorithmParameterSpec();
        byte[] scalar = {5, 6, 7, 8};
        XECPrivateKeySpec spec = new XECPrivateKeySpec(params, scalar);
        byte[] copy1 = spec.getScalar();
        copy1[0] = 42;
        byte[] copy2 = spec.getScalar();
        assert copy2[0] != 42 : "Modifying returned array should not affect internal state";
    }

    static class DummyAlgorithmParameterSpec implements AlgorithmParameterSpec {
        // Dummy implementation for testing
    }
}
