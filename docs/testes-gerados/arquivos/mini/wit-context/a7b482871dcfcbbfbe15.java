/*
 * @test
 * @summary Basic tests for sun.security.x509.AlgIdDSA constructors and getters
 * @run main AlgIdDSAWitupTest
 */

import sun.security.x509.AlgIdDSA;
import java.math.BigInteger;
import java.security.ProviderException;

public class AlgIdDSAWitupTest {
    public static void main(String[] args) throws Exception {
        testDefaultConstructor();
        testConstructorWithValidParams();
        testConstructorWithInvalidParams();
    }

    private static void testDefaultConstructor() {
        AlgIdDSA alg = new AlgIdDSA();
        // Default constructor does not initialize parameters, so getters return null
        if (alg.getP() != null) {
            throw new AssertionError("Expected getP() to return null");
        }
        if (alg.getQ() != null) {
            throw new AssertionError("Expected getQ() to return null");
        }
        if (alg.getG() != null) {
            throw new AssertionError("Expected getG() to return null");
        }
    }

    private static void testConstructorWithValidParams() {
        BigInteger p = new BigInteger("23");
        BigInteger q = new BigInteger("11");
        BigInteger g = new BigInteger("7");
        AlgIdDSA alg = new AlgIdDSA(p, q, g);
        if (!p.equals(alg.getP())) {
            throw new AssertionError("getP() returned unexpected value");
        }
        if (!q.equals(alg.getQ())) {
            throw new AssertionError("getQ() returned unexpected value");
        }
        if (!g.equals(alg.getG())) {
            throw new AssertionError("getG() returned unexpected value");
        }
    }

    private static void testConstructorWithInvalidParams() {
        BigInteger p = new BigInteger("23");
        BigInteger q = new BigInteger("11");
        BigInteger g = null;
        boolean thrown = false;
        try {
            new AlgIdDSA(p, q, g);
        } catch (ProviderException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected ProviderException when g is null but p and q are non-null");
        }

        thrown = false;
        try {
            new AlgIdDSA(null, q, g);
        } catch (ProviderException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected ProviderException when p is null but q is non-null");
        }

        thrown = false;
        try {
            new AlgIdDSA(p, null, g);
        } catch (ProviderException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected ProviderException when q is null but g is non-null");
        }
    }
}
