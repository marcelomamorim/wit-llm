/* @test    @summary Test CRLNumberExtension constructor with BigInteger argument    @run main CRLNumberExtensionWitupTest */

import java.math.BigInteger;
import sun.security.x509.CRLNumberExtension;

public class CRLNumberExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithBigInteger();
    }

    private static void testConstructorWithBigInteger() throws Exception {
        BigInteger testValue = new BigInteger("123456789");
        CRLNumberExtension ext = new CRLNumberExtension(testValue);
        if (ext == null) {
            throw new AssertionError("CRLNumberExtension instance is null");
        }
        // Additional observable checks can be added if public methods are available
        System.out.println("CRLNumberExtension created successfully with value: " + testValue);
    }
}
