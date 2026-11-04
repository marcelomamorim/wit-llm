/*
 * @test
 * @summary Basic behavior of the XECPrivateKeySpec constructor and accessors
 * @run main XECPrivateKeySpecWitupTest
 */

import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.XECPrivateKeySpec;
import java.util.Arrays;

public class XECPrivateKeySpecWitupTest {

    static final class DummyParams implements AlgorithmParameterSpec {
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullParamsRejected() {
        byte[] scalar = {1, 2, 3, 4};
        try {
            new XECPrivateKeySpec(null, scalar);
            throw new RuntimeException("expected NullPointerException for null params");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullScalarRejected() {
        DummyParams params = new DummyParams();
        try {
            new XECPrivateKeySpec(params, null);
            throw new RuntimeException("expected NullPointerException for null scalar");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testGetParamsReturnsSameInstance() {
        DummyParams params = new DummyParams();
        XECPrivateKeySpec spec = new XECPrivateKeySpec(params, new byte[]{1, 2, 3});
        check(spec.getParams() == params, "getParams should return the supplied params");
    }

    static void testGetScalarReturnsEncodedValue() {
        byte[] scalar = {1, 2, 3, 4};
        XECPrivateKeySpec spec = new XECPrivateKeySpec(new DummyParams(), scalar);
        check(Arrays.equals(spec.getScalar(), new byte[]{1, 2, 3, 4}),
              "getScalar should return the encoded scalar value");
    }

    static void testConstructorCopiesScalar() {
        byte[] scalar = {1, 2, 3, 4};
        XECPrivateKeySpec spec = new XECPrivateKeySpec(new DummyParams(), scalar);
        scalar[0] = 9;
        check(spec.getScalar()[0] == 1,
              "modifying the input array must not affect the spec");
    }

    static void testGetScalarReturnsDefensiveCopy() {
        XECPrivateKeySpec spec = new XECPrivateKeySpec(new DummyParams(), new byte[]{1, 2, 3, 4});
        byte[] first = spec.getScalar();
        first[0] = 7;
        check(spec.getScalar()[0] == 1,
              "modifying the returned array must not affect the spec");
        check(spec.getScalar() != spec.getScalar(),
              "getScalar should return a new array on each call");
    }

    public static void main(String[] args) throws Exception {
        testNullParamsRejected();
        testNullScalarRejected();
        testGetParamsReturnsSameInstance();
        testGetScalarReturnsEncodedValue();
        testConstructorCopiesScalar();
        testGetScalarReturnsDefensiveCopy();
    }
}
