/*
 * @test
 * @summary Test AdaptableX509CertSelector.setSerialNumber throws IllegalArgumentException
 * @run main AdaptableX509CertSelectorWitupTest
 */

import java.math.BigInteger;
import sun.security.provider.certpath.AdaptableX509CertSelector;

public class AdaptableX509CertSelectorWitupTest {
    public static void main(String[] args) {
        AdaptableX509CertSelector selector = new AdaptableX509CertSelector();
        try {
            selector.setSerialNumber(BigInteger.ONE);
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException expected) {
            // Expected exception
        }
    }
}
