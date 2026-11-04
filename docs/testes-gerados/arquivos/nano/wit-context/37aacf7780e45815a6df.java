/* @test    @summary Test for sun.security.x509.CRLNumberExtension constructors and behavior    @run main CRLNumberExtensionWitupTest */

import java.math.BigInteger;
import sun.security.x509.CRLNumberExtension;

public class CRLNumberExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithBigInteger();
        testConstructorWithNullBigInteger();
    }

    private static void testConstructorWithBigInteger() throws Exception {
        BigInteger crlNum = BigInteger.valueOf(12345);
        CRLNumberExtension ext = new CRLNumberExtension(crlNum);
        if (ext == null) {
            throw new AssertionError("Constructor with BigInteger returned null");
        }
        System.out.println("testConstructorWithBigInteger passed.");
    }

    private static void testConstructorWithNullBigInteger() throws Exception {
        try {
            new CRLNumberExtension((BigInteger) null);
            throw new AssertionError("Expected NullPointerException or IOException for null BigInteger");
        } catch (NullPointerException | IOException e) {
            System.out.println("Caught expected exception for null BigInteger: " + e.getClass().getSimpleName());
        }
        System.out.println("testConstructorWithNullBigInteger passed.");
    }
}
