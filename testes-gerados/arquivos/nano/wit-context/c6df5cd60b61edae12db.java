/*
 * @test
 * @summary Test for XECPublicKeySpec constructor null argument handling
 * @run main XECPublicKeySpecWitupTest
 */

import java.security.spec.XECPublicKeySpec;
import java.security.spec.AlgorithmParameterSpec;
import java.math.BigInteger;

public class XECPublicKeySpecWitupTest {

    public static void main(String[] args) {
        testConstructorNullParams();
        testConstructorNullU();
    }

    private static void testConstructorNullParams() {
        try {
            new XECPublicKeySpec(null, BigInteger.ONE);
            System.err.println("Failed: Expected NullPointerException for null params");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testConstructorNullU() {
        AlgorithmParameterSpec dummyParams = new AlgorithmParameterSpec() {};
        try {
            new XECPublicKeySpec(dummyParams, null);
            System.err.println("Failed: Expected NullPointerException for null u");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
