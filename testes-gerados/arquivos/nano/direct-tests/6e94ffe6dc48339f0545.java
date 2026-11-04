/* @test    @summary Test ECPrivateKeySpec constructor with valid parameters    @run main ECPrivateKeySpecWitupTest */

import java.math.BigInteger;
import java.security.spec.ECParameterSpec;

public class ECPrivateKeySpecWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidParameters();
        testConstructorWithNullS();
        testConstructorWithNullParams();
    }

    static void testConstructorWithValidParameters() {
        BigInteger s = BigInteger.valueOf(12345);
        ECParameterSpec params = new ECParameterSpec(); // Using default constructor for simplicity
        ECPrivateKeySpec spec = new ECPrivateKeySpec(s, params);
        assert spec.getS().equals(s) : "Expected s to be equal to input";
        assert spec.getParams() == params : "Expected params to be the same object";
    }

    static void testConstructorWithNullS() {
        ECParameterSpec params = new ECParameterSpec();
        try {
            new ECPrivateKeySpec(null, params);
            assert false : "Expected NullPointerException for null s";
        } catch (NullPointerException e) {
            assert e.getMessage().equals("s is null") : "Unexpected exception message";
        }
    }

    static void testConstructorWithNullParams() {
        BigInteger s = BigInteger.valueOf(67890);
        try {
            new ECPrivateKeySpec(s, null);
            assert false : "Expected NullPointerException for null params";
        } catch (NullPointerException e) {
            assert e.getMessage().equals("params is null") : "Unexpected exception message";
        }
    }
}
