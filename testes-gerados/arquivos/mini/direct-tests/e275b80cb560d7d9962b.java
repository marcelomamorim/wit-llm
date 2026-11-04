/*
 * @test
 * @summary Test that AdaptableX509CertSelector.setSubjectKeyIdentifier(byte[]) throws IllegalArgumentException
 * @run main AdaptableX509CertSelectorWitupTest
 */

import sun.security.provider.certpath.AdaptableX509CertSelector;

public class AdaptableX509CertSelectorWitupTest {
    public static void main(String[] args) {
        AdaptableX509CertSelector selector = new AdaptableX509CertSelector();
        try {
            selector.setSubjectKeyIdentifier(new byte[] {1, 2, 3});
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
        try {
            selector.setSubjectKeyIdentifier(null);
            throw new RuntimeException("Expected IllegalArgumentException not thrown for null argument");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }
}
