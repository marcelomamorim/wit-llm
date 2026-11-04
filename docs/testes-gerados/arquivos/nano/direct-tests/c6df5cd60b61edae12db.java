/* @test    @summary Test constructor of XECPublicKeySpec for null parameters    @run main XECPublicKeySpecWitupTest */

import java.security.spec.XECPublicKeySpec;
import java.security.spec.AlgorithmParameterSpec;
import java.math.BigInteger;

public class XECPublicKeySpecWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullParams();
        testConstructorWithNullU();
        testConstructorWithValidInputs();
    }

    private static void testConstructorWithNullParams() {
        try {
            new XECPublicKeySpec(null, BigInteger.ONE);
            System.err.println("Failed: Expected NullPointerException for null params");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    private static void testConstructorWithNullU() {
        AlgorithmParameterSpec dummyParams = new AlgorithmParameterSpec() {};
        try {
            new XECPublicKeySpec(dummyParams, null);
            System.err.println("Failed: Expected NullPointerException for null u");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    private static void testConstructorWithValidInputs() {
        AlgorithmParameterSpec dummyParams = new AlgorithmParameterSpec() {};
        BigInteger u = new BigInteger("123456789");
        XECPublicKeySpec spec = new XECPublicKeySpec(dummyParams, u);
        assert spec.getParams() == dummyParams : "Params do not match";
        assert spec.getU().equals(u) : "U coordinate does not match";
    }
}
