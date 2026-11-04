/*
 * @test
 * @summary Test for AdaptableX509CertSelector.setSerialNumber method.
 * @run main AdaptableX509CertSelectorWitupTest
 */

import java.math.BigInteger;
import sun.security.provider.certpath.AdaptableX509CertSelector;

public class AdaptableX509CertSelectorWitupTest {

    public static void main(String[] args) {
        testSetSerialNumberThrowsException();
    }

    public static void testSetSerialNumberThrowsException() {
        AdaptableX509CertSelector selector = new AdaptableX509CertSelector();
        BigInteger serial = BigInteger.valueOf(123456789);
        try {
            selector.setSerialNumber(serial);
            System.out.println("FAIL: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: IllegalArgumentException was correctly thrown.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t);
        }
    }
}
