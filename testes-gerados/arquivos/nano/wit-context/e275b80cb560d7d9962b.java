/* @test    @summary Test for AdaptableX509CertSelector.setSubjectKeyIdentifier to verify it throws IllegalArgumentException as expected    @run main AdaptableX509CertSelectorWitupTest */

import sun.security.provider.certpath.AdaptableX509CertSelector;

public class AdaptableX509CertSelectorWitupTest {

    public static void main(String[] args) {
        testSetSubjectKeyIdentifierThrowsException();
    }

    public static void testSetSubjectKeyIdentifierThrowsException() {
        AdaptableX509CertSelector selector = new AdaptableX509CertSelector();
        byte[] dummyKeyId = {0x01, 0x02, 0x03};
        try {
            selector.setSubjectKeyIdentifier(dummyKeyId);
            System.out.println("FAIL: Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: IllegalArgumentException was thrown as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t.getClass().getName());
        }
    }
}
