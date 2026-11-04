/* @test    @summary Test for sun.security.x509.CRLNumberExtension constructors and behavior    @run main CRLNumberExtensionWitupTest */

import java.io.IOException;
import java.math.BigInteger;
import sun.security.x509.CRLNumberExtension;

public class CRLNumberExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithInt();
        testConstructorWithBigInteger();
    }

    private static void testConstructorWithInt() {
        try {
            int crlNum = 12345;
            CRLNumberExtension ext = new CRLNumberExtension(crlNum);
            if (ext == null) {
                throw new AssertionError("CRLNumberExtension instance is null");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException in constructor with int", e);
        }
    }

    private static void testConstructorWithBigInteger() {
        try {
            BigInteger crlNum = new BigInteger("9876543210");
            CRLNumberExtension ext = new CRLNumberExtension(crlNum);
            if (ext == null) {
                throw new AssertionError("CRLNumberExtension instance is null");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException in constructor with BigInteger", e);
        }
    }
}
