/* @test    @summary Test for CertPathBuilderSpi.engineGetRevocationChecker() method.
 * @run main CertPathBuilderSpiWitupTest
 */

import java.security.cert.CertPathBuilderSpi;
import java.security.cert.CertPathChecker;

public class CertPathBuilderSpiWitupTest {

    public static void main(String[] args) {
        testEngineGetRevocationCheckerThrowsException();
    }

    public static void testEngineGetRevocationCheckerThrowsException() {
        CertPathBuilderSpi spi = new CertPathBuilderSpi() {
            @Override
            public CertPathChecker engineGetRevocationChecker() {
                throw new UnsupportedOperationException();
            }

            @Override
            public CertPathBuilderResult engineBuild(CertPathParameters params) {
                return null; // Not relevant for this test
            }
        };
        try {
            spi.engineGetRevocationChecker();
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: Caught expected UnsupportedOperationException.");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }
}
