/* @test    @summary Test setSerialNumber method of AdaptableX509CertSelector for exception throwing behavior    @run main AdaptableX509CertSelectorWitupTest */

import java.math.BigInteger;

public class AdaptableX509CertSelectorWitupTest {

    public static void main(String[] args) {
        testSetSerialNumberThrowsException();
    }

    public static void testSetSerialNumberThrowsException() {
        sun.security.provider.certpath.AdaptableX509CertSelector selector = new sun.security.provider.certpath.AdaptableX509CertSelector();
        BigInteger serialNumber = BigInteger.valueOf(123456789L);
        boolean exceptionThrown = false;
        try {
            selector.setSerialNumber(serialNumber);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException was not thrown.");
        }
    }
}
