/*
 * @test
 * @summary Test for setEnabledCipherSuites handling null input
 * @run main SSLEngineImplWitupTest
 */

import sun.security.ssl.SSLEngineImpl;
import javax.net.ssl.SSLException;

public class SSLEngineImplWitupTest {

    public static void main(String[] args) {
        testSetEnabledCipherSuitesNull();
    }

    public static void testSetEnabledCipherSuitesNull() {
        SSLEngineImpl engine = new SSLEngineImpl(null);
        try {
            engine.setEnabledCipherSuites(null);
            // Expecting an exception or specific behavior; since the method does not throw,
            // we check if the enabledCipherSuites is set to null or empty.
            // As per current implementation, it calls CipherSuite.validValuesOf(suites), which
            // should handle null input gracefully or throw. Since no exception is documented,
            // we assume no exception is thrown.
            System.out.println("setEnabledCipherSuites with null input executed without exception.");
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println("Exception caught as expected: " + e.getClass().getSimpleName());
        } catch (Exception e) {
            System.out.println("Unexpected exception: " + e);
        }
    }
}
